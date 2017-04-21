package com.education.innov.innoveducation.Activities;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.education.innov.innoveducation.Adapter.ClassePagerAdapter;
import com.education.innov.innoveducation.Adapter.ViewPagerAdapter;
import com.education.innov.innoveducation.Entities.Teacher;
import com.education.innov.innoveducation.R;
import com.education.innov.innoveducation.Utils.Config;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    LinearLayout LayoutErrorMessage;
    TextView tvErrorMsg;

    FirebaseUser mFirebaseUser =FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       if (mFirebaseUser != null) {
            Intent intent1 = new Intent(MainActivity.this, HomeActivity.class);
            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent1);
        }
        LayoutErrorMessage = (LinearLayout) findViewById(R.id.LayoutErrorMessage);
        tvErrorMsg = (TextView) findViewById(R.id.tvErrorMsg);
        ViewPager pager = (ViewPager) findViewById(R.id.VpPager);
        pager.setAdapter(buildAdapter());
        TabLayout tabLayout = (TabLayout) findViewById(R.id.event_sliding_tabs);
        tabLayout.setupWithViewPager(pager);
    }

    private PagerAdapter buildAdapter() {
        return (new ViewPagerAdapter(this, getSupportFragmentManager()));
    }

    public void ShowErorMessage(String message) {
        tvErrorMsg.setText(message);
        tvErrorMsg.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
        LayoutErrorMessage.setVisibility(View.VISIBLE);
        LayoutErrorMessage.postDelayed(new Runnable() {
            public void run() {
                LayoutErrorMessage.setVisibility(View.INVISIBLE);
            }
        }, 3000);


    }
}