package com.education.innov.innoveducation.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.education.innov.innoveducation.R;


public class ListLessonsFragment extends Fragment {


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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_lessons, container, false);
    }


}
