package com.education.innov.innoveducation.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.education.innov.innoveducation.Activities.ChatActivity;
import com.education.innov.innoveducation.Adapter.MenuLeftNaviguationAdapter;
import com.education.innov.innoveducation.Adapter.OnLineFrreindsAdapter;
import com.education.innov.innoveducation.Adapter.SimpleSectionedRecyclerViewAdapter;
import com.education.innov.innoveducation.R;
import com.education.innov.innoveducation.Utils.RecyclerItemClickListener;
import com.education.innov.innoveducation.model.NavigationDrawerItem;

import java.util.ArrayList;
import java.util.List;


public class RightFragmentNaviguation extends Fragment {

    public static int Item = 0;
    public static List<NavigationDrawerItem> a = null;

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_right_naviguation, container, false);

        setUpRecyclerView(view);


        return view;
    }


    private RecyclerView setUpRecyclerView(View view) {

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.FreindsOnlineRecycleView);
        a = NavigationDrawerItem.getData();

        OnLineFrreindsAdapter adapter = new OnLineFrreindsAdapter(getContext());
        adapter.notifyDataSetChanged();
       // recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),LinearLayoutManager.VERTICAL));


        List<SimpleSectionedRecyclerViewAdapter.Section> sections =
                new ArrayList<SimpleSectionedRecyclerViewAdapter.Section>();

        sections.add(new SimpleSectionedRecyclerViewAdapter.Section(0,"Classemates"));
        sections.add(new SimpleSectionedRecyclerViewAdapter.Section(2,"Parents"));
        sections.add(new SimpleSectionedRecyclerViewAdapter.Section(4,"Teachers"));

        SimpleSectionedRecyclerViewAdapter.Section[] dummy = new SimpleSectionedRecyclerViewAdapter.Section[sections.size()];
        SimpleSectionedRecyclerViewAdapter mSectionedAdapter = new SimpleSectionedRecyclerViewAdapter(getActivity(),R.layout.section_recycle_view,R.id.section_text,adapter);
        mSectionedAdapter.setSections(sections.toArray(dummy));
        recyclerView.setAdapter(mSectionedAdapter);
        //recyclerView.getChildAt(0).findViewById(R.id.drawerList).setVisibility(View.INVISIBLE);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                startActivity(new Intent(getActivity(), ChatActivity.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
        return recyclerView;
    }

    public void setUpDrawer(DrawerLayout drawerLayout, Toolbar toolbar) {
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                // Do something of Slide of Drawer
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);


        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }
}