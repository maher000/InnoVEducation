package com.education.innov.innoveducation.Activities;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.education.innov.innoveducation.Entities.HomeWork;
import com.education.innov.innoveducation.Entities.Parent;
import com.education.innov.innoveducation.Entities.Teacher;
import com.education.innov.innoveducation.R;
import com.education.innov.innoveducation.Utils.ComplexPreferences;
import com.education.innov.innoveducation.Utils.Config;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.mukesh.countrypicker.fragments.CountryPicker;
import com.mukesh.countrypicker.interfaces.CountryPickerListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class CompleteInformationUserActivity extends AppCompatActivity {

    EditText EdtAdress, EdtPhone, EdtCodePostal, EdtCity;
    TextView EdtBirthday, EdtCountry;
    RadioButton RbMen, RbWomen;
    Button btnLater, btnSubmit;
    DatabaseReference mDbase = Config.mDatabase;
    FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
    String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
    Parent parent;
    Teacher teacher;
    private int yearStart, monthStart, dayStart;
    private int year, month, day;
    CountryPicker picker;
    private SharedPreferences sharedpreferences;
    ProgressDialog progress;
    String country, address, phone, birthday, sex, code_postal, city;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_information_user);
        sharedpreferences = getSharedPreferences("role_user", Context.MODE_PRIVATE);
        picker = CountryPicker.newInstance("Select Country");

        EdtCodePostal = (EditText) findViewById(R.id.EdtCodePostal);
        EdtCity = (EditText) findViewById(R.id.EdtCity);
        EdtCountry = (EditText) findViewById(R.id.EdtCountry);
        EdtAdress = (EditText) findViewById(R.id.EdtAdress);
        EdtPhone = (EditText) findViewById(R.id.EdtPhone);
        EdtBirthday = (TextView) findViewById(R.id.EdtBirthday);
        RbMen = (RadioButton) findViewById(R.id.RbMen);
        RbWomen = (RadioButton) findViewById(R.id.RbWomen);
        btnLater = (Button) findViewById(R.id.btnLater);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress = new ProgressDialog(CompleteInformationUserActivity.this);
                progress.setMessage("Uploading dat ...");
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress.setIndeterminate(true);
                progress.show();
                if (RbMen.isChecked()) {
                    sex = "Men";
                }
                if (RbWomen.isChecked()) {
                    sex = "Women";
                }
                country = EdtCountry.getText().toString();
                birthday = EdtBirthday.getText().toString();
                phone = EdtPhone.getText().toString();
                address = EdtAdress.getText().toString();
                code_postal = EdtCodePostal.getText().toString();
                city = EdtCity.getText().toString();

                CompleteInformationParent();
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
                new DatePickerDialog(CompleteInformationUserActivity.this,R.style.DialogTheme, BirthdayListener,
                        year, month, day).show();
            }
        });
        btnLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CompleteInformationUserActivity.this, HomeActivity.class));
            }
        });

    }


    private void CompleteInformationParent() {
        mDbase.child(Config.CHILD_PARENT).child(id).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            parent = dataSnapshot.getValue(Parent.class);
                            final Parent user = new Parent();
                            String firstName = parent.getFirstName();
                            String lastName = parent.getLastName();
                            String urlImage = parent.getUrlImage();
                            user.setFirstName(firstName);
                            user.setLastName(lastName);
                            user.setUrlImage(urlImage);
                            user.setIdUser(id);
                            user.setAdresse(address);
                            user.setCodePostal(code_postal);
                            user.setContry(country);
                            user.setPhone(phone);
                            user.setCity(city);
                            user.setSex(sex);
                            mDbase.child("parents").child(id).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        SharedPreferences.Editor editor = sharedpreferences.edit();
                                        editor.putString("role", "parent");
                                        editor.commit();
                                        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(
                                                CompleteInformationUserActivity.this, "mypref", Context.MODE_PRIVATE);
                                        complexPreferences.putObject("current_user", user);
                                        complexPreferences.commit();

                                        progress.dismiss();
                                        startActivity(new Intent(CompleteInformationUserActivity.this, HomeActivity.class));
                                    }
                                }
                            });
                        } else {
                            CompleteInformationTeacher();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                }
        );
    }

    public void CompleteInformationTeacher() {

        mDbase.child("teachers").orderByChild("id").equalTo(id).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        teacher = dataSnapshot.getValue(Teacher.class);
                        final Teacher user = new Teacher();
                        String firstName = teacher.getFirstName();
                        String lastName = teacher.getLastName();
                        String urlImage = teacher.getUrlImage();
                        user.setFirstName(firstName);
                        user.setLastName(lastName);
                        user.setUrlImage(urlImage);
                        user.setIdUser(id);
                        user.setAdresse(address);
                        user.setCodePostal(code_postal);
                        user.setContry(country);
                        user.setPhone(phone);
                        user.setSex(sex);
                        user.setCity(city);
                        mDbase.child("teachers").child(id).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    SharedPreferences.Editor editor = sharedpreferences.edit();
                                    editor.putString("role", "teacher");
                                    editor.commit();
                                    ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(
                                            CompleteInformationUserActivity.this, "mypref", Context.MODE_PRIVATE);
                                    complexPreferences.putObject("current_user", user);
                                    complexPreferences.commit();
                                    progress.dismiss();
                                    startActivity(new Intent(CompleteInformationUserActivity.this, HomeActivity.class));
                                }
                            }
                        });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
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
}
