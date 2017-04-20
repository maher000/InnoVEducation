package com.education.innov.innoveducation.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.education.innov.innoveducation.Activities.AddHomeWorkActivity;
import com.education.innov.innoveducation.Activities.ChatActivity;
import com.education.innov.innoveducation.Adapter.CoursesAdapter;
import com.education.innov.innoveducation.Adapter.HomeAdapter;
import com.education.innov.innoveducation.Adapter.HomeWorkAdapter;
import com.education.innov.innoveducation.Entities.ClassRoom;
import com.education.innov.innoveducation.Entities.HomeWork;
import com.education.innov.innoveducation.Entities.post;
import com.education.innov.innoveducation.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class HomeworksFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private HomeWorkAdapter mAdapter;
    private HomeWork new_homework;
    private RecyclerView.LayoutManager mLayoutManager;
    private FloatingActionButton btnAddHomeWork;
    ArrayList<HomeWork> homeWorks;


    public HomeworksFragment() {
        // Required empty public constructor
    }

    public static HomeworksFragment newInstance(int page, String title) {
        HomeworksFragment fragment = new HomeworksFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_homeworks, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.HomeWorks_recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        //Adapter is created in the last step

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        getAllHomeworks();
        return view;
    }

    private void getAllHomeworks() {
        homeWorks = new ArrayList<>();
        mAdapter = new HomeWorkAdapter(getActivity(), homeWorks);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        FirebaseDatabase.getInstance()
                .getReference()
                .child("homeworks").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                new_homework = dataSnapshot.getValue(HomeWork.class);
                System.out.println("classRoom" );
                if (new_homework != null) {
                    //creta a listener
                    homeWorks.add(new_homework);
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
