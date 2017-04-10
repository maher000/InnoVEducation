package com.education.innov.innoveducation.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import com.education.innov.innoveducation.Adapter.ClassePagerAdapter;
import com.education.innov.innoveducation.Adapter.ViewPagerAdapter;
import com.education.innov.innoveducation.R;


public class ClasseFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
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
//hello wolrd
//modification

        //maher ye behi ;* 


        return view ;

    }

    private PagerAdapter buildAdapter() {
        return (new ClassePagerAdapter(getActivity().getSupportFragmentManager()));
    }
}