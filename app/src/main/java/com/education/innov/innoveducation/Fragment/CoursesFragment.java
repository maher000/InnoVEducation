package com.education.innov.innoveducation.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.education.innov.innoveducation.Activities.CourseActivity;
import com.education.innov.innoveducation.Adapter.CoursesAdapter;
import com.education.innov.innoveducation.Adapter.HomeAdapter;
import com.education.innov.innoveducation.R;
import com.education.innov.innoveducation.Utils.RecyclerItemClickListener;


public class CoursesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private RecyclerView mRecyclerView;
    private CoursesAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager ;

    public CoursesFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static CoursesFragment  newInstance(int page, String title) {
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
        mAdapter = new CoursesAdapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                startActivity(new Intent(getActivity(), CourseActivity.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
        return view ;
    }


}
