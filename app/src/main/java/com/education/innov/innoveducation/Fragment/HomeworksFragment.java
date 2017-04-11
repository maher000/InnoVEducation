package com.education.innov.innoveducation.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.education.innov.innoveducation.Activities.AddHomeWorkActivity;
import com.education.innov.innoveducation.Activities.ChatActivity;
import com.education.innov.innoveducation.Adapter.CoursesAdapter;
import com.education.innov.innoveducation.Adapter.HomeWorkAdapter;
import com.education.innov.innoveducation.R;


public class HomeworksFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private HomeWorkAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager ;
    private FloatingActionButton btnAddHomeWork;


    public HomeworksFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
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
        mAdapter = new HomeWorkAdapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);
        btnAddHomeWork =(FloatingActionButton) view.findViewById(R.id.btn_add_home_work_layout_homework);
        btnAddHomeWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addHomeWork();
            }
        });
        return view ;
    }
    // add a home work
    private void addHomeWork()
    {
        startActivity(new Intent(getActivity(), AddHomeWorkActivity.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
    }
}
