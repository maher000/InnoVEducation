package com.education.innov.innoveducation.Activities;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.education.innov.innoveducation.Entities.Child;
import com.education.innov.innoveducation.Entities.Parent;
import com.education.innov.innoveducation.Entities.Teacher;
import com.education.innov.innoveducation.Fragment.ClasseFragment;
import com.education.innov.innoveducation.Fragment.CoursesFragment;
import com.education.innov.innoveducation.Fragment.GameFragment;
import com.education.innov.innoveducation.Fragment.HomeFragment;
import com.education.innov.innoveducation.Fragment.LeftFragmentNaviguation;
import com.education.innov.innoveducation.Fragment.ProfileFragment;
import com.education.innov.innoveducation.Fragment.RightFragmentNaviguation;
import com.education.innov.innoveducation.R;
import com.education.innov.innoveducation.Utils.Config;
import com.education.innov.innoveducation.Utils.MyApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;

public class HomeActivity extends AppCompatActivity {
    private SearchView searchView;
    private Toolbar toolbar;
    private Menu m;
    DatabaseReference mBase = Config.mDatabase;
    boolean doubleBackToExitPressedOnce = false;
    private DrawerLayout drawerLayout;
    private LeftFragmentNaviguation drawerLeftFragment;
    private RightFragmentNaviguation drawerRightFragment;
    private Fragment currentFragment = null;
    private RelativeLayout chatLaout;
    private int position = R.id.tab_home;
    private BottomBar bottomBar = null;
    String RoleUser;
    Teacher teacher;
    Child child;
    SharedPreferences sharedpreferences;
    Parent parent;

    @Override
    protected void onResume() {
        super.onResume();

            System.out.println("onresume lala ");
            // finish();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        /*
                Subscribe users To receive Notification
         */
        FirebaseMessaging.getInstance().subscribeToTopic("09428835");
//store and retreive data from shared prefernces
        SharedPreferences sp = getSharedPreferences("role_user", Activity.MODE_PRIVATE);
        RoleUser = sp.getString("role", null);
        System.out.println("mon roole est" + RoleUser);
        if(RoleUser!=null)
        getUserInformation(RoleUser);
        System.out.println("**********************************   "+FirebaseAuth.getInstance().getCurrentUser().getUid());
        /* *******************************************/
        Config.mDatabase.child("child").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                System.out.println("********* " + MyApp.role);
               // if ( MyApp.role.equals("child") ){
                    Toast.makeText(getBaseContext(), MyApp.child.getClassRommId(),
                            Toast.LENGTH_LONG).show();
                    FirebaseMessaging.getInstance().subscribeToTopic(MyApp.child.getClassRommId());
                    System.out.println(FirebaseAuth.getInstance().getCurrentUser());

               // }


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
         /* *******************************************/

        /*** ToolBar ***.
         *
         */
        // Find the toolbar view inside the activity layout
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setUpToolbar();
        setUpDrawer();

        bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        if (savedInstanceState != null) {
            bottomBar.setDefaultTab(savedInstanceState.getInt("position"));
            System.out.println("MahersavedInstanceState");
        }

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                // if(currentFragment!=null)
                // getSupportFragmentManager().beginTransaction().remove(currentFragment);
                position = tabId;
                switch (tabId) {
                    case R.id.tab_classroom:
                        currentFragment = new ClasseFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_id, currentFragment)
                                .addToBackStack("gg")
                                .commit();

                        //      Toast.makeText(getApplicationContext(), tabId+"tabIdSelected", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.tab_courses:
                        currentFragment = new CoursesFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_id, currentFragment).commit();
                        break;
                    case R.id.tab_friends:
                        currentFragment = new ProfileFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_id, currentFragment).commit();
                        break;
                    case R.id.tab_bis:
                        currentFragment = new GameFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_id, currentFragment).commit();
                        break;
                    case R.id.tab_home:
                        currentFragment = new HomeFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_id, currentFragment).commit();
                        break;

                    default:
                        return;
                }
            }
        });

        bottomBar.setOnTabReselectListener(new OnTabReselectListener() {
            @Override
            public void onTabReSelected(@IdRes int tabId) {
                switch (tabId) {
                    case R.id.tab_classroom:
                        startActivity(new Intent(HomeActivity.this, MyClassRoomsActivity.class));
                        break;
                    default:
                        Toast.makeText(getApplicationContext(), tabId + "", Toast.LENGTH_LONG).show();
                }
            }
        });


        //BottomBarTab nearby = bottomBar.getTabWithId(R.id.tab_nearby);
        //nearby.setBadgeCount(5);
        chatLaout = (RelativeLayout) findViewById(R.id.badge_layout1);
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {

        savedInstanceState.putInt("position", position);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        position = savedInstanceState.getInt("position");

    }


    private void setUpToolbar() {

        //toolbar.setTitle("Associations Tunisiennes");
        toolbar.inflateMenu(R.menu.menu_main);
        //toolbar.setVisibility(View.INVISIBLE);
        m = toolbar.getMenu();
        //m.getItem(0).getsetVisible(false);
        setSupportActionBar(toolbar);


    }

    private void setUpDrawer() {

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);


        // to lock swipe left and right
        //      drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        //      drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        drawerLeftFragment = (LeftFragmentNaviguation) getSupportFragmentManager().findFragmentById(R.id.nav_drw_left_fragment);
        drawerRightFragment = (RightFragmentNaviguation) getSupportFragmentManager().findFragmentById(R.id.nav_drw_right_fragment);
        drawerLeftFragment.setUpDrawer(drawerLayout, toolbar);
        drawerRightFragment.setUpDrawer(drawerLayout, toolbar);

    }

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        // Inflate menu to add items to action bar if it is present.
        inflater.inflate(R.menu.menu_main, menu);
        // Associate searchable configuration with the SearchView
        final Menu m = menu;
        final MenuItem itemChat = m.findItem(R.id.id_chat);
        itemChat.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("viewselected");
                onOptionsItemSelected(itemChat);
            }
        });

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        int id = item.getItemId();
        switch (id) {
            case R.id.id_chat:
                drawerLayout.openDrawer(GravityCompat.END); /*Opens the Right Drawer*/
                return true;
            case R.id.action_search1:
                startActivity(new Intent(HomeActivity.this,ListClassroomsActivity.class).addFlags(FLAG_ACTIVITY_SINGLE_TOP));


        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
            //MainActivity.this.finish();


            if (doubleBackToExitPressedOnce) {
                moveTaskToBack(true);
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "clicker une autre fois pour sortir", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }


    private void getUserInformation(final String role) {
        DatabaseReference mBase = FirebaseDatabase.getInstance().getReference();
        sharedpreferences = getPreferences(MODE_APPEND);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        System.out.println("the id is " + id);
        if (role.trim().equals("child")) {
            ref = mBase.child("child").child(id);
        } else if (role.trim().equals("teacher")) {
            ref = mBase.child(Config.CHILD_TEACHER).child(id);
            System.out.println("the id is nn" + id);
        } else if (role.trim().equals("parent")) {
            ref = mBase.child("parents").child(id);
        }
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (role.trim().equals("child")) {

                    Child  child = dataSnapshot.getValue(Child.class);
                    System.out.println("priiiint" + child);
                    SharedPreferences.Editor prefsEditor = sharedpreferences.edit();
                    Gson gson = new Gson();
                    String json = gson.toJson(child);
                    prefsEditor.putString("current_user", json);
                    System.out.println("dfghjklm" + json);
                    prefsEditor.commit();
                    MyApp.getInstance(HomeActivity.this);

                } else if (role.trim().equals("teacher")) {
                    Teacher teacher = dataSnapshot.getValue(Teacher.class);
                    System.out.println(" i love you  " + teacher);
                    SharedPreferences.Editor prefsEditor = sharedpreferences.edit();
                    Gson gson = new Gson();
                    String json = gson.toJson(teacher);
                    System.out.println("dfghjklm" + json);
                    prefsEditor.putString("current_user", json);
                    prefsEditor.commit();
                    MyApp.getInstance(HomeActivity.this);
                } else if (role.trim().equals("parent")) {
                    Parent  parent = dataSnapshot.getValue(Parent.class);
                    SharedPreferences.Editor prefsEditor = sharedpreferences.edit();
                    Gson gson = new Gson();
                    String json = gson.toJson(parent);
                    prefsEditor.putString("current_user", json);
                    prefsEditor.commit();
                    MyApp.getInstance(HomeActivity.this);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
            ;
        });

    }

}
