package com.education.innov.innoveducation.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.education.innov.innoveducation.Entities.Course;
import com.education.innov.innoveducation.Entities.Lesson;
import com.education.innov.innoveducation.Fragment.CoursesFragment;
import com.education.innov.innoveducation.Fragment.HomeFragment;
import com.education.innov.innoveducation.Fragment.LeftFragmentNaviguation;
import com.education.innov.innoveducation.Fragment.ListLessonsFragment;
import com.education.innov.innoveducation.Fragment.RightFragmentNaviguation;
import com.education.innov.innoveducation.R;
import com.education.innov.innoveducation.Utils.MyApp;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class CourseActivity extends AppCompatActivity {
    Toolbar toolbar;
    VideoView videoView;
    FloatingActionButton btnAddLesson;
    private DrawerLayout drawerLayout;
    private CircleImageView image_profile_courses_activity;
    private ListLessonsFragment drawerlessonFragment;
    private TextView tvTiteCoursse, tvDescriptionVideo,
            tvdateCoursses_activity, tvNameLesson, tvFullNameCourses_activity;
    private String author, urlImageAuthor, titleCoursses, TitleLesson, description, dateLesson, urlVideo, urlMiniature;
    private ImageView course_menu_btn;
    private Course coursse;
    private Lesson lesson;
    private Intent intent;


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putParcelable("theCoursse", coursse);
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        System.out.println("restauré");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        btnAddLesson = (FloatingActionButton) findViewById(R.id.btnAddLesson);


        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_lessons);
        image_profile_courses_activity = (CircleImageView) findViewById(R.id.image_profile_courses_activity);
        tvTiteCoursse = (TextView) findViewById(R.id.tvTiteCoursse);
        tvFullNameCourses_activity = (TextView) findViewById(R.id.tvFullNameCourses_activity);
        tvDescriptionVideo = (TextView) findViewById(R.id.tv_description_courses_activity);
        tvdateCoursses_activity = (TextView) findViewById(R.id.tvdateCoursses_activity);
        tvNameLesson = (TextView) findViewById(R.id.tvNameLesson);
        setUpToolbar();


        btnAddLesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CourseActivity.this,AddLessonsActivity.class);
                intent.putExtra("id_coursse",coursse.getId());
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
        //  setUpDrawer();


        if (getCallingActivity() != null) {
            Log.d("taaag", getCallingActivity().getClassName());
            Log.d("taag",HomeActivity.class.getName());
            System.out.println("couuuucouuu this is a try " + getCallingActivity().getClassName());

            System.out.println("yes no baby " + getIntent());
            System.out.println("llkkjj" + getIntent().getClass().getName());

            if (getCallingActivity().getClassName().equals(HomeActivity.class.getName())) {
                intent = getIntent();
                coursse = intent.getExtras().getParcelable("coursse");
                MyApp.course=coursse;
                GetCoursseDetail();
            } else {

                intent = getIntent();
                coursse = MyApp.course;
                System.out.println("ppaa" + coursse);
                GetCoursseDetail();
                System.out.println("le cours est " + coursse);
                lesson = intent.getExtras().getParcelable("lesson");
                if (lesson != null) {
                    System.out.println("le cours est " + coursse);
                    System.out.println("le leçon est " + lesson);
                    getLessonDetail();
                }
            }
        }
        if(FirebaseAuth.getInstance().getCurrentUser().getUid().equals(coursse.getOwnerId())){
            btnAddLesson.setVisibility(View.VISIBLE);
        }

        drawerlessonFragment = (ListLessonsFragment) getSupportFragmentManager().findFragmentById(R.id.nav_drw_lessons_drawer);
        course_menu_btn = (ImageView) findViewById(R.id.course_menu_btn);
        course_menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(coursse!=null){
                    MyApp.course=coursse;
                    drawerLayout.openDrawer(Gravity.LEFT); /*Opens the Right Drawer*/
                }

            }
        });

    }


    private void setUpToolbar() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Course");
        toolbar.inflateMenu(R.menu.menu_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void setUpDrawer() {

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_lessons);
        drawerlessonFragment = (ListLessonsFragment) getSupportFragmentManager().findFragmentById(R.id.nav_drw_lessons_drawer);
        drawerlessonFragment.setUpDrawer(drawerLayout, toolbar);

    }

    private void GetCoursseDetail() {
        author = coursse.getAuthor();
        titleCoursses = coursse.getName();
        urlImageAuthor = coursse.getUrlImageAuthor();
        description=coursse.getDescription();



        tvTiteCoursse.setText(titleCoursses);
        tvFullNameCourses_activity.setText(author);
        tvDescriptionVideo.setText(description);
        Picasso.with(getApplicationContext()).load(urlImageAuthor).into(image_profile_courses_activity);


    }

    private void getLessonDetail() {
        System.out.println("yeeeeeeeees it works fine");
        urlMiniature = lesson.getUrlMiniature();
        urlVideo = lesson.getUrlVideo();
        TitleLesson = lesson.getTitle();
        description = lesson.getDescription();
        dateLesson = lesson.getDateCreation();

        tvDescriptionVideo.setText(description);
        tvdateCoursses_activity.setText(dateLesson);
        tvNameLesson.setText(TitleLesson);

        JCVideoPlayerStandard jcVideoPlayerStandard = (JCVideoPlayerStandard) findViewById(R.id.videoplayer);
        jcVideoPlayerStandard.setUp(lesson.getUrlVideo()
                , JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, TitleLesson);
        jcVideoPlayerStandard.thumbImageView.setImageURI(Uri.parse(lesson.getUrlMiniature()));

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        if(coursse!=null)
        savedInstanceState.putParcelable("coursse", coursse);

        // etc.
    }

}
