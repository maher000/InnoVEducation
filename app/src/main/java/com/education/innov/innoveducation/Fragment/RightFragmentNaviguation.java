package com.education.innov.innoveducation.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.education.innov.innoveducation.Activities.ChatActivity;
import com.education.innov.innoveducation.Adapter.MenuLeftNaviguationAdapter;
import com.education.innov.innoveducation.Adapter.OnLineFrreindsAdapter;
import com.education.innov.innoveducation.Adapter.SimpleSectionedRecyclerViewAdapter;
import com.education.innov.innoveducation.Entities.Child;
import com.education.innov.innoveducation.Entities.Parent;
import com.education.innov.innoveducation.Entities.Presence;
import com.education.innov.innoveducation.Entities.Teacher;
import com.education.innov.innoveducation.Entities.User;
import com.education.innov.innoveducation.R;
import com.education.innov.innoveducation.Utils.ComplexPreferences;
import com.education.innov.innoveducation.Utils.RecyclerItemClickListener;
import com.education.innov.innoveducation.model.NavigationDrawerItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class RightFragmentNaviguation extends Fragment {

    public static int Item = 0;
    public static List<NavigationDrawerItem> a = null;
    private ArrayList<Teacher> teachers;
    private ArrayList<Parent> parents;
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Child> children;
    private ComplexPreferences complexPreferences;
    private ArrayList<User> teachers_online = new ArrayList<>();
    private ArrayList<User> parents_online = new ArrayList<>();
    private ArrayList<User> users_online = new ArrayList<>();
    private ArrayList<User> children_online = new ArrayList<>();
    private List<SimpleSectionedRecyclerViewAdapter.Section> sections;
    private Parent new_parent, parent;
    private SimpleSectionedRecyclerViewAdapter.Section[] dummy;
    private SimpleSectionedRecyclerViewAdapter mSectionedAdapter;
    private Teacher new_teacher, teacher;
    private Child new_child, child;

    private OnLineFrreindsAdapter adapter;
    private RecyclerView recyclerView;
    private Presence presence;
    private Presence new_presence;
    private SharedPreferences shared;
    SharedPreferences sp;
    String Role, firstname, lastname, urlImage, id;
    private static Gson gson = new Gson();
    private static String json;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_right_naviguation, container, false);
        getInfomationUser();
        System.out.println("first name is " + firstname + "last name is " + lastname + "l url de l'image est " + urlImage);
        setUpRecyclerView(view);
        return view;
    }


    private RecyclerView setUpRecyclerView(View view) {

        recyclerView = (RecyclerView) view.findViewById(R.id.FreindsOnlineRecycleView);
        shared = getActivity().getSharedPreferences("role_user", Activity.MODE_PRIVATE);
        Role = shared.getString("role", null);
        System.out.println("mon roole est" + Role);

        a = NavigationDrawerItem.getData();


        // recyclerView.setAdapter(adapter);
        //recyclerView.getChildAt(0).findViewById(R.id.drawerList).setVisibility(View.INVISIBLE);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                intent.putExtra("name", users.get(mSectionedAdapter.sectionedPositionToPosition(position)).getFirstName() + " " + users.get(mSectionedAdapter.sectionedPositionToPosition
                        (position)).getLastName());
                intent.putExtra("id", users.get(mSectionedAdapter.sectionedPositionToPosition(position)).getIdUser());
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
        getListOnline();
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

    public void getInfomationUser() {
        sp = getActivity().getPreferences(Context.MODE_PRIVATE);
        json = sp.getString("current_user", "");
        if (Role != null) {
            json = sp.getString("current_user", "");
            System.out.println(json + "ffggdd");
            if (json != null) {

                switch (Role.trim()) {
                    case "teacher":
                        complexPreferences = ComplexPreferences.getComplexPreferences(getActivity(), "mypref", getActivity().MODE_PRIVATE);
                        teacher = complexPreferences.getObject("current_user", Teacher.class);
                        firstname = teacher.getFirstName();
                        lastname = teacher.getLastName();
                        urlImage = teacher.getUrlImage();
                        System.out.println(firstname + lastname + urlImage + "syriiine is trying");
                        System.out.println(teacher + "tttttttttttttt");
                        break;
                    case "parent":
                        complexPreferences = ComplexPreferences.getComplexPreferences(getActivity(), "mypref", getActivity().MODE_PRIVATE);
                        parent = complexPreferences.getObject("current_user", Parent.class);
                        firstname = parent.getFirstName();
                        lastname = parent.getLastName();
                        urlImage = parent.getUrlImage();
                        break;
                    case "child":
                        complexPreferences = ComplexPreferences.getComplexPreferences(getActivity(), "mypref", getActivity().MODE_PRIVATE);
                        child = complexPreferences.getObject("current_user", Child.class);
                        System.out.println(child + "ffggdds");
                        firstname = child.getFirstName();
                        lastname = child.getLastName();
                        urlImage = child.getUrlImage();
                        break;
                }
            }


        }
    }


    public void getListOnline() {

        adapter = new OnLineFrreindsAdapter(getContext(), users);
        adapter.notifyDataSetChanged();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter.notifyDataSetChanged();
        DatabaseReference listRef = FirebaseDatabase.getInstance()
                .getReference().child("presence");


        // Number of online users is the number of objects in the presence list.
        ChildEventListener myList = new ChildEventListener() {


            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                new_presence = dataSnapshot.getValue(Presence.class);
                User user = new User();
                if (new_presence.getRole().trim().equals("teacher")) {

                    user.setLastName(new_presence.getLastname());
                    user.setFirstName(new_presence.getFirstname());
                    user.setUrlImage(new_presence.getUrlImageUser());
                    user.setIdUser(new_presence.getId());
                    teachers_online.add(user);
                }
                if (new_presence.getRole().trim().equals("child")) {
                    user.setLastName(new_presence.getLastname());
                    user.setFirstName(new_presence.getFirstname());
                    user.setUrlImage(new_presence.getUrlImageUser());
                    user.setIdUser(new_presence.getId());
                    children_online.add(user);
                }
                if (new_presence.getRole().trim().equals("parent")) {
                    user.setLastName(new_presence.getLastname());
                    user.setFirstName(new_presence.getFirstname());
                    user.setUrlImage(new_presence.getUrlImageUser());
                    user.setIdUser(new_presence.getId());
                    parents_online.add(user);
                }
                Log.i("DBCount", "# of online users = " + String.valueOf(dataSnapshot.getChildrenCount()));

                for (User p : teachers_online) {
                    users.add(p);
                }
                for (User p : children_online) {
                    users.add(p);
                }
                for (User p : parents_online) {
                    users.add(p);
                }

                if (users.size() > 0) {
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    sections = new ArrayList<SimpleSectionedRecyclerViewAdapter.Section>();
                    sections.add(new SimpleSectionedRecyclerViewAdapter.Section(0, "Teachers" +
                            ""));
                    sections.add(new SimpleSectionedRecyclerViewAdapter.Section(teachers_online.size(), "Classemates"));
                    sections.add(new SimpleSectionedRecyclerViewAdapter.Section((children_online.size() + teachers_online.size()), "Parents"));
                    dummy = new SimpleSectionedRecyclerViewAdapter.Section[sections.size()];
                    if (getActivity() != null) {
                        mSectionedAdapter = new SimpleSectionedRecyclerViewAdapter(getActivity().getBaseContext(), R.layout.section_recycle_view, R.id.section_text, adapter);
                    }
                    mSectionedAdapter.setSections(sections.toArray(dummy));
                    recyclerView.setAdapter(mSectionedAdapter);
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
            public void onCancelled(DatabaseError firebaseError) {
                Log.e("DBCount", "The read failed: " + firebaseError.getMessage());
            }
        };

        listRef.addChildEventListener(myList);
    }


}