package com.education.innov.innoveducation.Fragment;

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

import com.education.innov.innoveducation.Adapter.HomeAdapter;
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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;


public class ListActivitiesFragment extends Fragment {
    ArrayList<post> posts;
    ArrayList<Teacher> teachers=new ArrayList<>();
    post new_post;
    DatabaseReference mDBase = Config.mDatabase;
    User user;

    private RecyclerView mRecyclerView;
    private HomeAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    String id_user;
    Teacher owner;
    Map<post,Teacher> list;

    public static ListActivitiesFragment newInstance(int param1, String param2) {
        ListActivitiesFragment fragment = new ListActivitiesFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_activities, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.Activities_recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        getAllPosts();
        return view;

    }

    public void getAllPosts() {

        posts = new ArrayList<>();
        mAdapter = new HomeAdapter(posts,teachers, getActivity());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter.notifyDataSetChanged();
        final String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDBase.child("posts").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                new_post = dataSnapshot.getValue(post.class);
                System.out.println("posts: "+new_post);
                getUsers(new_post.getUserId(),posts.size()-1,new_post);


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

                    //    mDBase.removeEventListener(this);
                   /* if(teachers.size()==posts.size()){
                        list.put(post,teacher);
                        mRecyclerView.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();
                    }
                    */


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
    }
}












