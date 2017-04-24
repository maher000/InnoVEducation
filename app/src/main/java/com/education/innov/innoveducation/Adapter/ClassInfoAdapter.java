package com.education.innov.innoveducation.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.education.innov.innoveducation.Entities.ClassroomRequest;
import com.education.innov.innoveducation.R;
import com.education.innov.innoveducation.Utils.Config;
import com.education.innov.innoveducation.Views.ClassInfoViewHolder;
import com.education.innov.innoveducation.Views.CommentsViewHolder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
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
    private FirebaseAuth mFirebaseAuth=FirebaseAuth.getInstance();
    private DatabaseReference mDatabase= Config.mDatabase;;

    public ClassInfoAdapter(Context context,ArrayList<ClassroomRequest> classroomRequests) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.classroomRequests=classroomRequests;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View CommentRow = inflater.inflate(R.layout.row_item_classroom_info, parent, false);
        return new ClassInfoViewHolder(CommentRow); // view holder for normal items

    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {



        ClassInfoViewHolder mHolder = (ClassInfoViewHolder) holder;
        Picasso.with(context).load(classroomRequests.get(position).getUrlImgSender()).into(mHolder.image_profile);

        String content = "<b>" + classroomRequests.get(position).getSenderName() + "</b> " +"wants to join "+"</b>"+
                classroomRequests.get(position).getClassroomName()+"</b>";
        mHolder.tvFullNameSend.setText(Html.fromHtml(content ));
       mHolder.btnAccpet.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               accept(position);
           }
       });
        mHolder.btnIgnore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });


    }

    @Override
    public int getItemCount() {
        return classroomRequests.size();
    }

    private void accept(final int position){
        if(classroomRequests.get(position).getSenderType().trim().equals("child")){
            mDatabase.child("child").child(classroomRequests.get(position).getSenderId()).child("classroomId").setValue(
                    classroomRequests.get(position).getClassroomId()
            ).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        mDatabase.child("classroomRequest").child(classroomRequests.get(position).getId()).
                                removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                classroomRequests.remove(position);
                                notifyDataSetChanged();
                            }
                        });
                    }
                }
            });
        }
    }
    private void ignore(final int position){
        mDatabase.child("classroomRequest").child(classroomRequests.get(position).getId()).
                removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                classroomRequests.remove(position);
            }
        });
    }



}
