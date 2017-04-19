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
import com.education.innov.innoveducation.Entities.HomeWork;
import com.education.innov.innoveducation.Entities.post;
import com.education.innov.innoveducation.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class HomeworksFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private HomeWorkAdapter mAdapter;
    private HomeWork new_homework ;
    private RecyclerView.LayoutManager mLayoutManager ;
    private FloatingActionButton btnAddHomeWork;
    ArrayList<HomeWork> homeWorks = new ArrayList<>();


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
        mAdapter = new HomeWorkAdapter(getActivity(),homeWorks);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        getAllHomeworks();
        return view ;
    }

    private void getAllHomeworks(){

            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

            mAdapter.notifyDataSetChanged();
            final String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
            FirebaseDatabase.getInstance()
                    .getReference()
                    .child("homeworks").addValueEventListener(

                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot child : dataSnapshot.getChildren()) {
                                new_homework = child.getValue(HomeWork.class);

                            }
                            homeWorks.add(new_homework);
                            mRecyclerView.setAdapter(mAdapter);
                            mRecyclerView.setAdapter(mAdapter);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
        }


    }
