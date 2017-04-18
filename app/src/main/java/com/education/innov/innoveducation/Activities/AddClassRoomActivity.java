package com.education.innov.innoveducation.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.education.innov.innoveducation.Entities.ClassRoom;
import com.education.innov.innoveducation.R;
import com.education.innov.innoveducation.Utils.Config;
import com.education.innov.innoveducation.Utils.Test;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.liuguangqiang.progressbar.CircleProgressBar;
import com.liuguangqiang.swipeback.SwipeBackActivity;
import com.liuguangqiang.swipeback.SwipeBackLayout;
import com.mukesh.countrypicker.fragments.CountryPicker;
import com.mukesh.countrypicker.interfaces.CountryPickerListener;

import java.text.SimpleDateFormat;
import java.util.Date;

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
    private CountryPicker picker ;
    private ClassRoom classroom;
    private LinearLayout LayoutErrorMessage;
    private TextView tvErrorMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class_room);
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


        //  views();
        initViews();
    }

    private void AddClassRoom() {

        if(!ShowErorMessage()) {
            classroom = new ClassRoom();
            id = mDBase.child("classrooms").push().getKey();
            classroom.setIdAdminstrator(FirebaseAuth.getInstance().getCurrentUser().getUid());
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
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        AddClassRoomActivity.this.finish();
                        startActivity(new Intent(AddClassRoomActivity.this,MyClassRoomsActivity.class));
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

        picker.show(getSupportFragmentManager(), "COUNTRY_PICKER");
        picker.setListener(new CountryPickerListener() {
            @Override
            public void onSelectCountry(String name, String code, String dialCode, int flagDrawableResID) {
                country=name;
                etCountry.setText(country);
                picker.dismiss();
            }
        });
    }

    public boolean ShowErorMessage() {
        String msg="";
        if (!new Test().TestConnection(this)) {
            msg="there is no internet connection";
            return dispalyError(msg);
        }
        if(name.trim().isEmpty()||institut.trim().isEmpty()|| country.trim().isEmpty()||codePostale.trim().isEmpty())
            return dispalyError("All fields are required");

        return false;

    }
    private boolean dispalyError(String message){
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
