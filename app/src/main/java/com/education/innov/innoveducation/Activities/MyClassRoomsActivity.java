package com.education.innov.innoveducation.Activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.education.innov.innoveducation.Adapter.CoursesAdapter;
import com.education.innov.innoveducation.Adapter.MyClassroomsAdapter;
import com.education.innov.innoveducation.Entities.Child;
import com.education.innov.innoveducation.Entities.ClassRoom;
import com.education.innov.innoveducation.Entities.Parent;
import com.education.innov.innoveducation.Entities.Teacher;
import com.education.innov.innoveducation.R;
import com.education.innov.innoveducation.Utils.ComplexPreferences;
import com.education.innov.innoveducation.Utils.Config;
import com.education.innov.innoveducation.Utils.MyApp;
import com.education.innov.innoveducation.Utils.RecyclerItemClickListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.liuguangqiang.progressbar.CircleProgressBar;
import com.liuguangqiang.swipeback.SwipeBackActivity;
import com.liuguangqiang.swipeback.SwipeBackLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MyClassRoomsActivity extends SwipeBackActivity {
    private CircleProgressBar progressBar;
    private SwipeBackLayout swipeBackLayout;
    private RecyclerView mRecyclerView;
    private MyClassroomsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public DatabaseReference mDatabase;
    private String mUserId;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private ArrayList<ClassRoom> classRooms = new ArrayList<>();
    private Teacher teacher;
    private SharedPreferences shared;
    private ComplexPreferences complexPreferences;
    private String Role;
    private Parent parent;
    private ArrayList<String> list_class_rooms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_class_rooms);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        shared = getSharedPreferences("role_user", Activity.MODE_PRIVATE);
        Role = shared.getString("role", null);
        if (Role != null) {
            getInfomationUser();
        }


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
        //
        mRecyclerView = (RecyclerView) findViewById(R.id.myclasses_recycler_view);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //Adapter is created in the last step
        mAdapter = new MyClassroomsAdapter(this, classRooms);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                final ClassRoom classRoom = classRooms.get(position);
                System.out.println("this the class selected" + classRoom);
                switch (Role) {
                    case "teacher":
                        mDatabase.child("teachers").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .child("active_class_room").setValue(classRooms.get(position).getId()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(MyClassRoomsActivity.this, "mypref", MODE_PRIVATE);
                                complexPreferences.putObject("my_class_room", classRoom);
                                complexPreferences.commit();
                                Intent i = new Intent(MyClassRoomsActivity.this, HomeActivity.class);
                                startActivity(i);
                                finish();
                            }
                        });
                        break;
                    case "parent":
                        mDatabase.child("parents").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .child("active_class_room").setValue(classRooms.get(position).getId()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(MyClassRoomsActivity.this, "mypref", MODE_PRIVATE);
                                complexPreferences.putObject("my_class_room", classRoom);
                                complexPreferences.commit();
                                Intent i = new Intent(MyClassRoomsActivity.this, HomeActivity.class);
                                startActivity(i);
                                finish();
                            }
                        });
                        break;

                }


            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
    }

    private void getClassRooms() {
        classRooms.clear();
        if (list_class_rooms != null) {
            System.out.println("la liste des classe est " + list_class_rooms);
            mDatabase.child("classrooms").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    ClassRoom classRoom = dataSnapshot.getValue(ClassRoom.class);
                    System.out.println("classRoom 1 " + classRoom);

                    System.out.println("contains" + list_class_rooms.contains(classRoom.getId().trim()));
                    System.out.println("classRoom 3 " + classRoom.getId());
                    if (list_class_rooms.contains(classRoom.getId().trim())) {
                        if (!existe(classRoom.getId())) {
                            classRooms.add(classRoom);
                            mRecyclerView.setAdapter(mAdapter);
                            mDatabase.removeEventListener(this);
                        }

                        //create a listener
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

    private boolean existe(String id) {
        for (ClassRoom c : classRooms) {
            if (c.getId().equals(id))
                return true;

        }
        return false;
    }

    public void getInfomationUser() {
        switch (Role.trim()) {
            case "teacher":
                complexPreferences = ComplexPreferences.getComplexPreferences(this, "mypref", this.MODE_PRIVATE);
                teacher = complexPreferences.getObject("current_user", Teacher.class);
                if (teacher != null) {
                    getListClassRooms();
                }
                System.out.println(teacher + "tttttttttttttt");
                break;
            case "parent":
                complexPreferences = ComplexPreferences.getComplexPreferences(this, "mypref", this.MODE_PRIVATE);
                parent = complexPreferences.getObject("current_user", Parent.class);
                if (parent != null)

                    break;
        }
    }

    private void getListClassRooms() {
        list_class_rooms = new ArrayList<>();
        mDatabase.child("teachers").child(teacher.getIdUser()).child("classRooms").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                list_class_rooms.add(dataSnapshot.getValue().toString());
                getClassRooms();


                return;
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
