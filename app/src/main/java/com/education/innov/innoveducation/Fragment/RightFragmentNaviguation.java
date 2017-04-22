package com.education.innov.innoveducation.Fragment;

import android.content.Context;
import android.content.Intent;
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
import com.education.innov.innoveducation.Entities.Teacher;
import com.education.innov.innoveducation.Entities.User;
import com.education.innov.innoveducation.R;
import com.education.innov.innoveducation.Utils.RecyclerItemClickListener;
import com.education.innov.innoveducation.model.NavigationDrawerItem;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class RightFragmentNaviguation extends Fragment {

    public static int Item = 0;
    public static List<NavigationDrawerItem> a = null;
    ArrayList<Teacher> teachers;
    ArrayList<Parent> parents;
    ArrayList<User> users ;
    ArrayList<Child> children ;
    Parent new_parent ;
    Teacher new_teacher;
    Child new_child;
    OnLineFrreindsAdapter adapter ;
    RecyclerView recyclerView ;
    User user ;

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_right_naviguation, container, false);

        setUpRecyclerView(view);


        return view;
    }


    private RecyclerView setUpRecyclerView(View view) {

        recyclerView = (RecyclerView) view.findViewById(R.id.FreindsOnlineRecycleView);
        a = NavigationDrawerItem.getData();


       // recyclerView.setAdapter(adapter);
        //recyclerView.getChildAt(0).findViewById(R.id.drawerList).setVisibility(View.INVISIBLE);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(getActivity(),ChatActivity.class);
                intent.putExtra("name",users.get(position).getFirstName()+" "+users.get(position).getLastName());
                intent.putExtra("id",users.get(position).getIdUser());
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
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
    }private void getListTeachers() {


    }

    private void getListChildren() {
        children = new ArrayList<>();
        parents = new ArrayList<>();
        teachers = new ArrayList<>();
        users = new ArrayList<>();
       user =new User();
        adapter = new OnLineFrreindsAdapter(getContext(), users);
        adapter.notifyDataSetChanged();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter.notifyDataSetChanged();
        FirebaseDatabase.getInstance()
                .getReference()
                .child("child").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                new_child = dataSnapshot.getValue(Child.class);
                User user = new User();
                System.out.println("classRoom");
                if (new_child != null) {
                    //creta a listener
                    children.add(new_child);

                    user.setIdUser(new_child.getIdUser());
                    user.setUrlImage(new_child.getUrlImage());
                    user.setFirstName(new_child.getFirstName());
                    user.setLastName(new_child.getLastName());
                    users.add(user);
                    System.out.println("liste de children size " + children.size());
                    System.out.println("list online"+users);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    List<SimpleSectionedRecyclerViewAdapter.Section> sections =
                            new ArrayList<SimpleSectionedRecyclerViewAdapter.Section>();

                    sections.add(new SimpleSectionedRecyclerViewAdapter.Section(0, "Teachers" +
                            ""));
                    sections.add(new SimpleSectionedRecyclerViewAdapter.Section(teachers.size(), "Classemates"));
                    sections.add(new SimpleSectionedRecyclerViewAdapter.Section((children.size()+teachers.size()), "Parents"));

                    SimpleSectionedRecyclerViewAdapter.Section[] dummy = new SimpleSectionedRecyclerViewAdapter.Section[sections.size()];
                    SimpleSectionedRecyclerViewAdapter mSectionedAdapter = new SimpleSectionedRecyclerViewAdapter(getActivity(), R.layout.section_recycle_view, R.id.section_text, adapter);
                    mSectionedAdapter.setSections(sections.toArray(dummy));
                    recyclerView.setAdapter(mSectionedAdapter);

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
                        .child("parents").addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        new_parent = dataSnapshot.getValue(Parent.class);
                        System.out.println("classRoom");
                        if (new_parent != null) {
                            //creta a listener
                            parents.add(new_parent);
                            User user = new User();
                            user.setIdUser(new_parent.getIdUser());
                            user.setUrlImage(new_parent.getUrlImage());
                            user.setFirstName(new_parent.getFirstName());
                            user.setLastName(new_parent.getLastName());
                            users.add(user);
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
        System.out.println("liste de parent size "+parents.size());
        FirebaseDatabase.getInstance()
                .getReference()
                .child("teachers").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                new_teacher = dataSnapshot.getValue(Teacher.class);
                System.out.println("classRoom");
                if (new_teacher != null) {
                    //creta a listener
                    teachers.add(new_teacher);
                    User user = new User();
                    user.setIdUser(new_teacher.getIdUser());
                    user.setUrlImage(new_teacher.getUrlImage());
                    user.setFirstName(new_teacher.getFirstName());
                    user.setLastName(new_teacher.getLastName());
                    users.add(user);
                }
                System.out.println("liste de teachers size "+teachers.size());

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




