package com.education.innov.innoveducation.Activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.education.innov.innoveducation.Fragment.ClasseFragment;
import com.education.innov.innoveducation.Fragment.GameFragment;
import com.education.innov.innoveducation.Fragment.LeftFragmentNaviguation;
import com.education.innov.innoveducation.Fragment.ListActivitiesFragment;
import com.education.innov.innoveducation.Fragment.ProfileFragment;
import com.education.innov.innoveducation.Fragment.RightFragmentNaviguation;
import com.education.innov.innoveducation.R;
import com.google.firebase.messaging.FirebaseMessaging;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

public class HomeActivity extends AppCompatActivity {
    private SearchView searchView;
    private Toolbar toolbar;
    private Menu m ;
    boolean doubleBackToExitPressedOnce = false;
    private DrawerLayout drawerLayout ;
    private LeftFragmentNaviguation drawerLeftFragment ;
    private RightFragmentNaviguation drawerRightFragment ;
    private Fragment currentFragment=null;
    private RelativeLayout chatLaout;
    private int position=R.id.tab_home;
    private BottomBar bottomBar = null;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        /*
                Subscribe users To receive Notification
         */
        FirebaseMessaging.getInstance().subscribeToTopic("09428835");



        /*** ToolBar ***.
         *
         */
        // Find the toolbar view inside the activity layout
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setUpToolbar();
        setUpDrawer();

        bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        if(savedInstanceState!=null){
            bottomBar.setDefaultTab(savedInstanceState.getInt("position"));
            System.out.println("MahersavedInstanceState");
        }

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
               // if(currentFragment!=null)
                   // getSupportFragmentManager().beginTransaction().remove(currentFragment);
                position=tabId;
                switch (tabId) {
                    case R.id.tab_classroom:
                        currentFragment=new ClasseFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_id, currentFragment)
                                .addToBackStack("gg")
                                .commit();

                        //      Toast.makeText(getApplicationContext(), tabId+"tabIdSelected", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.tab_friends:
                        currentFragment=new ProfileFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_id, currentFragment).commit();
                        break;
                    case R.id.tab_bis:
                        currentFragment=new GameFragment();
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
                        startActivity(new Intent(HomeActivity.this,MyClassRoomsActivity.class));
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
        position=savedInstanceState.getInt("position");

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

        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

        // to lock swipe left and right
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
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
        int id=item.getItemId();
        switch (id){
            case R.id.id_chat:
                drawerLayout.openDrawer(GravityCompat.END); /*Opens the Right Drawer*/
                return true;


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


}
