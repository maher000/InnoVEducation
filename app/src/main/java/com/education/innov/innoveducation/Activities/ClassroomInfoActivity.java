package com.education.innov.innoveducation.Activities;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.education.innov.innoveducation.Adapter.ClassInfoAdapter;
import com.education.innov.innoveducation.Adapter.CommentsAdapter;
import com.education.innov.innoveducation.Entities.Child;
import com.education.innov.innoveducation.Entities.ClassRoom;
import com.education.innov.innoveducation.Entities.ClassroomRequest;
import com.education.innov.innoveducation.Entities.Message;
import com.education.innov.innoveducation.Entities.Parent;
import com.education.innov.innoveducation.Entities.Teacher;
import com.education.innov.innoveducation.R;
import com.education.innov.innoveducation.Utils.ComplexPreferences;
import com.education.innov.innoveducation.Utils.Config;
import com.education.innov.innoveducation.Utils.MyApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

import co.intentservice.chatui.models.ChatMessage;


public class ClassroomInfoActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ClassInfoAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<ClassroomRequest> classroomRequests;
    private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    private DatabaseReference mDatabase = Config.mDatabase;
    private ComplexPreferences complexPreferences;
    private ClassRoom class_room;
    private String id_class_room ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        classroomRequests = new ArrayList<>();
        setContentView(R.layout.activity_classroom_info);
        getClassRoom();
        mRecyclerView = (RecyclerView) findViewById(R.id.info_recycler_view);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //Adapter is created in the last step
        mAdapter = new ClassInfoAdapter(this, classroomRequests);
        mRecyclerView.setAdapter(mAdapter);
        getRequests();
    }
    private void getRequests() {
        mDatabase.child("classroomRequest").orderByChild("adminClassroomId").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                ClassroomRequest m = dataSnapshot.getValue(ClassroomRequest.class);
                if (m.getClassroomId().trim().equals(id_class_room.trim())) {


                    if (m != null)
                        classroomRequests.add(m);
                    mAdapter.notifyDataSetChanged();
                    mRecyclerView.setAdapter(mAdapter);
                    System.out.println(dataSnapshot.getChildrenCount() + "gggggffffff");
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

    public void getClassRoom() {
        complexPreferences = ComplexPreferences.getComplexPreferences(this, "mypref", this.MODE_PRIVATE);
        class_room = complexPreferences.getObject("my_class_room", ClassRoom.class);
        if (class_room != null){
            id_class_room = class_room.getId();
        }

    }
}
