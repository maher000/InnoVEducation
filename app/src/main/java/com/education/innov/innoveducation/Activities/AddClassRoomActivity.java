package com.education.innov.innoveducation.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.education.innov.innoveducation.Entities.Child;
import com.education.innov.innoveducation.Entities.ClassRoom;
import com.education.innov.innoveducation.Entities.Parent;
import com.education.innov.innoveducation.Entities.Teacher;
import com.education.innov.innoveducation.R;
import com.education.innov.innoveducation.Utils.ComplexPreferences;
import com.education.innov.innoveducation.Utils.Config;
import com.education.innov.innoveducation.Utils.MyApp;
import com.education.innov.innoveducation.Utils.Test;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.liuguangqiang.progressbar.CircleProgressBar;
import com.liuguangqiang.swipeback.SwipeBackActivity;
import com.liuguangqiang.swipeback.SwipeBackLayout;
import com.mukesh.countrypicker.fragments.CountryPicker;
import com.mukesh.countrypicker.interfaces.CountryPickerListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class AddClassRoomActivity extends SwipeBackActivity {
    private CircleProgressBar progressBar;
    private SwipeBackLayout swipeBackLayout;
    private EditText etName;
    private EditText etInstitut;
    private EditText etCountry;
    private EditText etCodePostal;
    private Button btnSubmit;
    private RadioButton RbYes, RbNo;
    private String name, institut, country, codePostale, visivility, id;
    private DatabaseReference mDBase = Config.mDatabase;
    private CountryPicker picker;
    private ClassRoom classroom;
    private LinearLayout LayoutErrorMessage;
    private TextView tvErrorMsg;
    private Teacher teacher;
    private String author, urlImageAuthor, admin;
    private SharedPreferences shared;
    private SharedPreferences sp;
    private static String json;
    private ComplexPreferences complexPreferences;
    private HashMap<String,String> list_class_rooms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class_room);
        getInfomationUser();
        picker = CountryPicker.newInstance("Select Country");
        LayoutErrorMessage = (LinearLayout) findViewById(R.id.LayoutErrorMessage);
        tvErrorMsg = (TextView) findViewById(R.id.tvErrorMsg);
        etName = (EditText) findViewById(R.id.et_classe_room_name);
        etInstitut = (EditText) findViewById(R.id.et_classe_room_institut);
        etCountry = (EditText) findViewById(R.id.etCountry_add_class_room);
        etCodePostal = (EditText) findViewById(R.id.EdtCodePostal);
        RbYes = (RadioButton) findViewById(R.id.RbYes);
        RbNo = (RadioButton) findViewById(R.id.RbNo);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        etCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectCountry();
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = etName.getText().toString();
                institut = etInstitut.getText().toString();
                codePostale = etCodePostal.getText().toString();
                country = etCountry.getText().toString();
                if (RbNo.isChecked()) {
                    visivility = "no";
                } else if (RbYes.isChecked()) {
                    visivility = "yes";
                }
                AddClassRoom();
            }
        });
        initViews();
    }

    private void AddClassRoom() {
        if (!ShowErorMessage()) {
            classroom = new ClassRoom();
            id = mDBase.child("classrooms").push().getKey();
            admin = FirebaseAuth.getInstance().getCurrentUser().getUid();
            classroom.setIdAdminstrator(admin);
            classroom.setAuthor(author);
            classroom.setUrlImageAuthor(urlImageAuthor);
            classroom.setId(id);
            classroom.setName(name);
            classroom.setAdress(institut);
            classroom.setVisibility(visivility);
            classroom.setCountry(country);
            Date date = new Date();
            SimpleDateFormat simpleDate = new SimpleDateFormat("dd-mm-yyyy hh:mm");
            String dateCreation = simpleDate.format(date);
            classroom.setCreationDate(dateCreation);
            mDBase.child("classrooms").child(id).setValue(classroom).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull final Task<Void> task) {
                    if (task.isSuccessful()) {
                        list_class_rooms = teacher.getClassRooms();
                        if (list_class_rooms == null) {
                            list_class_rooms = new HashMap<String, String>();
                        }
                        list_class_rooms.put(id,id);

                        mDBase.child("teachers").child(admin).child("classRooms").child(id).setValue(id).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                if (task.isSuccessful()) {
                                    teacher.setClassRooms(list_class_rooms);
                                    ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(
                                            AddClassRoomActivity.this, "mypref", Context.MODE_PRIVATE);
                                    complexPreferences.putObject("current_user", teacher);
                                    complexPreferences.commit();
                                    AddClassRoomActivity.this.finish();
                                    startActivity(new Intent(AddClassRoomActivity.this, MyClassRoomsActivity.class));
                                }
                            }
                        });

                    } else {

                        System.out.println("error" + task.getException().getMessage());
                    }
                }
            });
        }
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

    private void SelectCountry() {
        picker.setStyle(1, R.style.CountryPicker);
        picker.show(getSupportFragmentManager(), "COUNTRY_PICKER");
        picker.setListener(new CountryPickerListener() {
            @Override
            public void onSelectCountry(String name, String code, String dialCode, int flagDrawableResID) {

                country = name;
                etCountry.setText(country);
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
        if (name.trim().isEmpty() || institut.trim().isEmpty() || country.trim().isEmpty() || codePostale.trim().isEmpty())
            return dispalyError("All fields are required");

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

    public void getInfomationUser() {
            complexPreferences = ComplexPreferences.getComplexPreferences(this, "mypref", this.MODE_PRIVATE);
            teacher = complexPreferences.getObject("current_user", Teacher.class);
            if (teacher != null) {
                author = teacher.getFirstName() + " " + teacher.getLastName();
                urlImageAuthor = teacher.getUrlImage();
                list_class_rooms = teacher.getClassRooms();
                System.out.println(teacher + "tttttttttttttt");
            }
    }
}
