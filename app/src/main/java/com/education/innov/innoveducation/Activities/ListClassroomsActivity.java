package com.education.innov.innoveducation.Activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.education.innov.innoveducation.Adapter.SearchClassAdapter;
import com.education.innov.innoveducation.Entities.ClassRoom;
import com.education.innov.innoveducation.Entities.ClassroomRequest;
import com.education.innov.innoveducation.R;
import com.education.innov.innoveducation.Utils.Config;
import com.education.innov.innoveducation.Utils.RecyclerItemClickListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ListClassroomsActivity extends ActionBarActivity {


    private RecyclerView mRecyclerView;
    private SearchClassAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public DatabaseReference mDatabase;
    private String mUserId;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private ArrayList<ClassRoom> classRooms = new ArrayList<>();
    private ArrayList<ClassroomRequest> list_request = new ArrayList<>();
    private ArrayList<ClassRoom> Search_classRooms = new ArrayList<>();
    private Toolbar toolbar;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_classrooms);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        handleIntent(getIntent());
        initViews();
    }

    private void setUpToolbar() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_search);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


    }

    private void initViews() {

        mRecyclerView = (RecyclerView) findViewById(R.id.myclasses_recycler_view);
        mLayoutManager = new LinearLayoutManager(this);
        setUpToolbar();
        mRecyclerView.setLayoutManager(mLayoutManager);
        //Adapter is created in the last step

        mAdapter = new SearchClassAdapter(this, classRooms,list_request);
        getClassRooms();
        getListRequest();
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
            }

            @Override
            public void onLongItemClick(View view, int position) {
            }
        }));
    }

    private void getClassRooms() {

        classRooms.clear();

        mRecyclerView.setAdapter(mAdapter);
        mDatabase.child(Config.CHILD_CLASSROOM).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ClassRoom classRoom = dataSnapshot.getValue(ClassRoom.class);
                if (classRoom != null) {
                    classRooms.add(classRoom);
                    mRecyclerView.setAdapter(mAdapter);
                    mDatabase.removeEventListener(this);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        // Inflate menu to add items to action bar if it is present.
        inflater.inflate(R.menu.menu_search, menu);
        // Associate searchable configuration with the SearchView
        final Menu m = menu;


        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconified(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                SearchClassRoom(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                SearchClassRoom(newText);
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        int id = item.getItemId();
        switch (id) {
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void SearchClassRoom(String text) {
        Search_classRooms.clear();
        mAdapter = new SearchClassAdapter(this, Search_classRooms,list_request);
        mRecyclerView.setAdapter(mAdapter);
        if (text.equals("")) {
            getClassRooms();
            return;
        }
        if (classRooms.size() > 0) {
            for (ClassRoom classe : classRooms) {
                if (classe.getName().toLowerCase().contains(text.toLowerCase())) {
                    Search_classRooms.add(classe);
                }
            }
        }
        //     events_api_search = midle;
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search
        }
    }

    private void getListRequest() {
        mDatabase.child("classroomRequest").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if (dataSnapshot != null) {
                    ClassroomRequest request = dataSnapshot.getValue(ClassroomRequest.class);
                    if (request.getSenderId().trim().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                        list_request.add(request);
                        System.out.println("liiisttt " + list_request);
                        mRecyclerView.setAdapter(mAdapter);
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot != null) {


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
        });
    }


    }