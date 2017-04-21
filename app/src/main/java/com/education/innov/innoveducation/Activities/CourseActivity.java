package com.education.innov.innoveducation.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
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

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class CourseActivity extends AppCompatActivity {
    Toolbar toolbar;
    VideoView videoView;
    FloatingActionButton btnAddLesson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        btnAddLesson = (FloatingActionButton) findViewById(R.id.btnAddLesson);
        btnAddLesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CourseActivity.this, AddLessonsActivity.class);
                intent.putExtra("id_coursses", "-KiCTI8u0EaSm-Vawqpu");
                startActivity(intent);
            }
        });
        setUpToolbar();
        JCVideoPlayerStandard jcVideoPlayerStandard = (JCVideoPlayerStandard) findViewById(R.id.videoplayer);
        jcVideoPlayerStandard.setUp("http://2449.vod.myqcloud.com/2449_22ca37a6ea9011e5acaaf51d105342e3.f20.mp4"
                , JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "video name");
        jcVideoPlayerStandard.thumbImageView.setImageURI(Uri.parse("http://p.qpic.cn/vide5oyun/0/2449_43b6f696980311e59ed467f22794e792_1/640"));


    }

    /*
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
    */
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

}
