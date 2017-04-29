package com.education.innov.innoveducation.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.session.MediaSessionManager;
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
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.education.innov.innoveducation.Entities.Child;
import com.education.innov.innoveducation.Entities.ClassRoom;
import com.education.innov.innoveducation.Entities.Notification;
import com.education.innov.innoveducation.Entities.Parent;
import com.education.innov.innoveducation.Entities.Teacher;
import com.education.innov.innoveducation.Entities.User;
import com.education.innov.innoveducation.Entities.post;
import com.education.innov.innoveducation.R;
import com.education.innov.innoveducation.Utils.ComplexPreferences;
import com.education.innov.innoveducation.Utils.Config;
import com.education.innov.innoveducation.Utils.FileChooser;
import com.education.innov.innoveducation.Utils.FilePath;
import com.education.innov.innoveducation.Utils.MyApp;
import com.education.innov.innoveducation.Utils.psuhNotificationAllUsers;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.liuguangqiang.progressbar.CircleProgressBar;
import com.liuguangqiang.swipeback.SwipeBackActivity;

import android.media.ThumbnailUtils;

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
    private VideoView video;
    private ImageView image_post;
    private String filePath;
    private String id;
    private Teacher teacher;
    private Parent parent;
    private Child child;
    private ProgressDialog circleProgressBar;
    private StorageReference ViedeoPostRef = Config.storage.getReference("posts_videos");
    private StorageReference videosRef;
    private StorageReference FilePostRef = Config.storage.getReference("posts_files");
    private StorageReference fileRef;
    private StorageReference ImagePostRef = Config.storage.getReference("posts_images");
    private StorageReference imagesRef;
    private RadioButton RbVYes, RbVNo;
    private DatabaseReference mDBase = Config.mDatabase;
    private Bitmap bmpImagePost;
    private String typePost = "text";
    private String title, subject, description, urlPostStorage, visibility, author, urlImageAuthor, Role;
    private post new_post;
    private SharedPreferences shared;
    private SharedPreferences sp;
    private static Gson gson = new Gson();
    private static String json;
    private ComplexPreferences complexPreferences;
    private ClassRoom class_room ;
    private String extentionFile;
    private TextView name_file;
    private String urlFile;
    private String notificationBody="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shared = getSharedPreferences("role_user", Activity.MODE_PRIVATE);
        Role = shared.getString("role", null);
        if (Role != null) {
            getInfomationUser();
        }

        setContentView(R.layout.activity_add_post);
        EdtNamePost = (EditText) findViewById(R.id.EdtNamePost);
        EdtSubjectPost = (EditText) findViewById(R.id.EdtSubjectPost);
        EdtDescriptionPost = (EditText) findViewById(R.id.EdtDescriptionPost);
        ImgAddVideo = (ImageView) findViewById(R.id.ImgAddVideo);
        ImgAddPhoto = (ImageView) findViewById(R.id.ImgAddPhoto);
        ImgAddFile = (ImageView) findViewById(R.id.ImgAddFile);

        RbVYes = (RadioButton) findViewById(R.id.RbVYesPoste);
        RbVNo = (RadioButton) findViewById(R.id.RbVNoPoste);

        btnAddPost = (Button) findViewById(R.id.btnAddPost);

        attchementContainer = (LinearLayout) findViewById(R.id.attchementContainer);
        circleProgressBar = new ProgressDialog(this);
        circleProgressBar.setMax(100);
        circleProgressBar.setCancelable(false);
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

        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select a Video "), SELECT_VIDEO);

    }

    private void ChooseImage() {
        Intent intent = new Intent(
                Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(
                Intent.createChooser(intent, "Select File"),
                SELECT_PICTURE);
    }

    private void ChooseFile() {
        attchementContainer.removeAllViews();
        name_file= new TextView(this);
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


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {

            if (requestCode == SELECT_VIDEO) {
                System.out.println("SELECT_VIDEO");
                attchementContainer.removeAllViews();
                video = new VideoView(this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(300, 300);
                attchementContainer.setLayoutParams(params);
                attchementContainer.addView(video, params);
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
                video=null;
                attchementContainer.removeAllViews();
                image_post = new ImageView(this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(300, 300);
                attchementContainer.setLayoutParams(params);
                attchementContainer.addView(image_post, params);
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
                    urlFile=downloadUrl.toString();
                    upload_thms(filePath);
                    System.out.println("url videoooo" + downloadUrl);
                }
            });
        }

    }

    private void upload_image() {
        circleProgressBar.show();
        System.out.println("uploading image");
        if (id != null) {
            imagesRef = ImagePostRef.child(id);
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
                    AddPost(downloadUrl.toString());
                    System.out.println("url image" + downloadUrl);
                }
            });
        }
    }

    private void upload_file() {
        circleProgressBar.show();
        System.out.println("uploading file");
        if (id != null) {
            fileRef = FilePostRef.child(id);
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
                    AddPost(downloadUrl.toString());
                    System.out.println("url fileeee" + downloadUrl);
                }
            });
        }
    }

    private void AddPost(String urlPost) {

        title = EdtNamePost.getText().toString();
        subject = EdtSubjectPost.getText().toString();
        description = EdtDescriptionPost.getText().toString();
        if (RbVNo.isChecked()) {
            visibility = "no";
        }
        if (RbVYes.isChecked()) {
            visibility = "yes";
        }
        urlPostStorage = urlPost;
        new_post = new post();
        new_post.setId(id);
        if(typePost.trim().equals("file")) {
            new_post.setName(name_file.getText().toString());
        }
        else
        new_post.setName(title);
        if(typePost.trim().equals("video")){
            new_post.setUrlMiniature(urlPost);
            new_post.setUrlFile(urlFile);
        }
        new_post.setDescription(description);
        new_post.setAuthor(author);
        new_post.setUrlImageAuthor(urlImageAuthor);

        if(!typePost.trim().equals("video"))
        new_post.setUrlFile(urlPost);

        new_post.setSubject(subject);
        new_post.setClassRoomId(class_room.getId());
        new_post.setVisibility(visibility);
        new_post.setUserId(FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
        new_post.setType(typePost);
        mDBase.child("posts").child(id).setValue(new_post).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                System.out.println("yees baby "+task.getResult());
                    notificationBody=author+"has added a new Post : "+new_post.getName()+" on "+class_room.getName();
                    Notification not=new Notification();
                    not.setContenue(notificationBody);
                    not.setSenderId(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    not.setSenderName(author);
                    not.setType("post");
                    not.setUrlImage(urlImageAuthor);
                    not.setDate(new Date().toString());
                    not.setClassRoomId(class_room.getId());
                    not.setId( mDBase.child("notification").push().getKey());
                    mDBase.child("notification").child(not.getId()).setValue(not);
                    psuhNotificationAllUsers.sendAndroidNotification("/topics/"+new_post.getClassRoomId(),notificationBody,"new Post");

                    circleProgressBar.dismiss();
                    System.out.println("post added successfully");
                    AddPostActivity.this.finish();
                }
            }
        });
        System.out.println("type of post is " + typePost);
    }

    private void upload_thms(String urlFile) {
        Bitmap bmThumbnail;
        bmThumbnail = ThumbnailUtils.createVideoThumbnail(urlFile, MediaStore.Images.Thumbnails.FULL_SCREEN_KIND);
        ViedeoPostRef.child("miniatures");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmThumbnail.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = ViedeoPostRef.child("post_thms").child(id).putBytes(data);
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

    public void getInfomationUser() {
                switch (Role.trim()) {
                    case "teacher":
                        complexPreferences = ComplexPreferences.getComplexPreferences(this, "mypref", this.MODE_PRIVATE);
                        teacher = complexPreferences.getObject("current_user", Teacher.class);
                        author = teacher.getFirstName() + " " + teacher.getLastName();
                        urlImageAuthor = teacher.getUrlImage();
                        class_room = complexPreferences.getObject("my_class_room", ClassRoom.class);
                        notificationBody=teacher.getFirstName()+ " "+ teacher.getLastName()+" ";
                        System.out.println(teacher + "tttttttttttttt");
                        break;
                    case "parent":
                        complexPreferences = ComplexPreferences.getComplexPreferences(this, "mypref", this.MODE_PRIVATE);
                        parent = complexPreferences.getObject("current_user", Parent.class);
                        author = parent.getFirstName() + " " + parent.getLastName();
                        urlImageAuthor = parent.getUrlImage();
                        class_room = complexPreferences.getObject("my_class_room", ClassRoom.class);
                        notificationBody=parent.getFirstName()+ " "+ parent.getLastName()+" ";
                        break;
                    case "child":
                        complexPreferences = ComplexPreferences.getComplexPreferences(this, "mypref", MODE_PRIVATE);
                        child = complexPreferences.getObject("current_user", Child.class);
                        System.out.println(child + "ffggdds");
                        author = child.getFirstName() + " " + child.getLastName();
                        urlImageAuthor = child.getUrlImage();
                        class_room = complexPreferences.getObject("my_class_room", ClassRoom.class);
                        notificationBody=child.getFirstName()+ " "+ child.getLastName()+" ";


                        break;
                }
            }
        }