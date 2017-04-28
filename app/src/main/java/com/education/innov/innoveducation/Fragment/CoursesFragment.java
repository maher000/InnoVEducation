package com.education.innov.innoveducation.Fragment;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.education.innov.innoveducation.Activities.CourseActivity;
import com.education.innov.innoveducation.Adapter.CoursesAdapter;
import com.education.innov.innoveducation.Entities.Course;
import com.education.innov.innoveducation.Entities.Lesson;
import com.education.innov.innoveducation.R;
import com.education.innov.innoveducation.Utils.MyApp;
import com.education.innov.innoveducation.Utils.RecyclerItemClickListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class CoursesFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private CoursesAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<Course> courses;
    Course new_coursse;



    public CoursesFragment() {
    }


    // TODO: Rename and change types and number of parameters
    public static CoursesFragment newInstance(int page, String title) {
        CoursesFragment fragment = new CoursesFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_courses, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.Courses_recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        //Adapter is created in the last step

        getAllPublicCourses();
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                MyApp.course=courses.get(position);
                Intent intent = new Intent(getActivity(), CourseActivity.class) ;
                Course course = courses.get(position);
                intent.putExtra("coursse", course);
                startActivityForResult(intent,1);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
        return view;
    }

    private void getAllPublicCourses() {
        courses = new ArrayList<>();
        mAdapter = new CoursesAdapter(getActivity(), courses);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter.notifyDataSetChanged();
        FirebaseDatabase.getInstance()
                .getReference()
                .child("coursses").orderByChild("visibility").equalTo("yes").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                new_coursse = dataSnapshot.getValue(Course.class);
                System.out.println("classRoom");
                if (new_coursse != null) {
                    //creta a listener
                    courses.add(new_coursse);
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
