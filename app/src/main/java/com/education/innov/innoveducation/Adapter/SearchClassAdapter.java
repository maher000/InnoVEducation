package com.education.innov.innoveducation.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.education.innov.innoveducation.Entities.Child;
import com.education.innov.innoveducation.Entities.ClassRoom;
import com.education.innov.innoveducation.Entities.ClassroomRequest;
import com.education.innov.innoveducation.Entities.Notification;
import com.education.innov.innoveducation.Entities.Teacher;
import com.education.innov.innoveducation.R;
import com.education.innov.innoveducation.Utils.ComplexPreferences;
import com.education.innov.innoveducation.Utils.Config;
import com.education.innov.innoveducation.Utils.psuhNotificationAllUsers;
import com.education.innov.innoveducation.Views.NotificationViewHolder;
import com.education.innov.innoveducation.Views.SearchClassRoomViewHolder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Syrine on 28/04/2017.
 */

public class SearchClassAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private LayoutInflater inflater;
    private Context context;
    private ArrayList<ClassRoom> classRooms;

    private String Role ,id;
    private Child child;
    private Teacher teacher;
    private SharedPreferences shared;
    private ComplexPreferences complexPreferences;
    private String author;
    private String urlImageAuthor;
    private DatabaseReference mDBase = Config.mDatabase;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private SearchClassRoomViewHolder mHolder;
    private String id_class;
    private HashMap<String,String> list_class_teacher;
    private ArrayList<ClassroomRequest> list_class_room_requests ;
    private String notificationBody="";


    public SearchClassAdapter(Context context, ArrayList<ClassRoom> classRooms,ArrayList<ClassroomRequest> list_class_room_requests) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.classRooms = classRooms;
        this.list_class_room_requests = list_class_room_requests ;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View search_class_room_row = inflater.inflate(R.layout.row_item_class_room_search, parent, false);
        return new SearchClassRoomViewHolder(search_class_room_row); // view holder for normal items

    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final int itemType = getItemViewType(position);
        getInfomationUser();
        id = classRooms.get(position).getId() + FirebaseAuth.getInstance().getCurrentUser().getUid();
        mHolder = (SearchClassRoomViewHolder) holder;
        mHolder.tvName.setText(classRooms.get(position).getName());
        mHolder.tvCreatedAt.setText(classRooms.get(position).getCreationDate());
        mHolder.tvResponsable.setText(classRooms.get(position).getAuthor());
        mHolder.tvCountry.setText(classRooms.get(position).getCountry());
        mHolder.tvInstitut.setText(classRooms.get(position).getAdress());//institut
        String source = classRooms.get(position).getUrlImageAuthor();
        Picasso.with(context).load(source).into(mHolder.imgProfile);


        switch (Role.trim()) {
            case "teacher":
                if (list_class_teacher.containsValue(classRooms.get(position).getId().trim())) {
                    System.out.println("yes it exist");
                    mHolder.btnJoin.setVisibility(View.GONE);
                }

                break;
            case "child":
                if(id_class != null)
                if (classRooms.get(position).getId().trim().equals(id_class.trim())) {
                    System.out.println("yes it exist");
                    mHolder.btnJoin.setVisibility(View.GONE);
                }
                break;
        }


            if(list_class_room_requests.contains(classRooms.get(position).getId())){
                mHolder.btnJoin.setText("request sent");


        }

        System.out.println("the id of request id "+id);
        System.out.println("list of requests"+list_class_room_requests);

        mHolder.btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHolder.btnJoin.setText("request sent");
                join(position);

            }
        });
    }

    private void join(final int position) {
        ClassroomRequest req = new ClassroomRequest();
        req.setAdminClassroomId(classRooms.get(position).getIdAdminstrator());
        req.setClassroomId(classRooms.get(position).getId());
        req.setSenderId(FirebaseAuth.getInstance().getCurrentUser().getUid());
        req.setSenderName(author);
        req.setUrlImgSender(urlImageAuthor);
        req.setSenderType(Role);

        Date date = new Date();
        SimpleDateFormat simpleDate = new SimpleDateFormat("dd-mm-yyyy hh:mm");
        String dateS = simpleDate.format(date);
        req.setDate(dateS);

        req.setClassroomName(classRooms.get(position).getName());

        String id = classRooms.get(position).getId() + FirebaseAuth.getInstance().getCurrentUser().getUid();
        req.setId(id);

        mDBase.child("classroomRequest").child(id).setValue(req).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    notificationBody=author+" wants to join "+classRooms.get(position).getName();
                    Notification not=new Notification();
                    not.setContenue(notificationBody);
                    not.setSenderId(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    not.setSenderName(author);
                    not.setType("join");
                    not.setUrlImage(urlImageAuthor);
                    not.setDate(new Date().toString());
                    not.setClassRoomId(classRooms.get(position).getId());
                    sendNotification(classRooms.get(position).getIdAdminstrator(),not);

                } else {
                    System.out.println("error" + task.getException().getMessage());
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return classRooms.size();
    }

    public void getInfomationUser() {

        shared = context.getSharedPreferences("role_user", Activity.MODE_PRIVATE);
        Role = shared.getString("role", null);
        if (Role != null) {

            switch (Role.trim()) {
                case "teacher":
                    complexPreferences = ComplexPreferences.getComplexPreferences(context, "mypref", context.MODE_PRIVATE);
                    teacher = complexPreferences.getObject("current_user", Teacher.class);
                    if (teacher != null) {
                        author = teacher.getFirstName() + " " + teacher.getLastName();
                        urlImageAuthor = teacher.getUrlImage();
                        list_class_teacher = teacher.getClassRooms();
                    }
                    System.out.println(teacher + "tttttttttttttt");
                    break;
                case "child":
                    complexPreferences = ComplexPreferences.getComplexPreferences(context, "mypref", context.MODE_PRIVATE);
                    child = complexPreferences.getObject("current_user", Child.class);
                    if (child != null) {
                        author = child.getFirstName() + " " + child.getLastName();
                        urlImageAuthor = child.getUrlImage();
                        id_class = child.getClassRommId();

                    }
                    break;
            }
        }
    }
  private void  sendNotification(String adminId,final  Notification not){
      mDBase.child("teachers").orderByChild("idUser").equalTo(adminId).addChildEventListener(new ChildEventListener() {
          @Override
          public void onChildAdded(DataSnapshot dataSnapshot, String s) {
              Teacher teacher=dataSnapshot.getValue(Teacher.class);
              System.out.println("tokenTeacher"+teacher.getToken());
              if(teacher!=null){
                  not.setId( mDBase.child("notification").push().getKey());
                  mDBase.child("notification").child(not.getId()).setValue(not);
                    final Teacher t=teacher;
                      new AsyncTask<Void, Void, Void>() {
                          @Override
                          protected Void doInBackground(Void... voids) {
                              psuhNotificationAllUsers.sendAndroidNotification(t.getToken(), not.getContenue(), "new join request");
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