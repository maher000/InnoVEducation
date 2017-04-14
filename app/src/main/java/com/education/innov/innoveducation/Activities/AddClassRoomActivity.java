package com.education.innov.innoveducation.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.education.innov.innoveducation.Entities.ClassRoom;
import com.education.innov.innoveducation.R;
import com.education.innov.innoveducation.Utils.Config;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.liuguangqiang.progressbar.CircleProgressBar;
import com.liuguangqiang.swipeback.SwipeBackActivity;
import com.liuguangqiang.swipeback.SwipeBackLayout;
import com.mukesh.countrypicker.fragments.CountryPicker;
import com.mukesh.countrypicker.interfaces.CountryPickerListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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
    CountryPicker picker ;
    private ClassRoom classroom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_home_work);
        picker = CountryPicker.newInstance("Select Country");
        etName = (EditText) findViewById(R.id.et_classe_room_name);
        etInstitut = (EditText) findViewById(R.id.et_classe_room_institut);
        etCountry = (EditText) findViewById(R.id.EdtCountry);
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


        //  views();
        initViews();
    }

    private void AddClassRoom() {
        classroom = new ClassRoom();
        id = mDBase.child("classrooms").push().getKey();
        classroom.setIdAdminstrator(FirebaseAuth.getInstance().getCurrentUser().getUid());
        classroom.setId(id);
        classroom.setName(name);
        classroom.setAdress(institut);
        classroom.setVisibility(visivility);
        Date date = new Date();
        SimpleDateFormat simpleDate =  new SimpleDateFormat("dd-mm-yyyy hh:mm");
        String dateCreation = simpleDate.format(date);
        classroom.setCreationDate(dateCreation);
        mDBase.child("classrooms").child(id).setValue(classroom).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                } else {

                    System.out.println("error" + task.getException().getMessage());
                }
            }
        });
    }


    private void initViews() {
        progressBar = (CircleProgressBar) findViewById(R.id.progressbar1);
        progressBar.setBackgroundColor(Color.rgb(0, 0, 0));
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

        picker.show(getSupportFragmentManager(), "COUNTRY_PICKER");
        picker.setListener(new CountryPickerListener() {
            @Override
            public void onSelectCountry(String name, String code, String dialCode, int flagDrawableResID) {
                etCountry.setText(name);
                picker.dismiss();
            }
        });
    }
}
