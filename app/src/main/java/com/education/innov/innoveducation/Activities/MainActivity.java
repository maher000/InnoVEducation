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
import com.education.innov.innoveducation.Entities.Child;
import com.education.innov.innoveducation.Entities.Parent;
import com.education.innov.innoveducation.Entities.Teacher;
import com.education.innov.innoveducation.R;
import com.education.innov.innoveducation.Utils.ComplexPreferences;
import com.education.innov.innoveducation.Utils.Config;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class MainActivity extends AppCompatActivity {
    private LinearLayout LayoutErrorMessage;
    private TextView tvErrorMsg;
    private DatabaseReference mDBase = Config.mDatabase;
    private SharedPreferences sharedpreferences;
    private Teacher teacher;
    private Child child;
    private Parent parent;

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

    private void getUserInformation() {
        final String id=FirebaseAuth.getInstance().getCurrentUser().getUid();
        final Query ref_teacher = mDBase.child("teachers").orderByKey().equalTo(id);
        final Query ref_child = mDBase.child("child").orderByKey().equalTo(id);
        final Query ref_parent = mDBase.child("parents").orderByKey().equalTo(id);
        System.out.println("getUserInformation");
        ref_teacher.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.exists()) {
                    teacher = dataSnapshot.getValue(Teacher.class);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("role", "teacher");
                    editor.commit();
                    System.out.println("le role est teacher");
                    ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(
                            MainActivity.this, "mypref", Context.MODE_PRIVATE);
                    complexPreferences.putObject("current_user", teacher);
                    complexPreferences.commit();
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    return;
                } else
                    ref_teacher.removeEventListener(this);
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        ref_parent.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if (dataSnapshot.exists()) {
                    parent = dataSnapshot.getValue(Parent.class);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("role", "parent");
                    editor.commit();
                    System.out.println("le role est parent");
                    ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(
                            MainActivity.this, "mypref", MainActivity.this.MODE_PRIVATE);
                    complexPreferences.putObject("current_user", parent);
                    complexPreferences.commit();
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    return;

                } else
                    ref_parent.removeEventListener(this);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        ref_child.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if (dataSnapshot.exists()) {
                    child = dataSnapshot.getValue(Child.class);
                    if(child.getActive().trim().equals("yes".toLowerCase())) {
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("role", "child");
                        editor.commit();
                        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(
                                MainActivity.this, "mypref", Context.MODE_PRIVATE);
                        complexPreferences.putObject("current_user", child);
                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }else {
                        Intent intent = new Intent(MainActivity.this, CompleteInformationChildActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }

                }}

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

    }
}