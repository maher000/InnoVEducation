package com.education.innov.innoveducation.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.session.MediaSessionManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.education.innov.innoveducation.Activities.AddChildActivity;
import com.education.innov.innoveducation.Activities.MainActivity;
import com.education.innov.innoveducation.Adapter.MenuLeftNaviguationAdapter;
import com.education.innov.innoveducation.Entities.Presence;
import com.education.innov.innoveducation.R;
import com.education.innov.innoveducation.Utils.ComplexPreferences;
import com.education.innov.innoveducation.Utils.Config;
import com.education.innov.innoveducation.Utils.RecyclerItemClickListener;
import com.education.innov.innoveducation.model.NavigationDrawerItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;


public class LeftFragmentNaviguation extends Fragment {

    public static int Item = 0;
    public static List<NavigationDrawerItem> a = null;

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    ComplexPreferences co ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_left_naviguation, container, false);
        setUpRecyclerView(view);
        ImageView DownloadImage = ((ImageView) view.findViewById(R.id.profileImage));


        // email
        String email =  "laura.delcapo@gmail.com";
        String imageUrl = "";
        //String tel = user.get(SessionManager.KEY_tel);


        TextView nameView = (TextView) view.findViewById(R.id.nameAndSurname);
        TextView emailView = (TextView) view.findViewById(R.id.email);
        emailView.setText(email);
        nameView.setText("syrine");

      //  Picasso.with(view.getContext())
        //        .load(imageUrl)
          //      .into(DownloadImage);

        return view;
    }



    private RecyclerView setUpRecyclerView(View view) {

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.drawerList);
        a = NavigationDrawerItem.getData();

        MenuLeftNaviguationAdapter adapter = new MenuLeftNaviguationAdapter(getActivity(), a);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //recyclerView.getChildAt(0).findViewById(R.id.drawerList).setVisibility(View.INVISIBLE);
       recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                LeftFragmentNaviguation.Item = position;
                System.out.println(position+"possyrine");
                switch (position){
                    case 1 : addChild();
                        break;
                    case 5 : logOut();
                        break;
                    default:
                        break;
                }
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
    private void logOut(){

        setUserOffline();
        FirebaseAuth.getInstance().signOut();
        SharedPreferences sharedpreferences = getActivity().getPreferences(getActivity().MODE_APPEND);
        SharedPreferences.Editor prefsEditor = sharedpreferences.edit();
        prefsEditor.remove("role_user");
        prefsEditor.remove("current_user");
        prefsEditor.commit();
        startActivity(new Intent(getActivity(), MainActivity.class));
}
    private void addChild(){
        startActivity(new Intent(getActivity(), AddChildActivity.class));
    }

    private void setUserOffline() {
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();

        final DatabaseReference presenceRef = FirebaseDatabase.getInstance()
                .getReference().child(".info/connected");
        final DatabaseReference userRef = FirebaseDatabase.getInstance()
                .getReference().child("presence").child(id);

        ValueEventListener myPresence = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                // Remove ourselves when we disconnect.

                    userRef.onDisconnect().removeValue();

            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {
                Log.e("DBCount", "The read failed: " + firebaseError.getMessage());
            }
        };

        presenceRef.addValueEventListener(myPresence);


    }
}