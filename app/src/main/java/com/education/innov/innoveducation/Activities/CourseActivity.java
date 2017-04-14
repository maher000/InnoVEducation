package com.education.innov.innoveducation.Activities;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.education.innov.innoveducation.R;

public class CourseActivity extends AppCompatActivity {
    Toolbar toolbar;
    VideoView videoView;
    ImageView btnFullScreen;
    private int position = 0;
    private ProgressDialog progressDialog;
    private MediaController mediaControls;
    private boolean isFullScreen=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        mediaControls = new MediaController(CourseActivity.this);
        videoView = (VideoView) findViewById(R.id.videoView_courses);
        btnFullScreen=(ImageView) findViewById(R.id.btn_full_screen);
        btnFullScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fullScreen();
            }
        });
        setUpToolbar();
        setUpVideoView();


    }
    private void fullScreen(){
        if(isFullScreen==true){
            isFullScreen=false;
            // set the video to full screnn mode
            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            android.widget.RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) videoView.getLayoutParams();
            params.width = (int) (300*metrics.density);
            params.height = (int) (250*metrics.density);
            params.leftMargin = 30;
            videoView.setLayoutParams(params);
        }
        else {
            isFullScreen=true;
            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            android.widget.RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) videoView.getLayoutParams();
            params.width =  metrics.widthPixels;
            params.height = metrics.heightPixels;
            params.leftMargin = 0;
            videoView.setLayoutParams(params);
        }
    }
    private void setUpVideoView(){
        // create a progress bar while the video file is loading
        progressDialog = new ProgressDialog(CourseActivity.this);
        // set a title for the progress bar
        progressDialog.setTitle("course number...");
        // set a message for the progress bar
        progressDialog.setMessage("Loading...");
        //set the progress bar not cancelable on users' touch
        progressDialog.setCancelable(false);
        // show the progress bar
        progressDialog.show();

        try {
            //set the media controller in the VideoView
            videoView.setMediaController(mediaControls);
            //set the uri of the video to be played
            videoView.setVideoURI(Uri.parse("https://firebasestorage.googleapis.com/v0/b/innoveducation-a76b3.appspot.com/o/videos_users%2F20160914215632.mp4?alt=media&token=d6d55236-9576-42f0-bcd6-bf97e574c81f"));
        } catch (Exception e) {

            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        videoView.requestFocus();
        //we also set an setOnPreparedListener in order to know when the video file is ready for playback
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            public void onPrepared(MediaPlayer mediaPlayer) {
                // close the progress bar and play the video
                progressDialog.dismiss();
                //if we have a position on savedInstanceState, the video playback should start from here
                videoView.seekTo(position);
                if (position == 0) {
                    videoView.start();
                } else {
                    //if we come from a resumed activity, video playback will be paused
                    videoView.pause();

                }

            }
        });



    }
    private void setUpToolbar() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Android");
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

}
