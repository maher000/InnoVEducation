package com.education.innov.innoveducation.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.education.innov.innoveducation.Entities.Child;
import com.education.innov.innoveducation.Entities.ClassRoom;
import com.education.innov.innoveducation.Entities.Course;
import com.education.innov.innoveducation.Entities.Notification;
import com.education.innov.innoveducation.Entities.Parent;
import com.education.innov.innoveducation.Entities.Teacher;
import com.education.innov.innoveducation.R;
import com.education.innov.innoveducation.Utils.ComplexPreferences;
import com.education.innov.innoveducation.Utils.Config;
import com.education.innov.innoveducation.Utils.MyApp;
import com.education.innov.innoveducation.Utils.psuhNotificationAllUsers;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.liuguangqiang.progressbar.CircleProgressBar;
import com.liuguangqiang.swipeback.SwipeBackActivity;
import com.liuguangqiang.swipeback.SwipeBackLayout;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddCourssesActivity extends SwipeBackActivity {

    private String name, description, visibility, author, urlImageAuthor, id, dateStart;
    private EditText EdtNameCoursse, EdtDescriptionCoursse;
    private RadioButton RbVYes, RbVNo;
    private Button btnAddCoursse;
    private Course new_coursse;
    private DatabaseReference mDBase = Config.mDatabase;
    private CircleProgressBar progressBar;
    private SwipeBackLayout swipeBackLayout;
    private Teacher teacher;
    private ComplexPreferences complexPreferences;
    private ClassRoom class_room;
    private String notificationBody="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_coursses);

        getInfomationUser();

        EdtNameCoursse = (EditText) findViewById(R.id.EdtNameCoursse);
        EdtDescriptionCoursse = (EditText) findViewById(R.id.EdtDescriptionCoursse);
        RbVYes = (RadioButton) findViewById(R.id.RbVYes);
        RbVNo = (RadioButton) findViewById(R.id.RbVNo);
        btnAddCoursse = (Button) findViewById(R.id.btnAddCoursse);
        getInfomationUser();
        btnAddCoursse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddCOursse();
            }
        });
        initViews();
    }

    private void initViews() {
        progressBar = (CircleProgressBar) findViewById(R.id.progressbar1);
        swipeBackLayout = (SwipeBackLayout) findViewById(R.id.swipe_layout);
        swipeBackLayout.setEnableFlingBack(false);
        swipeBackLayout.setOnPullToBackListener(new SwipeBackLayout.SwipeBackListener() {
            @Override
            public void onViewPositionChanged(float fractionAnchor, float fractionScreen) {
                progressBar.setProgress((int) (progressBar.getMax() * fractionAnchor));
            }
        });
    }

    private void AddCOursse() {
        System.out.println("add new coursse");
        name = EdtNameCoursse.getText().toString();
        description = EdtDescriptionCoursse.getText().toString();
        if (RbVNo.isChecked()) {
            visibility = "no";
        }
        if (RbVYes.isChecked()) {
            visibility = "yes";

        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMMM dd 'at' hh:mm aaa");
        dateStart = sdf.format(new Date());
        id = mDBase.child("coursses").push().getKey();
        new_coursse = new Course();
        new_coursse.setDescription(description);
        new_coursse.setAuthor(author);
        new_coursse.setUrlImageAuthor(urlImageAuthor);
        new_coursse.setName(name);
        new_coursse.setVisibility(visibility);
        new_coursse.setIdClassRoom(class_room.getId());
        new_coursse.setId(id);
        new_coursse.setOwnerId(FirebaseAuth.getInstance().getCurrentUser().getUid());
        new_coursse.setCreationDate(dateStart);
        mDBase.child("coursses").child(id).setValue(new_coursse).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... voids) {
                            notificationBody=author+" has added a new Course : "+new_coursse.getName()+" on "+class_room.getName();
                            Notification not=new Notification();
                            not.setContenue(notificationBody);
                            not.setSenderId(FirebaseAuth.getInstance().getCurrentUser().getUid());
                            not.setSenderName(teacher.getFirstName()+" "+teacher.getLastName());
                            not.setType("course");
                            not.setUrlImage(teacher.getUrlImage());
                            not.setDate(new Date().toString());
                            not.setClassRoomId(class_room.getId());
                            not.setId( mDBase.child("notification").push().getKey());
                            mDBase.child("notification").child(not.getId()).setValue(not);
                            psuhNotificationAllUsers.sendAndroidNotification("/topics/"+new_coursse.getIdClassRoom(),notificationBody,"new Course");
                            return null;
                        }
                    }.execute();
                    System.out.println(" success");
                    AddCourssesActivity.this.finish();
                } else {
                    System.out.println(task.getException().getMessage());
                }
            }
        });
    }

    public void getInfomationUser() {

        complexPreferences = ComplexPreferences.getComplexPreferences(this, "mypref", this.MODE_PRIVATE);
        teacher = complexPreferences.getObject("current_user", Teacher.class);
        if (teacher != null) {
            author = teacher.getFirstName() + " " + teacher.getLastName();
            urlImageAuthor = teacher.getUrlImage();
            class_room = complexPreferences.getObject("my_class_room", ClassRoom.class);
            System.out.println(teacher + "tttttttttttttt");
        }

    }
}
