package com.education.innov.innoveducation.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.education.innov.innoveducation.Adapter.HomeAdapter;
import com.education.innov.innoveducation.Adapter.LessonsAdapter;
import com.education.innov.innoveducation.Entities.Lesson;
import com.education.innov.innoveducation.Entities.post;
import com.education.innov.innoveducation.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class ListLessonsFragment extends Fragment {


    RecyclerView  mRecyclerView ;
    LessonsAdapter mAdapter ;
    ArrayList<Lesson> lessons ;
    Lesson new_lesson ;
    private RecyclerView.LayoutManager mLayoutManager;


    public ListLessonsFragment() {
        // Required empty public constructor
    }

    public static ListLessonsFragment newInstance(String param1, String param2) {
        ListLessonsFragment fragment = new ListLessonsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_lessons, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.LessonsRecycleView);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        getPosts();
        return view;
    }

    public void getPosts() {

        lessons = new ArrayList<>();
        mAdapter = new LessonsAdapter(getActivity(),lessons);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter.notifyDataSetChanged();
        FirebaseDatabase.getInstance()
                .getReference()
                .child("lessons").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                new_lesson = dataSnapshot.getValue(Lesson.class);
                System.out.println("classRoom");
                if (new_lesson != null) {
                    //creta a listener
                    lessons.add(new_lesson);
                    mRecyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
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