package com.education.innov.innoveducation.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.education.innov.innoveducation.Activities.HomeActivity;
import com.education.innov.innoveducation.Adapter.HomeAdapter;
import com.education.innov.innoveducation.Entities.Child;
import com.education.innov.innoveducation.Entities.Parent;
import com.education.innov.innoveducation.Entities.Teacher;
import com.education.innov.innoveducation.Entities.User;
import com.education.innov.innoveducation.Entities.post;
import com.education.innov.innoveducation.R;
import com.education.innov.innoveducation.Utils.Config;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ListActivitiesFragment extends Fragment {
    ArrayList<post> posts = new ArrayList<>();
    ;
    ArrayList<Teacher> teachers = new ArrayList<>();
    post new_post;
    DatabaseReference mDBase = Config.mDatabase;

    private RecyclerView mRecyclerView;
    private HomeAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    String id_user;
    Teacher owner;
    Object obj;
    Map<post, Teacher> list;

    public static ListActivitiesFragment newInstance(int param1, String param2) {
        ListActivitiesFragment fragment = new ListActivitiesFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ;


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_activities, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.Activities_recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        getPosts();
        getInfomationUser();


        return view;


    }

   /* public void getAllPosts() {

        posts = new ArrayList<>();
        mAdapter = new HomeAdapter(posts,teachers, getActivity());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter.notifyDataSetChanged();
        final String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        if(HomeActivity.activeClassroom!=null) {
            mDBase.child("posts").orderByChild("classroomId").equalTo(HomeActivity.activeClassroom).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    new_post = dataSnapshot.getValue(post.class);
                    System.out.println("posts: " + new_post);
                    getUsers(new_post.getUserId(), posts.size() - 1, new_post);


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


    private void getUsers(String id, final int position, final post post){
        mDBase.child(Config.CHILD_TEACHER).child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println(dataSnapshot.getValue()+"teacher");

                try{

                    Teacher teacher  = dataSnapshot.getValue(Teacher.class);
                    System.out.println("teacher :"+teacher);
                    //teachers.add(position,teacher);
                    post.setOwner(teacher);
                    posts.add(post);
                    System.out.println("typeMaher"+posts.get(posts.size()-1).getType());
                    mRecyclerView.setAdapter(mAdapter);



                } catch (Throwable e) {
                    Log.e("errorTeacher",e.getMessage());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public Teacher getUser(final String UserID) {


        return owner;
    } */


    public void getPosts() {

        posts = new ArrayList<>();
        mAdapter = new HomeAdapter(posts, teachers, getActivity());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter.notifyDataSetChanged();
        final String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance()
                .getReference()
                .child("posts").addValueEventListener(

                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            new_post = child.getValue(post.class);

                        }
                        posts.add(new_post);
                        mRecyclerView.setAdapter(mAdapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

  /*  private void getUsers(String id, final post post) {
        mDBase.child(Config.CHILD_TEACHER).orderByChild("id").equalTo(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println(dataSnapshot.getValue() + "teacher");

                try {

                    Teacher teacher = dataSnapshot.getValue(Teacher.class);
                    System.out.println("teacher :" + teacher);
                    //teachers.add(position,teacher);
                    post.setOwner(teacher);
                    posts.add(post);
                    mRecyclerView.setAdapter(mAdapter);


                } catch (Throwable e) {
                    Log.e("errorTeacher", e.getMessage());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });  } */

    public void getInfomationUser() {

        SharedPreferences sp = getActivity().getSharedPreferences("role_user", Activity.MODE_PRIVATE);
        String role = sp.getString("role", null);
        SharedPreferences mPrefs = getActivity().getPreferences(Context.MODE_PRIVATE);
        Gson gson = new Gson();
        System.out.println("le role est" + role);
        String json = mPrefs.getString("current_user", "");
        if (role == "child") {
            obj = gson.fromJson(json, Child.class);
            System.out.println("this is information of user connected" + obj);
            if(Config.currentChild==null)
                Config.currentChild=gson.fromJson(json, Child.class);
        } else if (role.trim().equals("teacher")) {
            System.out.println("bras bouk");
            obj = gson.fromJson(json, Teacher.class);
            if(Config.currentTeacher==null)
                Config.currentTeacher=gson.fromJson(json, Teacher.class);
            System.out.println("this is information of user connected" + gson.fromJson(json, Teacher.class));
            System.out.println("this is information of user connected" + obj);
        } else if (role == "parent") {
            if(Config.currentParent==null)
                Config.currentParent=gson.fromJson(json, Parent.class);
            obj = gson.fromJson(json, Parent.class);
            System.out.println("this is information of user connected" + obj);
        }
    }
}
