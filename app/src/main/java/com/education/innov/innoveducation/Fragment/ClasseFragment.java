package com.education.innov.innoveducation.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import com.education.innov.innoveducation.Activities.AddClassRoomActivity;
import com.education.innov.innoveducation.Activities.AddCourssesActivity;
import com.education.innov.innoveducation.Activities.AddHomeWorkActivity;
import com.education.innov.innoveducation.Activities.AddPostActivity;
import com.education.innov.innoveducation.Adapter.ClassePagerAdapter;
import com.education.innov.innoveducation.Adapter.ViewPagerAdapter;
import com.education.innov.innoveducation.R;


public class ClasseFragment extends Fragment {

    FloatingActionButton btnAddHomeWork,btnAddClassroom ,btn_add_post_layout_post ,btn_add_course_layout;
    public ClasseFragment() {
        // Required empty public constructor
    }

    public static ClasseFragment newInstance(String param1, String param2) {
        ClasseFragment fragment = new ClasseFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_classe, container, false);
        ViewPager pager = (ViewPager) view.findViewById(R.id.VpPagerClasse);
        pager.setAdapter(buildAdapter());
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.classe_sliding_tabs);
        tabLayout.setupWithViewPager(pager);
        btnAddHomeWork =(FloatingActionButton) view.findViewById(R.id.btn_add_home_work_layout_homework);
        btnAddClassroom =(FloatingActionButton) view.findViewById(R.id.btn_add_classroom_layout_homework);
        btn_add_post_layout_post =(FloatingActionButton) view.findViewById(R.id.btn_add_post_layout_post);
        btn_add_course_layout =(FloatingActionButton) view.findViewById(R.id.btn_add_course_layout);
        btnAddHomeWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addHomeWork();
            }
        });
        btnAddClassroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addClassroom();
            }
        });
        btn_add_post_layout_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPost();
            }
        });
        btn_add_course_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCoursse();
            }
        });
        return view ;
    }

    private PagerAdapter buildAdapter() {
        return (new ClassePagerAdapter(getActivity().getSupportFragmentManager()));
    }
    // add a home work
    private void addHomeWork()
    {
        startActivity(new Intent(getActivity(), AddHomeWorkActivity.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
    }
    private void addClassroom()
    {
        startActivity(new Intent(getActivity(), AddClassRoomActivity.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
    }

    private void addPost()
    {
        startActivity(new Intent(getActivity(), AddPostActivity.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
    }

    private void addCoursse() {
        startActivity(new Intent(getActivity(), AddCourssesActivity.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
    }
}