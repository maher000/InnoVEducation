package com.education.innov.innoveducation.Activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.education.innov.innoveducation.Adapter.MyClassroomsAdapter;
import com.education.innov.innoveducation.Adapter.NotificationAdapter;
import com.education.innov.innoveducation.Entities.Child;
import com.education.innov.innoveducation.Entities.ClassRoom;
import com.education.innov.innoveducation.Entities.Notification;
import com.education.innov.innoveducation.Entities.Parent;
import com.education.innov.innoveducation.Entities.Teacher;
import com.education.innov.innoveducation.R;
import com.education.innov.innoveducation.Utils.ComplexPreferences;
import com.education.innov.innoveducation.Utils.RecyclerItemClickListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class NotificationsActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private NotificationAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private DatabaseReference mDatabase;
    private String mUserId;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private Toolbar toolbar;
    private Child child;
    private Teacher teacher;
    private Parent parent;
    private String Role;
    private SharedPreferences shared;
    private ComplexPreferences complexPreferences;
    private ArrayList<Notification> notifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        notifications = new ArrayList<>();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        initViews();
        shared = getSharedPreferences("role_user", Activity.MODE_PRIVATE);
        Role = shared.getString("role", null);
        if (Role != null) {
            getInfomationUser();
            getNotifications();

        }
    }

    private void setUpToolbar() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_search);
        toolbar.setTitle("Notifications");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


    }

    public void getInfomationUser() {
        System.out.println("role tttt" + Role);
        if (Role != null) {
            switch (Role.trim()) {
                case "teacher":
                    complexPreferences = ComplexPreferences.getComplexPreferences(this, "mypref", this.MODE_PRIVATE);
                    teacher = complexPreferences.getObject("current_user", Teacher.class);
                    break;
                case "parent":
                    complexPreferences = ComplexPreferences.getComplexPreferences(this, "mypref", this.MODE_PRIVATE);
                    parent = complexPreferences.getObject("current_user", Parent.class);
                    break;
                case "child":
                    complexPreferences = ComplexPreferences.getComplexPreferences(this, "mypref", this.MODE_PRIVATE);
                    child = complexPreferences.getObject("current_user", Child.class);
                    break;
            }
        }
    }

    private void initViews() {

        mRecyclerView = (RecyclerView) findViewById(R.id.notif_recycler_view);
        mLayoutManager = new LinearLayoutManager(this);
        setUpToolbar();
        mRecyclerView.setLayoutManager(mLayoutManager);
        //Adapter is created in the last step
        mAdapter = new NotificationAdapter(this, notifications);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                /*
                HomeActivity.activeClassroom=classRooms.get(position).getId();
                Intent i = new Intent(MyClassRoomsActivity.this,HomeActivity.class);
                startActivity(i);
                //  i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                // startActivityForResult(i,RESULT_OK);
                finish();
                */
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void getNotifications() {
        mDatabase.child("notification").orderByChild("classRoomId").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot != null) {
                    Notification not = dataSnapshot.getValue(Notification.class);

                    switch (Role) {
                        case "teacher":
                            if (teacher != null) {
                                if (teacher.getClassRooms() != null)
                                    if (teacher.getClassRooms().containsValue(not.getClassRoomId())) {
                                        notifications.add(not);
                                        mRecyclerView.setAdapter(mAdapter);
                                    }
                            }
                            break;
                        case "parent":
                            if (parent != null) {
                                if (parent.getClassRooms() != null)
                                    if (parent.getClassRooms().containsValue(not.getClassRoomId())&&
                                            !not.getType().equals("join")) {
                                        notifications.add(not);
                                        mRecyclerView.setAdapter(mAdapter);
                                    }
                            }
                            break;
                        case "child":
                            if (child != null) {
                                if (child.getClassRommId() != null)
                                    if (child.getClassRommId().equals(not.getClassRoomId())&&
                                            !not.getType().equals("join")   ) {
                                        notifications.add(not);
                                        mRecyclerView.setAdapter(mAdapter);
                                    }
                            }
                            break;
                    }

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
