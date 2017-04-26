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
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.education.innov.innoveducation.Entities.Child;
import com.education.innov.innoveducation.Entities.ClassRoom;
import com.education.innov.innoveducation.Entities.Parent;
import com.education.innov.innoveducation.Entities.Presence;
import com.education.innov.innoveducation.Entities.Teacher;
import com.education.innov.innoveducation.Fragment.ClasseFragment;
import com.education.innov.innoveducation.Fragment.CoursesFragment;
import com.education.innov.innoveducation.Fragment.GameFragment;
import com.education.innov.innoveducation.Fragment.HomeFragment;
import com.education.innov.innoveducation.Fragment.LeftFragmentNaviguation;
import com.education.innov.innoveducation.Fragment.ProfileFragment;
import com.education.innov.innoveducation.Fragment.RightFragmentNaviguation;
import com.education.innov.innoveducation.R;
import com.education.innov.innoveducation.Utils.ComplexPreferences;
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
    private DatabaseReference mBase = Config.mDatabase;
    boolean doubleBackToExitPressedOnce = false;
    private DrawerLayout drawerLayout;
    private LeftFragmentNaviguation drawerLeftFragment;
    private RightFragmentNaviguation drawerRightFragment;
    private Fragment currentFragment = null;
    private RelativeLayout chatLaout;
    private int position = R.id.tab_home;
    private BottomBar bottomBar = null;
    private String Role;
    private static Gson gson = new Gson();
    private static String json;
    private Teacher teacher;
    private Child child;
    private Parent parent;
    private Presence presence;
    private String firstname, lastname, id, urlImage;
    private SharedPreferences shared;
    private SharedPreferences sp;
    private ComplexPreferences complexPreferences;
    private ComplexPreferences complexPreferencesClassRoom;
    private ClassRoom classRoom ;

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
        shared = getSharedPreferences("role_user", Activity.MODE_PRIVATE);
        Role = shared.getString("role", null);
        if (Role != null) {
            getInfomationUser();
            setUserOnline();
        }
        System.out.println("**********************************   " + FirebaseAuth.getInstance().getCurrentUser().getUid());
        /* *******************************************/
        Config.mDatabase.child("child").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                child = dataSnapshot.getValue(Child.class);
                if (child != null) {
                    if (child.getClassRommId() != null) {
                        System.out.println("********* " + Role);
                        Toast.makeText(getBaseContext(), child.getClassRommId(),
                                Toast.LENGTH_LONG).show();
                        FirebaseMessaging.getInstance().subscribeToTopic(child.getClassRommId());
                        System.out.println(FirebaseAuth.getInstance().getCurrentUser());
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
        });
         /* *******************************************/

        /*** ToolBar ***.
         *
         */
        // Find the toolbar view inside the activity layout
        toolbar = (Toolbar) findViewById(R.id.toolbar);
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

                        switch (Role){
                            case  "teacher" :
                                complexPreferencesClassRoom = ComplexPreferences.getComplexPreferences(HomeActivity.this, "prefs_classrooms", MODE_PRIVATE);
                                classRoom = complexPreferencesClassRoom.getObject("my_class_room", ClassRoom.class);

                                if(classRoom != null) {
                                    currentFragment = new ClasseFragment();
                                    getSupportFragmentManager().beginTransaction().replace(R.id.container_id, currentFragment)
                                            .addToBackStack("gg")
                                            .commit();
                                }
                                else {
                                    startActivity(new Intent(HomeActivity.this, MyClassRoomsActivity.class));
                                }
                                break;
                            case "parent" :
                                complexPreferencesClassRoom = ComplexPreferences.getComplexPreferences(HomeActivity.this, "prefs_classrooms", MODE_PRIVATE);
                                classRoom = complexPreferencesClassRoom.getObject("my_class_room", ClassRoom.class);

                                if(classRoom != null) {
                                    currentFragment = new ClasseFragment();
                                    getSupportFragmentManager().beginTransaction().replace(R.id.container_id, currentFragment)
                                            .addToBackStack("gg")
                                            .commit();
                                }
                                else {
                                    startActivity(new Intent(HomeActivity.this, MyClassRoomsActivity.class));
                                }
                                break;
                            case "child" :
                                complexPreferencesClassRoom = ComplexPreferences.getComplexPreferences(HomeActivity.this, "prefs_classrooms", MODE_PRIVATE);
                                classRoom = complexPreferencesClassRoom.getObject("my_class_room", ClassRoom.class);

                                if(classRoom != null) {
                                    currentFragment = new ClasseFragment();
                                    getSupportFragmentManager().beginTransaction().replace(R.id.container_id, currentFragment)
                                            .addToBackStack("gg")
                                            .commit();
                                }
                               break;
                        }


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
                startActivity(new Intent(HomeActivity.this, ListClassroomsActivity.class).addFlags(FLAG_ACTIVITY_SINGLE_TOP));


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

    public void getInfomationUser() {
        sp = getPreferences(Context.MODE_PRIVATE);
        json = sp.getString("current_user", "");
        if (Role != null) {
            json = sp.getString("current_user", "");
            System.out.println(json + "ffggdd");
            if (json != null) {

                switch (Role.trim()) {
                    case "teacher":
                        complexPreferences = ComplexPreferences.getComplexPreferences(this, "mypref", this.MODE_PRIVATE);
                        teacher = complexPreferences.getObject("current_user", Teacher.class);
                        firstname = teacher.getFirstName();
                        lastname = teacher.getLastName();
                        urlImage = teacher.getUrlImage();
                        System.out.println(firstname + lastname + urlImage + "syriiine is trying");
                        System.out.println(teacher + "tttttttttttttt");
                        break;
                    case "parent":
                        complexPreferences = ComplexPreferences.getComplexPreferences(this, "mypref", this.MODE_PRIVATE);
                        parent = complexPreferences.getObject("current_user", Parent.class);
                        firstname = parent.getFirstName();
                        lastname = parent.getLastName();
                        urlImage = parent.getUrlImage();
                        break;
                    case "child":
                        complexPreferences = ComplexPreferences.getComplexPreferences(this, "mypref", MODE_PRIVATE);
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

    private void setUserOnline() {
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        presence = new Presence(id, lastname, firstname, urlImage, "true", Role);
        presence.setId(id);
        presence.setConnected("true");
        presence.setLastname(lastname);
        presence.setFirstname(firstname);
        presence.setUrlImageUser(urlImage);
        presence.setRole(Role);

        final DatabaseReference presenceRef = FirebaseDatabase.getInstance()
                .getReference().child(".info/connected");
        final DatabaseReference userRef = FirebaseDatabase.getInstance()
                .getReference().child("presence").child(id);

        ValueEventListener myPresence = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                // Remove ourselves when we disconnect.
                if (snapshot.getValue(Boolean.class)) {
                    userRef.onDisconnect().removeValue();
                    userRef.setValue(presence);
                }
            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {
                Log.e("DBCount", "The read failed: " + firebaseError.getMessage());
            }
        };

        presenceRef.addValueEventListener(myPresence);


    }
}