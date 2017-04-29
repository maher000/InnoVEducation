package com.education.innov.innoveducation.Activities;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.education.innov.innoveducation.Entities.Child;
import com.education.innov.innoveducation.Entities.ClassRoom;
import com.education.innov.innoveducation.Entities.Notification;
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
import com.education.innov.innoveducation.Utils.psuhNotificationAllUsers;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.Date;

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
    private Teacher teacher;
    private Child child;
    private Parent parent;
    private Presence presence;
    private String firstname, lastname, id, urlImage;
    private SharedPreferences shared;
    private SharedPreferences sp;
    private ComplexPreferences complexPreferences;
    private ClassRoom classRoom;
    String id_class_room_child ;
    private RelativeLayout menu_chat;
    private Button btnChat, btnNotification;
    private EditText edtSearch;
    private DatabaseReference userRef ;
    private TextView badge_notification_2;
    private int count=0;

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
        menu_chat = (RelativeLayout) findViewById(R.id.relative_layout_item_count);
        btnChat = (Button) findViewById(R.id.button1);
        btnNotification = (Button) findViewById(R.id.button2);
        edtSearch = (EditText) findViewById(R.id.edt_menu_search);
        badge_notification_2 = (TextView) findViewById(R.id.badge_notification_2);
        FirebaseMessaging.getInstance().subscribeToTopic("09428835");
        //store and retreive data from shared prefernces
        shared = getSharedPreferences("role_user", Activity.MODE_PRIVATE);
        Role = shared.getString("role", null);
        if (Role != null) {
            getInfomationUser();
            setUserOnline();
            subscribeToTopic();
        }

        System.out.println("**********************************   " + FirebaseAuth.getInstance().getCurrentUser().getUid());
        /* *******************************************/
        Config.mDatabase.child("child").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
/*                child = dataSnapshot.getValue(Child.class);
                if (child != null) {
                    if (child.getClassRommId() != null) {
                        System.out.println("********* " + Role);
                        Toast.makeText(getBaseContext(), child.getClassRommId(),
                                Toast.LENGTH_LONG).show();
                        FirebaseMessaging.getInstance().subscribeToTopic(child.getClassRommId());
                        System.out.println(FirebaseAuth.getInstance().getCurrentUser());
                        */




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

                        switch (Role) {
                            case "teacher":

                                System.out.println("this is your classRoom" + classRoom);
                                if (classRoom != null) {
                                    currentFragment = new ClasseFragment();
                                    getSupportFragmentManager().beginTransaction().replace(R.id.container_id, currentFragment)
                                            .addToBackStack("gg")
                                            .commit();
                                } else {
                                    startActivity(new Intent(HomeActivity.this, MyClassRoomsActivity.class));
                                }
                                break;
                            case "parent":
                                if (classRoom != null) {
                                    currentFragment = new ClasseFragment();
                                    getSupportFragmentManager().beginTransaction().replace(R.id.container_id, currentFragment)
                                            .addToBackStack("gg")
                                            .commit();
                                } else {
                                    startActivity(new Intent(HomeActivity.this, MyClassRoomsActivity.class));
                                }
                                break;
                            case "child":
                                if (classRoom != null) {
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
                        switch (Role) {
                            case "teacher":
                                startActivity(new Intent(HomeActivity.this, MyClassRoomsActivity.class));
                                break;
                            case "parent":
                                startActivity(new Intent(HomeActivity.this, MyClassRoomsActivity.class));
                                break;
                        }
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
        inflater.inflate(R.menu.menu_main1, menu);
        // Associate searchable configuration with the SearchView

        final MenuItem itemChat = menu.findItem(R.id.id_chat);
        MenuItemCompat.setActionView(itemChat, R.layout.menu_layout_notification1);
        menu_chat = (RelativeLayout) MenuItemCompat.getActionView(itemChat);
        btnChat = (Button) menu_chat.findViewById(R.id.button1);
        edtSearch = (EditText) menu_chat.findViewById(R.id.edt_menu_search);
        btnNotification = (Button) menu_chat.findViewById(R.id.button2);
        badge_notification_2=(TextView) menu_chat.findViewById(R.id.badge_notification_2);
        getNotifications();

        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("maherBtn");
                drawerLayout.openDrawer(GravityCompat.END);

            }
        });


        btnNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("maherBtn");
                System.out.println("maherSearch");
                startActivity(new Intent(HomeActivity.this, NotificationsActivity.class).addFlags(FLAG_ACTIVITY_SINGLE_TOP));

            }
        });
        edtSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, ListClassroomsActivity.class).addFlags(FLAG_ACTIVITY_SINGLE_TOP));
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
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
            Toast.makeText(this, "Double click to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }

    public void getInfomationUser() {
        System.out.println("role tttt" + Role);
        if (Role != null) {
            switch (Role.trim()) {
                case "teacher":
                    complexPreferences = ComplexPreferences.getComplexPreferences(this, "mypref", this.MODE_PRIVATE);
                    teacher = complexPreferences.getObject("current_user", Teacher.class);
                    firstname = teacher.getFirstName();
                    lastname = teacher.getLastName();
                    urlImage = teacher.getUrlImage();
                    System.out.println(firstname + lastname + urlImage + "syriiine is trying");
                    System.out.println(teacher + "tttttttttttttt");
                    classRoom = complexPreferences.getObject("my_class_room", ClassRoom.class);
                    break;
                case "parent":
                    complexPreferences = ComplexPreferences.getComplexPreferences(this, "mypref", this.MODE_PRIVATE);
                    parent = complexPreferences.getObject("current_user", Parent.class);
                    firstname = parent.getFirstName();
                    lastname = parent.getLastName();
                    urlImage = parent.getUrlImage();
                    classRoom = complexPreferences.getObject("my_class_room", ClassRoom.class);
                    break;
                case "child":
                    complexPreferences = ComplexPreferences.getComplexPreferences(this, "mypref", this.MODE_PRIVATE);
                    child = complexPreferences.getObject("current_user", Child.class);
                    System.out.println(child + "ffggdds");
                    firstname = child.getFirstName();
                    lastname = child.getLastName();
                    urlImage = child.getUrlImage();
                    id_class_room_child = child.getClassRommId();
                    getClassRoomChild();
                    break;
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
        switch (Role){
            case "teacher":
                userRef = FirebaseDatabase.getInstance()
                        .getReference().child("teachers").child(id).child("connected");
                break;
            case "child":
                userRef = FirebaseDatabase.getInstance()
                        .getReference().child("child").child(id).child("connected");
                break;
            case "parent":
                userRef = FirebaseDatabase.getInstance()
                        .getReference().child("parents").child(id).child("connected");
                break;
        }

        ValueEventListener myPresence = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                // Remove ourselves when we disconnect.
                if (snapshot.getValue(Boolean.class)) {
                    userRef.onDisconnect().removeValue();
                    userRef.setValue("yes");
                }
            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {
                Log.e("DBCount", "The read failed: " + firebaseError.getMessage());
            }
        };

        presenceRef.addValueEventListener(myPresence);


    }

    private void subscribeToTopic() {

        final String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        System.out.println("loginId" + id);
        final Query ref_teacher = mBase.child("teachers").orderByKey().equalTo(id);
        final Query ref_child = mBase.child("child").orderByKey().equalTo(id);
        final Query ref_parent = mBase.child("parents").orderByKey().equalTo(id);
       final String token = FirebaseInstanceId.getInstance().getToken();


        if(Role.equals("teacher"))
        ref_teacher.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.exists()) {
                    mBase.child("teachers").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("token").setValue(token);
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
        else if(Role.equals("parent"))
        ref_parent.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if (dataSnapshot.exists()) {
                    mBase.child("parents").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("token").setValue(token);}}
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
        else if(Role.equals("child"))
        ref_child.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if (dataSnapshot.exists()) {

                    child = dataSnapshot.getValue(Child.class);
                    if(child.getTopic() != null)
                    FirebaseMessaging.getInstance().subscribeToTopic(child.getTopic());
                    System.out.println(child.getTopic()+"childTopic");
                    mBase.child("child").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("token").setValue(token);
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
    public void getClassRoomChild() {
        if(id_class_room_child != null)
        mBase.child("classrooms").child(id_class_room_child).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    classRoom = dataSnapshot.getValue(ClassRoom.class);
                    complexPreferences.putObject("my_class_room", classRoom);
                    complexPreferences.commit();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    private void getNotifications(){
        if(Role.equals("child")){
            if(child!=null){
                if(child.getClassRommId()!=null)
                    mBase.child("notification").orderByChild("classRoomId").equalTo(child.getClassRommId()).addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                            Notification not=dataSnapshot.getValue(Notification.class);
                            if(not.getChecked().equals("no")&& !not.getType().equals("join")){
                                count+=1;
                                badge_notification_2.setText(""+(int)count);
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
    }
        /*-------------------------------*/

}
