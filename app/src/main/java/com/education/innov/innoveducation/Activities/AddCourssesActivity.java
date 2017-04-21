package com.education.innov.innoveducation.Activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.education.innov.innoveducation.Entities.Course;
import com.education.innov.innoveducation.R;
import com.education.innov.innoveducation.Utils.Config;
import com.education.innov.innoveducation.Utils.MyApp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.liuguangqiang.progressbar.CircleProgressBar;
import com.liuguangqiang.swipeback.SwipeBackActivity;
import com.liuguangqiang.swipeback.SwipeBackLayout;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddCourssesActivity extends SwipeBackActivity {

    String name, description, visibility, author, urlImageAuthor, id ,dateStart;
    EditText EdtNameCoursse, EdtDescriptionCoursse;
    RadioButton RbVYes, RbVNo;
    Button btnAddCoursse;
    Course new_coursse;
    private DatabaseReference mDBase = Config.mDatabase;
    private CircleProgressBar progressBar;
    private SwipeBackLayout swipeBackLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_coursses);

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
            visibility = "No";
        }
        if (RbVYes.isChecked()) {
            visibility = "Yes";

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
        new_coursse.setIdClassRoom("NONE");
        new_coursse.setId(id);
        new_coursse.setCreationDate(dateStart);
        mDBase.child("coursses").child(id).setValue(new_coursse).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful() ){
                    System.out.println(" success");
                }
                else {
                    System.out.println(task.getException().getMessage()) ;
                }
            }
        });
    }
    public void getInfomationUser() {
        MyApp.getInstance(this);
        switch (MyApp.role){
            case "teacher":
                author = MyApp.teacher.getFirstName() + " " + MyApp.teacher.getLastName();
                urlImageAuthor = MyApp.teacher.getUrlImage();
                break;
            case "child":
                author = MyApp.child.getFirstName() + " " + MyApp.child.getLastName();
                urlImageAuthor = MyApp.child.getUrlImage();
                break;
            case "parent":
                author = MyApp.parent.getFirstName() + " " + MyApp.parent.getLastName();
                urlImageAuthor = MyApp.parent.getUrlImage();
                break;
        }
        System.out.println(MyApp.teacher+"pppppppppppppppp");
    }}