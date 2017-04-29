package com.education.innov.innoveducation.Activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.education.innov.innoveducation.Entities.Child;
import com.education.innov.innoveducation.Entities.Parent;
import com.education.innov.innoveducation.Entities.Teacher;
import com.education.innov.innoveducation.R;
import com.education.innov.innoveducation.Utils.ComplexPreferences;
import com.education.innov.innoveducation.Utils.Config;
import com.education.innov.innoveducation.Utils.Test;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mukesh.countrypicker.fragments.CountryPicker;
import com.mukesh.countrypicker.interfaces.CountryPickerListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CompleteInformationChildActivity extends AppCompatActivity {

    private EditText EdtAdress, EdtPhone, EdtCodePostal, edtFirstName, edtLastName, EdtCity;
    private TextView EdtBirthday, EdtCountry;
    private RadioButton RbMen, RbWomen;
    private Button btnSubmit;
    private DatabaseReference mDbase = Config.mDatabase;
    private FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
    private String id = null;
    private Child child;
    private int yearStart, monthStart, dayStart;
    private int year, month, day;
    private CountryPicker picker;
    private ProgressDialog progress;
    private String country, address, phone, birthday, sex, code_postal, firstName, lastName, city;
    private LinearLayout LayoutErrorMessage;
    private TextView tvErrorMsg;
    private Bitmap bmpUser;
    private FloatingActionButton btnChooseImage;
    private static final int SELECT_PICTURE = 0;
    private static final int REQUEST_CAMERA = 1;
    private ImageView ImageProfileUser;
    private StorageReference storageRef = Config.storage.getReference("images_users");
    private StorageReference imagesRef;
    private SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_information_child);
        picker = CountryPicker.newInstance("Select Country");
        if (current_user != null)
            id = current_user.getUid();

        ImageProfileUser = (ImageView) findViewById(R.id.ImageProfileUser);
        LayoutErrorMessage = (LinearLayout) findViewById(R.id.LayoutErrorMessage);
        tvErrorMsg = (TextView) findViewById(R.id.tvErrorMsg);
        btnChooseImage = (FloatingActionButton) findViewById(R.id.btnChooseImage);
        edtFirstName = (EditText) findViewById(R.id.EdtFirstName);
        edtLastName = (EditText) findViewById(R.id.EdtLastname);
        EdtCodePostal = (EditText) findViewById(R.id.EdtCodePostal);
        EdtCountry = (EditText) findViewById(R.id.EdtCountry);
        EdtAdress = (EditText) findViewById(R.id.EdtAdress);
        EdtCity = (EditText) findViewById(R.id.EdtCity);
        EdtPhone = (EditText) findViewById(R.id.EdtPhone);
        EdtBirthday = (TextView) findViewById(R.id.EdtBirthday);
        RbMen = (RadioButton) findViewById(R.id.RbMen);
        RbWomen = (RadioButton) findViewById(R.id.RbWomen);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress = new ProgressDialog(CompleteInformationChildActivity.this);
                progress.setMessage("Uploading dat ...");
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress.setIndeterminate(true);
                if (RbMen.isChecked()) {
                    sex = "Men";
                }
                if (RbWomen.isChecked()) {
                    sex = "Women";
                }
                address = EdtAdress.getText().toString();
                country = EdtCountry.getText().toString();
                birthday = EdtBirthday.getText().toString();
                phone = EdtPhone.getText().toString();
                address = EdtAdress.getText().toString();
                code_postal = EdtCodePostal.getText().toString();
                firstName = edtFirstName.getText().toString();
                lastName = edtLastName.getText().toString();
                city = EdtCity.getText().toString();
                if (!ShowErorMessage()) {
                    progress.show();
                    storageImage();
                }

            }
        });
        EdtCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectCountry();
            }
        });
        EdtBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(CompleteInformationChildActivity.this, R.style.DialogTheme, BirthdayListener,
                        year, month, day).show();
            }
        });

        btnChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });


    }


    private void completeInformation(final String urlImage) {
        if (id != null) {
            mDbase.child(Config.CHILD_CHILD).child(id).addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                child = dataSnapshot.getValue(Child.class);
                                if (!ShowErorMessage()) {
                                    child.setFirstName(firstName);
                                    child.setLastName(lastName);
                                    child.setUrlImage(urlImage);
                                    if (city != null)
                                        child.setCity(city);
                                    if (address != null)
                                        child.setAdresse(address);
                                    child.setActive("yes");
                                    child.setSex(sex);
                                    if (!phone.trim().isEmpty())
                                        child.setPhone(phone);
                                    if (!phone.trim().isEmpty())
                                        child.setPhone(phone);
                                    if (!birthday.trim().isEmpty())
                                        child.setBirthday(birthday);
                                    if (!code_postal.trim().isEmpty())
                                        child.setCodePostal(code_postal);
                                    if (!country.trim().isEmpty())
                                        child.setContry(country);
                                    if (!sex.trim().isEmpty())
                                        child.setSex(sex);


                                }

                                mDbase.child(Config.CHILD_CHILD).child(id).setValue(child).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            progress.dismiss();
                                            sharedpreferences = getSharedPreferences("role_user", Context.MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedpreferences.edit();
                                            editor.putString("role", "child");
                                            editor.commit();
                                            ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(
                                                    CompleteInformationChildActivity.this, "mypref", Context.MODE_PRIVATE);
                                            complexPreferences.putObject("current_user", child);
                                            complexPreferences.commit();
                                            Intent intent = new Intent(CompleteInformationChildActivity.this, HomeActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);
                                            ;
                                        }
                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    }
            );
        }
    }


    private DatePickerDialog.OnDateSetListener BirthdayListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker p, int selectedYear,
                                      int selectedMonth, int selectedDay) {

                    yearStart = selectedYear;
                    monthStart = selectedMonth;
                    dayStart = selectedDay;
                    showDateStart(yearStart, monthStart + 1, dayStart);

                }
            };

    private void showDateStart(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        Date date1 = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = sdf.format(date1);
        EdtBirthday.setText(dateString);
    }

    private void SelectCountry() {

        picker.show(getSupportFragmentManager(), "COUNTRY_PICKER");

        picker.setListener(new CountryPickerListener() {
            @Override
            public void onSelectCountry(String name, String code, String dialCode, int flagDrawableResID) {
                EdtCountry.setText(name);
                picker.dismiss();
            }
        });
    }

    public boolean ShowErorMessage() {
        String msg = "";
        if (!new Test().TestConnection(this)) {
            msg = "there is no internet connection";
            return dispalyError(msg);
        }
        if (firstName.trim().isEmpty() || lastName.trim().isEmpty())
            return dispalyError("Please fill required fields");
        if (bmpUser == null)
            return dispalyError("Please choose an image");

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

    private void storageImage() {

        if (id != null) {
            imagesRef = storageRef.child(id);
            ImageProfileUser.setDrawingCacheEnabled(true);
            ImageProfileUser.buildDrawingCache();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmpUser.compress(Bitmap.CompressFormat.JPEG, 100, baos);
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
                    completeInformation(downloadUrl.toString());
                }
            });
        }
    }

    /***** select image ***/

    private void selectImage() {
        TextView tvCamera, tvGallery;
        Button btnCancelPicture;

        final Dialog dialog = new Dialog(this);

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
                dialog.dismiss();
                startActivityForResult(intent, REQUEST_CAMERA);
            }
        });
        tvGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                dialog.dismiss();
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
                    Matrix matrix = new Matrix();
                    matrix.postRotate(-90);
                    bmpUser = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
                    ImageProfileUser.setImageBitmap(bmpUser);
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
                bmpUser = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
                ImageProfileUser.setImageBitmap(bmpUser);

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
