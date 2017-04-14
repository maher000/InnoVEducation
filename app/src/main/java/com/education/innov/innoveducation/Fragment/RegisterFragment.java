package com.education.innov.innoveducation.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.education.innov.innoveducation.Activities.CompleteInformationUserActivity;
import com.education.innov.innoveducation.Activities.MainActivity;
import com.education.innov.innoveducation.Entities.Child;
import com.education.innov.innoveducation.Entities.Parent;
import com.education.innov.innoveducation.Entities.Teacher;
import com.education.innov.innoveducation.Entities.User;
import com.education.innov.innoveducation.R;
import com.education.innov.innoveducation.Utils.Config;
import com.education.innov.innoveducation.Utils.Test;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class RegisterFragment extends Fragment {
    private static final int SELECT_PICTURE = 0;
    private static final int REQUEST_CAMERA = 1;
    ImageView ImageProfileUser;
    EditText EdtFirstname, EdtLastname, EdtEmail, EdtPassword, EdtConfirmPassword;
    FloatingActionButton btnChooseImage;
    RadioGroup RgType;
    RadioButton RbTeacher, RbParent;
    StorageReference storageRef = Config.storage.getReference("images_users");
    DatabaseReference mDBase = Config.mDatabase;
    StorageReference imagesRef;
    Button btnRegister;
    String email, password, firstname, lastname, confirmpassword, role;
    Parent parent;
    FirebaseAuth auth = Config.mAuth;
    Teacher teacher;
    Activity activity;
    User user;
    Child child;


    public RegisterFragment() {

    }

    public static RegisterFragment newInstance(int page, String title) {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        activity = getActivity();
        ImageProfileUser = (ImageView) view.findViewById(R.id.ImageProfileUser);
        EdtFirstname = (EditText) view.findViewById(R.id.EdtFirstname);
        EdtLastname = (EditText) view.findViewById(R.id.EdtLastname);
        EdtEmail = (EditText) view.findViewById(R.id.EdtEmail);
        EdtPassword = (EditText) view.findViewById(R.id.EdtPassword);
        EdtConfirmPassword = (EditText) view.findViewById(R.id.EdtConfirmPassword);
        btnChooseImage = (FloatingActionButton) view.findViewById(R.id.btnChooseImage);
        RgType = (RadioGroup) view.findViewById(R.id.RgType);
        RbTeacher = (RadioButton) view.findViewById(R.id.RbTeacher);
        RbParent = (RadioButton) view.findViewById(R.id.RbParent);
        btnRegister = (Button) view.findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = EdtEmail.getText().toString();
                password = EdtPassword.getText().toString();
                confirmpassword = EdtConfirmPassword.getText().toString();
                firstname = EdtFirstname.getText().toString();
                lastname = EdtLastname.getText().toString();

                if (!new Test().TestConnection(getActivity())) {
                    ((MainActivity) activity).ShowErorMessage("there is no internet connection");
                } else if (ImageProfileUser == null) {
                    ((MainActivity) activity).ShowErorMessage("you have to choose a picture");
                } else if (email.isEmpty() || password.isEmpty() || confirmpassword.isEmpty() || firstname.isEmpty() || lastname.isEmpty()) {
                    ((MainActivity) activity).ShowErorMessage("All fields are required");
                } else if (!password.equals(confirmpassword)) {
                    ((MainActivity) activity).ShowErorMessage("passwords does not matchs");
                } else {
                    Register();
                }
            }
        });
        btnChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        return view;

    }

    void Register() {


        System.out.println("test");
        System.out.println("syriiine" + email + password);
        System.out.println(auth.toString());
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            /*******************************************/
                            String token = FirebaseInstanceId.getInstance().getToken();
                            Config.mDatabase.child("Tokens").child(Config.mAuth.getCurrentUser().getUid()).child("Token").setValue(token);
                            /*******************************************/
                            storageImage();

                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setMessage(task.getException().getMessage())
                                    .setTitle("erreur")
                                    .setPositiveButton(android.R.string.ok, null);
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    }
                });
        System.out.println("test");
    }


    private void storageImage() {

        String id = Config.user_connected.getUid();
        if (id != null ){
        imagesRef = storageRef.child(id);
        ImageProfileUser.setDrawingCacheEnabled(true);
        ImageProfileUser.buildDrawingCache();
        Bitmap bitmap = ImageProfileUser.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = imagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                AddDetailUser(downloadUrl.toString());
            }
        });
    }}

    public void AddDetailUser(String urlImage) {

        String id = Config.user_connected.getUid();
        if (RbParent.isChecked()) {

            parent = new Parent();
            parent.setId(id);
            parent.setFirstName(firstname);
            parent.setLastName(lastname);
            parent.setUrlImage(urlImage);
            mDBase.child("parents").child(id).setValue(parent).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Intent intent = new Intent(getActivity(), CompleteInformationUserActivity.class);
                        startActivity(intent);

                    } else {
                        System.out.println("error");
                    }
                }
            });

        } else if (RbTeacher.isChecked()) {

            teacher = new Teacher();
            teacher.setId(id);
            teacher.setFirstName(firstname);
            teacher.setLastName(lastname);
            teacher.setUrlImage(urlImage);
            mDBase.child("teachers").child(id).setValue(teacher).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Intent intent = new Intent(getActivity(), CompleteInformationUserActivity.class);
                        startActivity(intent);

                    } else {
                        System.out.println("error");
                    }
                }
            });


        }


    }

    /***** select image ***/

    private void selectImage() {
        TextView tvCamera, tvGallery;
        Button btnCancelPicture;

        final Dialog dialog = new Dialog(getActivity());

        dialog.setContentView(R.layout.dialog_select_image);
                tvCamera = (TextView) dialog.findViewById(R.id.tvCamera);
        tvGallery = (TextView) dialog.findViewById(R.id.tvGallery);
        btnCancelPicture = (Button) dialog.findViewById(R.id.btnCancelPicture);
        tvCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File f = new File(android.os.Environment
                        .getExternalStorageDirectory(), "temp.jpg");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                startActivityForResult(intent, REQUEST_CAMERA);
            }
        });
        tvGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(
                        Intent.createChooser(intent, "Select File"),
                        SELECT_PICTURE);
            }
        });
        btnCancelPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {
                File f = new File(Environment.getExternalStorageDirectory()
                        .toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                    Bitmap bm;
                    BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
                    btmapOptions.inSampleSize = 2;
                    bm = BitmapFactory.decodeFile(f.getAbsolutePath(),
                            btmapOptions);

                    // bm = Bitmap.createScaledBitmap(bm, 70, 70, true);
                    ImageProfileUser.setImageBitmap(bm);

                    String path = android.os.Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "test";
                    f.delete();
                    OutputStream fOut = null;
                    File file = new File(path, String.valueOf(System
                            .currentTimeMillis()) + ".jpg");
                    fOut = new FileOutputStream(file);
                    bm.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
                    fOut.flush();
                    fOut.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                String tempPath = getPath(selectedImageUri, this.getActivity());
                Bitmap bm;
                BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
                btmapOptions.inSampleSize = 2;
                bm = BitmapFactory.decodeFile(tempPath, btmapOptions);
                ImageProfileUser.setImageBitmap(bm);
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


}
