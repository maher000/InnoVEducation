package com.education.innov.innoveducation.Activities;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.education.innov.innoveducation.Entities.post;
import com.education.innov.innoveducation.R;
import com.education.innov.innoveducation.Utils.Config;
import com.education.innov.innoveducation.Utils.FileChooser;
import com.education.innov.innoveducation.Utils.FilePath;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
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
import java.util.Date;


public class AddPostActivity extends SwipeBackActivity {

    EditText EdtNamePost, EdtSubjectPost, EdtDescriptionPost;
    ImageView ImgAddVideo, ImgAddPhoto, ImgAddFile;
    Button btnAddPost;
    LinearLayout attchementContainer;
    private SwipeBackLayout swipeBackLayout;
    private CircleProgressBar progressBar;
    private static final int SELECT_VIDEO = 3;
    private static final int SELECT_PICTURE = 0;
    VideoView video;
    TextView name_file;
    ImageView image_post;
    String filePath;
    String id;
    StorageReference ViedeoPostRef = Config.storage.getReference("posts_videos");
    StorageReference videosRef;
    StorageReference FilePostRef = Config.storage.getReference("posts_files");
    StorageReference fileRef;
    StorageReference ImagePostRef = Config.storage.getReference("posts_images");
    StorageReference imagesRef;
    DatabaseReference mDBase = Config.mDatabase;
    Bitmap bmpImagePost;
    String typePost = "text";
    String title, subject, description, urlPostStorage;
    post new_post;


    String extentionFile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        EdtNamePost = (EditText) findViewById(R.id.EdtNamePost);
        EdtSubjectPost = (EditText) findViewById(R.id.EdtSubjectPost);
        EdtDescriptionPost = (EditText) findViewById(R.id.EdtDescriptionPost);

        ImgAddVideo = (ImageView) findViewById(R.id.ImgAddVideo);
        ImgAddPhoto = (ImageView) findViewById(R.id.ImgAddPhoto);
        ImgAddFile = (ImageView) findViewById(R.id.ImgAddFile);

        btnAddPost = (Button) findViewById(R.id.btnAddPost);

        attchementContainer = (LinearLayout) findViewById(R.id.attchementContainer);


        btnAddPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                id = mDBase.child("post").push().getKey();
                switch (typePost) {
                    case "image":
                        upload_image();
                        break;
                    case "video":
                        upload_Video();
                        break;
                    case "file":
                        upload_file();
                        break;
                    case "text":
                        AddPost("NONE");
                        break;
                    default:
                        AddPost("NONE");
                }


            }
        });
        ImgAddFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseFile();
            }
        });
        ImgAddVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseVideo();
            }
        });
        ImgAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseImage();
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
        attchementContainer.removeAllViews();
        if (image_post != null) {
            image_post.setImageDrawable(null);
        }
        video = new VideoView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(300, 300);
        attchementContainer.setLayoutParams(params);
        attchementContainer.addView(video, params);
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select a Video "), SELECT_VIDEO);

    }

    private void ChooseFile() {
        attchementContainer.removeAllViews();
        if (image_post != null) {
            image_post.setImageDrawable(null);
        }
        name_file = new TextView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 50);
        attchementContainer.setLayoutParams(params);
        attchementContainer.addView(name_file, params);
        new FileChooser(this).setFileListener(new FileChooser.FileSelectedListener() {
            @Override
            public void fileSelected(final File file) {
                // do something with the file
                typePost = "file";
                filePath = file.getAbsoluteFile().getPath();
                System.out.println("this file" + file.getName());
                System.out.println("file path" + filePath);
                name_file.setTextColor(getResources().getColor(R.color.black));
                name_file.setText(file.getName());
                extentionFile = file.getName().substring(file.getName().lastIndexOf(".") + 1, file.getName().length());
                System.out.println("extension" + extentionFile);
            }
        }).showDialog();

    }

    private void ChooseImage() {
        attchementContainer.removeAllViews();
        image_post = new ImageView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(300, 300);
        attchementContainer.setLayoutParams(params);
        attchementContainer.addView(image_post, params);

        Intent intent = new Intent(
                Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(
                Intent.createChooser(intent, "Select File"),
                SELECT_PICTURE);
    }

    private void AddPost(String urlPost) {

        title = EdtNamePost.getText().toString() ;
        subject = EdtSubjectPost.getText().toString();
        description =EdtDescriptionPost.getText().toString() ;
        urlPostStorage = urlPost;
        new_post = new post();
        new_post.setId(id);
        new_post.setName(title);
        new_post.setDescription(description);
        new_post.setUrlFile(urlPost);
        new_post.setSubject(subject);
        new_post.setUserId(FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
        new_post.setType(typePost);
        mDBase.child("posts").child(id).setValue(new_post).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    System.out.println("post added successfully");
                }
            }
        });

        System.out.println("type of post is " + typePost);



    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {

            if (requestCode == SELECT_VIDEO) {
                System.out.println("SELECT_VIDEO");
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
                typePost = "video";
                video.setVideoURI(selectedVideoUri);
                MediaController mc = new MediaController(this);
                video.setMediaController(mc);
                video.start();
            } else if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                int angle = 0;
                String tempPath = getPath(selectedImageUri, this);
                ExifInterface exif = null;
                try {
                    exif = new ExifInterface(tempPath);
                    int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                    switch (orientation) {
                        case ExifInterface.ORIENTATION_ROTATE_90:
                            angle = 90;
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_180:
                            angle = 180;
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_270:
                            angle = 270;
                            break;
                        default:
                            angle = 0;
                            break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Bitmap bm = null;
                BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
                btmapOptions.inSampleSize = 2;
                bm = BitmapFactory.decodeFile(tempPath, btmapOptions);
                Matrix matrix = new Matrix();
                matrix.postRotate(angle);
                bmpImagePost = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
                image_post.setImageBitmap(bmpImagePost);
                typePost = "image";

            }
        }
    }

    public String getPath(Uri uri, Activity activity) {
        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = activity
                .managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
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

    private void upload_Video() {
        System.out.println("uploading video");
          if (id != null) {
        videosRef = ViedeoPostRef.child(new Date()+"");
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
                AddPost(downloadUrl.toString());
                System.out.println("url videoooo" + downloadUrl);
            }
        });
    }

     }

    private void upload_image() {

        System.out.println("uploading image");
          if (id != null) {
        imagesRef = ImagePostRef.child(new Date()+"");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmpImagePost.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = imagesRef.putBytes(data);
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
                AddPost(downloadUrl.toString());
                System.out.println("url image" + downloadUrl);
            }
        });
    }
     }

    private void upload_file() {
        System.out.println("uploading file");
         if (id != null) {
        fileRef = FilePostRef.child(new Date()+"");
        String type = "*/" + extentionFile + "";
        System.out.println(type);
        StorageMetadata metadata = new StorageMetadata.Builder()
                .setContentType("file/" + extentionFile)
                .build();
        Uri file = Uri.fromFile(new File(filePath));
        System.out.println(file + "new file");
        UploadTask uploadTask = fileRef.putFile(file, metadata);
        uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
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
                AddPost(downloadUrl.toString());
                System.out.println("url fileeee" + downloadUrl);
            }
        });
    }
}
}
