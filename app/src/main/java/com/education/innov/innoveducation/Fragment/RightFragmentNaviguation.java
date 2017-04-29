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
import com.education.innov.innoveducation.Entities.ClassRoom;
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
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RightFragmentNaviguation extends Fragment {

    public static int Item = 0;
    public static List<NavigationDrawerItem> a = null;
    private ArrayList<Teacher> teachers = new ArrayList<>();
    private ArrayList<Parent> parents = new ArrayList<>();
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Child> children = new ArrayList<>();
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
    private ArrayList<String> list_classe_user = new ArrayList<>();

    private OnLineFrreindsAdapter adapter;
    private RecyclerView recyclerView;
    private Presence presence;
    private Presence new_presence;
    private SharedPreferences shared;
    SharedPreferences sp;
    private User user;
    String Role, firstname, lastname, urlImage, id;
    private static Gson gson = new Gson();
    private static String json;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String active_classe_room;
    private ClassRoom classRoom;

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

        a = NavigationDrawerItem.getData(Role);


        // recyclerView.setAdapter(adapter);
        //recyclerView.getChildAt(0).findViewById(R.id.drawerList).setVisibility(View.INVISIBLE);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), ChatActivity.class);
              //  if (position < users.size() && position <mSectionedAdapter.getItemCount()) {
                    intent.putExtra("name", users.get(mSectionedAdapter.sectionedPositionToPosition(position)).getFirstName() + " " + users.get(mSectionedAdapter.sectionedPositionToPosition
                            (position)).getLastName());
                    intent.putExtra("id", users.get(mSectionedAdapter.sectionedPositionToPosition(position)).getIdUser());
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
               // }
            }
            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
        getListChildren();
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
        if (Role != null) {
            complexPreferences = ComplexPreferences.getComplexPreferences(getActivity(), "mypref", getActivity().MODE_PRIVATE);
            switch (Role.trim()) {
                case "teacher":
                    teacher = complexPreferences.getObject("current_user", Teacher.class);
                    firstname = teacher.getFirstName();
                    lastname = teacher.getLastName();
                    urlImage = teacher.getUrlImage();
                    System.out.println(firstname + lastname + urlImage + "syriiine is trying");
                    System.out.println(teacher + "tttttttttttttt");
                    classRoom = complexPreferences.getObject("my_class_room", ClassRoom.class);
                    if (classRoom != null)
                        active_classe_room = classRoom.getId();
                    break;
                case "parent":
                    parent = complexPreferences.getObject("current_user", Parent.class);
                    firstname = parent.getFirstName();
                    lastname = parent.getLastName();
                    urlImage = parent.getUrlImage();
                    classRoom = complexPreferences.getObject("my_class_room", ClassRoom.class);
                    if (classRoom != null)
                        active_classe_room = classRoom.getId();
                    break;
                case "child":
                    child = complexPreferences.getObject("current_user", Child.class);
                    System.out.println(child + "ffggdds");
                    firstname = child.getFirstName();
                    lastname = child.getLastName();
                    urlImage = child.getUrlImage();
                    classRoom = complexPreferences.getObject("my_class_room", ClassRoom.class);
                    active_classe_room = child.getClassRommId();
                    break;
            }
        }
    }

    private void getListChildren() {
        getInfomationUser();
        children = new ArrayList<>();
        parents = new ArrayList<>();
        teachers = new ArrayList<>();
        users = new ArrayList<>();
        user = new User();
        adapter = new OnLineFrreindsAdapter(getContext(), users);
        adapter.notifyDataSetChanged();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter.notifyDataSetChanged();
        System.out.println("active class est" + active_classe_room);
        if (active_classe_room != null)
            FirebaseDatabase.getInstance()
                    .getReference().child("child").orderByChild("classRommId").equalTo(active_classe_room).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    new_child = dataSnapshot.getValue(Child.class);

                    System.out.println("classRoom");
                    if (new_child != null) {
                        if (!(new_child.getIdUser().trim().equals(FirebaseAuth.getInstance().getCurrentUser().getUid()))) {
                            User user = new User();
                            //creta a listener
                            children.add(new_child);
                            user.setConnected(new_child.getConnected());
                            user.setIdUser(new_child.getIdUser());
                            user.setUrlImage(new_child.getUrlImage());
                            user.setFirstName(new_child.getFirstName());
                            user.setLastName(new_child.getLastName());
                            users.add(user);
                            System.out.println("liste de children size " + children.size());
                            System.out.println("list online" + users);
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            List<SimpleSectionedRecyclerViewAdapter.Section> sections =
                                    new ArrayList<SimpleSectionedRecyclerViewAdapter.Section>();

                        }

                    }
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

                @Override

                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                }
            });
        FirebaseDatabase.getInstance()
                .getReference()
                .
                        child("parents").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                new_parent = dataSnapshot.getValue(Parent.class);
                System.out.println("classRoom");
                if (new_parent != null) {
                    //creta a listener
                    if (!(new_parent.getIdUser().trim().equals(FirebaseAuth.getInstance().getCurrentUser().getUid()))) {
                        Map<String, String> objectMap = (HashMap<String, String>)
                                new_parent.getClassRooms();
                        if (objectMap != null)
                            if (objectMap.containsValue(active_classe_room)) {
                                User user = new User();
                                parents.add(new_parent);
                                user.setConnected(new_parent.getConnected());
                                user.setIdUser(new_parent.getIdUser());
                                user.setUrlImage(new_parent.getUrlImage());
                                user.setFirstName(new_parent.getFirstName());
                                user.setLastName(new_parent.getLastName());
                                users.add(user);
                            }
                    }

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
        System.out.println("liste de parent size " + parents.size());
        FirebaseDatabase.getInstance()
                .getReference()
                .child("teachers").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                new_teacher = dataSnapshot.getValue(Teacher.class);
                System.out.println("classRoom");
                if (new_teacher != null) {
                    //creta a listener

                    User user = new User();
                    Map<String, String> objectMap = (HashMap<String, String>)
                            new_teacher.getClassRooms();
                    if (objectMap != null)
                        if (!(new_teacher.getIdUser().trim().equals(FirebaseAuth.getInstance().getCurrentUser().getUid()))) {
                        if (objectMap.containsValue(active_classe_room)) {
                            teachers.add(new_teacher);
                            user.setConnected(new_teacher.getConnected());
                            user.setIdUser(new_teacher.getIdUser());
                            user.setUrlImage(new_teacher.getUrlImage());
                            user.setFirstName(new_teacher.getFirstName());
                            user.setLastName(new_teacher.getLastName());
                            users.add(user);
                        }

                }
                System.out.println("liste de teachers size " + teachers.size());
            }
                if (users.size() > 0) {

                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    sections = new ArrayList<SimpleSectionedRecyclerViewAdapter.Section>();
                    sections.add(new SimpleSectionedRecyclerViewAdapter.Section(0, "Classmates" +
                            ""));
                    sections.add(new SimpleSectionedRecyclerViewAdapter.Section(children.size(), "Parents"));
                    sections.add(new SimpleSectionedRecyclerViewAdapter.Section((children.size() + parents.size()), "Teachers"));
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
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }


}
