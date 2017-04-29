package com.education.innov.innoveducation.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.education.innov.innoveducation.Activities.AddClassRoomActivity;
import com.education.innov.innoveducation.Activities.MyClassRoomsActivity;
import com.education.innov.innoveducation.Entities.Child;
import com.education.innov.innoveducation.Entities.ClassroomRequest;
import com.education.innov.innoveducation.R;
import com.education.innov.innoveducation.Utils.ComplexPreferences;
import com.education.innov.innoveducation.Utils.Config;
import com.education.innov.innoveducation.Utils.psuhNotificationAllUsers;
import com.education.innov.innoveducation.Views.ClassInfoViewHolder;
import com.education.innov.innoveducation.Views.CommentsViewHolder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Syrine on 07/04/2017.
 */

public class ClassInfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private ArrayList<ClassroomRequest> classroomRequests;
    private LayoutInflater inflater;
    private Context context;
    private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    private DatabaseReference mDatabase = Config.mDatabase;
    ;

    public ClassInfoAdapter(Context context, ArrayList<ClassroomRequest> classroomRequests) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.classroomRequests = classroomRequests;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View CommentRow = inflater.inflate(R.layout.row_item_classroom_info, parent, false);
        return new ClassInfoViewHolder(CommentRow); // view holder for normal items

    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {


        final ClassInfoViewHolder mHolder = (ClassInfoViewHolder) holder;
        Picasso.with(context).load(classroomRequests.get(position).getUrlImgSender()).into(mHolder.image_profile);

        String content = "<b>" + classroomRequests.get(position).getSenderName() + "</b> " + "wants to join " + "</b>" +
                classroomRequests.get(position).getClassroomName() + "</b>";
        mHolder.tvFullNameSend.setText(Html.fromHtml(content));
        mHolder.btnAccpet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accept(position,mHolder);
                mHolder.btnAccpet.setClickable(false);
            }
        });
        mHolder.btnIgnore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ignore(position,mHolder);
                mHolder.btnIgnore.setClickable(false);
            }
        });


    }

    @Override
    public int getItemCount() {
        return classroomRequests.size();
    }

    private void accept(final int position, final ClassInfoViewHolder holder) {

        System.out.println("senderId0");
        final int pos=position;
        if (classroomRequests.get(position).getSenderType().trim().equals("child")) {
            System.out.println(classroomRequests.get(pos).getSenderId()+"senderId1");
            mDatabase.child("child").child(classroomRequests.get(pos).getSenderId()).child("topic").
                    setValue(classroomRequests.get(pos).getClassroomId());

            mDatabase.child("child").child(classroomRequests.get(position).getSenderId()).child("classRommId").setValue(
                    classroomRequests.get(position).getClassroomId()
            ).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        final String senderId=classroomRequests.get(position).getSenderId();
                        final ClassroomRequest clr=classroomRequests.get(pos);
                        sendNotification(senderId,clr,true);
                        System.out.println(classroomRequests.get(pos).getSenderId()+"senderId");
                        mDatabase.child("child").child(classroomRequests.get(pos).getSenderId()).child("topic").
                                setValue(classroomRequests.get(pos).getClassroomId()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    mDatabase.child("child").orderByChild("idUser").equalTo((classroomRequests.get(position).getSenderId())).
                                            addChildEventListener(new ChildEventListener() {
                                                @Override
                                                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                                    if(dataSnapshot !=null){
                                                        Child child = dataSnapshot.getValue(Child.class);

                                                        mDatabase.child("parents").child(child.getParentId()).child("topics").child(classroomRequests.get(position).getClassroomId()).
                                                                setValue(classroomRequests.get(position).getClassroomId());

                                                        mDatabase.child("parents").child(child.getParentId()).child("classRooms").
                                                                child(classroomRequests.get(position).getClassroomId()).setValue(classroomRequests.get(position).getClassroomId()).
                                                                addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        if(task.isSuccessful()){


                                                                            mDatabase.child("classroomRequest").child(classroomRequests.get(position).getId()).
                                                                                    removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                @Override
                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                    classroomRequests.remove(position);
                                                                                    notifyDataSetChanged();
                                                                                    holder.btnAccpet.setClickable(true);
                                                                                }});}
                                                                    }});
                                                    }
                                                    mDatabase.removeEventListener(this);
                                                }

                                                @Override
                                                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                                                }

                                                @Override
                                                public void onChildRemoved(DataSnapshot dataSnapshot) {
                                                }

                                                @Override
                                                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {

                                                }
                                            });
                                }
                            }
                        });



                    }
                }
            });
        } else if (classroomRequests.get(position).getSenderType().trim().equals("teacher")) {
            mDatabase.child("teachers").child(classroomRequests.get(position).getSenderId()).child("classRooms").
                    child(classroomRequests.get(position).getClassroomId()).setValue(classroomRequests.get(position).getClassroomId()).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    classroomRequests.remove(position);
                    notifyDataSetChanged();
                }
            });

        }
    }

    private void ignore(final int position, final ClassInfoViewHolder holder) {
        sendNotification(classroomRequests.get(position).getSenderId(),classroomRequests.get(position),false);
        mDatabase.child("classroomRequest").child(classroomRequests.get(position).getId()).
                removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                classroomRequests.remove(position);
                holder.btnIgnore.setClickable(true);
            }
        });
    }

    private void sendNotification(final String senderId, final ClassroomRequest clr, final boolean accept){

        mDatabase.child("child").orderByChild("idUser").equalTo(senderId).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

               final Child c=dataSnapshot.getValue(Child.class);
                if(c!=null){
                    if(c.getToken()!=null)
                        new AsyncTask<Void, Void, Void>() {
                            @Override
                            protected Void doInBackground(Void... voids) {
                                String body="";
                                if(accept)
                               body= "you are now a member in "+clr.getClassroomName();
                                else
                                    body="the administrator of "+clr.getClassroomName()+" has ignored your request";
                                psuhNotificationAllUsers.sendAndroidNotification(c.getToken(), body, "new join request");
                                return null;
                            }
                        }.execute();
                }


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


}
