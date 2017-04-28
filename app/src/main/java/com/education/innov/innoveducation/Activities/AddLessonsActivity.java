package com.education.innov.innoveducation.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.education.innov.innoveducation.Entities.Lesson;
import com.education.innov.innoveducation.R;
import com.education.innov.innoveducation.Utils.Config;
import com.education.innov.innoveducation.Utils.Test;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.liuguangqiang.progressbar.CircleProgressBar;
import com.liuguangqiang.swipeback.SwipeBackActivity;
import com.liuguangqiang.swipeback.SwipeBackLayout;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddLessonsActivity extends SwipeBackActivity {
    private CircleProgressBar progressBar;
    private SwipeBackLayout swipeBackLayout;
    LinearLayout attchementContainerLesson;
    String title, description, idCoursses, id, urlVideo, urlMiniature, dateStart;
    VideoView video;
    ProgressDialog circleProgressBar;
    DatabaseReference mDBase = Config.mDatabase;
    private static final int SELECT_VIDEO = 3;
    Lesson new_lesson;
    String filePath;
    EditText EdtNameLesson, EdtDescriptionLesson;
    ImageView ImgAddVideoLesson;
    Button btnAddLesson;
    StorageReference ViedeoPostRef = Config.storage.getReference("lessons_videos");
    StorageReference videosRef;
    Intent intent;
    private LinearLayout LayoutErrorMessage;
    private TextView tvErrorMsg;
    private boolean verif=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lessons);
        EdtNameLesson = (EditText) findViewById(R.id.EdtNameLesson);
        EdtDescriptionLesson = (EditText) findViewById(R.id.EdtDescriptionLesson);
        ImgAddVideoLesson = (ImageView) findViewById(R.id.ImgAddVideoLesson);
        btnAddLesson = (Button) findViewById(R.id.btnAddLesson);
        attchementContainerLesson = (LinearLayout) findViewById(R.id.attchementContainerLesson);
        LayoutErrorMessage = (LinearLayout) findViewById(R.id.LayoutErrorMessage);
        tvErrorMsg = (TextView) findViewById(R.id.tvErrorMsg);

        intent = getIntent();

        circleProgressBar = new ProgressDialog(this);
        circleProgressBar.setMax(100);
        circleProgressBar.setCancelable(false);

        ImgAddVideoLesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseVideo();
            }
        });
        btnAddLesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = mDBase.child("lessons").push().getKey();
                title = EdtNameLesson.getText().toString();
                description=EdtDescriptionLesson.getText().toString();
                if(!ShowErorMessage()){
                    upload_Video();
                }

            }
        });
        initViews();
    }

    private void initViews() {
        progressBar = (CircleProgressBar) findViewById(R.id.progressbar1);
        swipeBackLayout = (SwipeBackLayout) findViewById(R.id.swipe_layout);
        swipeBackLayout.setEnableFlingBack(false);
        swipeBackLayout.setOnPullToBackListener(new SwipeBackLayout.SwipeBackListener() {
            @Override
            public void onViewPositionChanged(float fractionAnchor, float fractionScreen) {
                progressBar.setProgress((int) (progressBar.getMax() * fractionAnchor));
            }
        });
    }

    private void ChooseVideo() {

        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select a Video "), SELECT_VIDEO);

    }

    private void upload_Video() {
        circleProgressBar.show();
        System.out.println("uploading video");
        if (id != null) {
            videosRef = ViedeoPostRef.child(id);
            StorageMetadata metadata = new StorageMetadata.Builder()
                    .setContentType("video/mp4")
                    .build();
            Uri file = Uri.fromFile(new File(filePath));
            System.out.println(file + "new file");
            UploadTask uploadTask = videosRef.putFile(file, metadata);
            uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    circleProgressBar.setProgress((int) progress);
                    System.out.println("Upload is " + progress + "% done");
                }
            }).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {
                    System.out.println("Upload is paused");
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    urlVideo = downloadUrl.toString();
                    upload_thms(filePath);
                    System.out.println("url videoooo" + downloadUrl);
                }
            });
        }

    }


    private void AddLesson() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMMM dd 'at' hh:mm aaa");
        dateStart = sdf.format(new Date());
        title = EdtNameLesson.getText().toString();
        description = EdtDescriptionLesson.getText().toString();
        idCoursses = intent.getStringExtra("id_coursse");
        new_lesson = new Lesson();
        new_lesson.setDescription(description);
        new_lesson.setDateCreation(dateStart);
        new_lesson.setTitle(title);
        new_lesson.setId(id);
        new_lesson.setUrlVideo(urlVideo);
        new_lesson.setUrlMiniature(urlMiniature);
        new_lesson.setIdCoursse(idCoursses);
        mDBase.child("lessons").child(id).setValue(new_lesson).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    AddLessonsActivity.this.finish();
                }
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {

            if (requestCode == SELECT_VIDEO) {
                System.out.println("SELECT_VIDEO");
                attchementContainerLesson.removeAllViews();
                video = new VideoView(this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(300, 300);
                attchementContainerLesson.setLayoutParams(params);
                attchementContainerLesson.addView(video, params);
                Uri selectedVideoUri = data.getData();
                final String docId = DocumentsContract.getDocumentId(selectedVideoUri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                }
                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };
                filePath = getDataColumn(this, contentUri, selection, selectionArgs);
                System.out.println("url video " + filePath);
                video.setVideoURI(selectedVideoUri);
                MediaController mc = new MediaController(this);
                video.setMediaController(mc);
                video.start();
                verif=true;

            }
        }
    }

    /***end select image ****/

    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    private void upload_thms(String urlFile) {
        Bitmap bmThumbnail;
        bmThumbnail = ThumbnailUtils.createVideoThumbnail(urlFile, MediaStore.Images.Thumbnails.FULL_SCREEN_KIND);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmThumbnail.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = ViedeoPostRef.child("miniaturesLessons").child(id).putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override

            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                System.out.println("Upload is " + progress + "% done");
            }
        }).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {
                System.out.println("Upload is paused");
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override

            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                urlMiniature = downloadUrl.toString();
                AddLesson();
                System.out.println("url image" + downloadUrl);
            }

        });
    }

    public boolean ShowErorMessage() {
        String msg = "";
        if (!new Test().TestConnection(this)) {
            msg = "there is no internet connection";
            return dispalyError(msg);
        }
        if (description.trim().isEmpty() || title.trim().isEmpty())
            return dispalyError("All fields are required");
        if(!verif)
            return dispalyError("choose a video to upload");


        return false;

    }

    private boolean dispalyError(String message) {
        tvErrorMsg.setText(message);
        tvErrorMsg.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
        LayoutErrorMessage.setVisibility(View.VISIBLE);
        LayoutErrorMessage.postDelayed(new Runnable() {
            public void run() {
                LayoutErrorMessage.setVisibility(View.INVISIBLE);
            }
        }, 3000);
        return true;
    }


}
