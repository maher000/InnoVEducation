package com.education.innov.innoveducation.Activities;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.education.innov.innoveducation.Entities.HomeWork;
import com.education.innov.innoveducation.R;
import com.education.innov.innoveducation.Utils.Config;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.liuguangqiang.progressbar.CircleProgressBar;
import com.liuguangqiang.swipeback.SwipeBackActivity;
import com.liuguangqiang.swipeback.SwipeBackLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddHomeWorkActivity extends SwipeBackActivity {
    private CircleProgressBar progressBar;
    private SwipeBackLayout swipeBackLayout;
    private EditText eEndDateView, eEndTimeView, EdtTitleHomework, EdtSubjectHomework, EdtDescriptionHomework;
    HomeWork new_homework;
    String dateStart, title, timeStart, timeEnd, dateEnd, description ,author, urlImageAuthor, subject, idClassRoom;
    Date startDate, endDate;
    Button btnAddHomework;
    private int yearStart, monthStart, dayStart;
    private int hour, minute;
    DatabaseReference mDBase = Config.mDatabase;
    ProgressDialog circleProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_home_work);

        eEndDateView = (EditText) findViewById(R.id.txtDateEnd);
        eEndTimeView = (EditText) findViewById(R.id.txtTimeEnd);
        EdtTitleHomework = (EditText) findViewById(R.id.EdtTitleHomework);
        EdtSubjectHomework = (EditText) findViewById(R.id.EdtSubjectHomework);
        EdtDescriptionHomework = (EditText) findViewById(R.id.EdtDescriptionHomework);
        btnAddHomework = (Button) findViewById(R.id.btnAddHomework);

        btnAddHomework.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddHomeWork();
            }
        });


        circleProgressBar = new ProgressDialog(this);
        circleProgressBar.setMax(100);
        circleProgressBar.setCancelable(false);

        eEndDateView.setInputType(InputType.TYPE_NULL);
        eEndTimeView.setInputType(InputType.TYPE_NULL);
        getInfomationUser();
        eEndDateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                new DatePickerDialog(AddHomeWorkActivity.this, myStartDateListener,
                        mYear, mMonth, mDay).show();
            }
        });
        eEndTimeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(AddHomeWorkActivity.this, myEndTimeListener,
                        hour, minute, true).show();
            }
        });
        //  views();
        initViews();
    }


    private DatePickerDialog.OnDateSetListener myStartDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker p, int selectedYear,
                                      int selectedMonth, int selectedDay) {
                    yearStart = selectedYear;
                    monthStart = selectedMonth;
                    dayStart = selectedDay;
                    showDateEnd(yearStart, monthStart, dayStart);
                }
            };

    private void showDateEnd(int year, int month, int day) {
        Locale locale = new Locale("en");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        startDate = calendar.getTime();
        if (new Date().after(startDate)) {
            System.out.println("you have to choose an available date after the current date ");
            Toast.makeText(this, "you have to choose an available date after the current date ", Toast.LENGTH_LONG).show();
            eEndDateView.setText("");
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
            dateEnd = sdf.format(startDate);
            eEndDateView.setText(dateEnd);
        }
    }
    /******************
     * select end time
     **************/
    private TimePickerDialog.OnTimeSetListener myEndTimeListener = new
            TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker p, int hourOfDay, int minuteOfDay) {
                    hour = hourOfDay;
                    minute = minuteOfDay;
                    showTimeEnd(hour, minute);
                }
            };

    private void showTimeEnd(int hourOfDay, int minuteOfDay) {
        String aMpM = " AM";
        if (hourOfDay > 11) {
            aMpM = " PM";
        }
        int currentHour;
        if (hourOfDay > 11) {
            currentHour = hourOfDay - 12;
        } else {
            currentHour = hourOfDay;
        }

        eEndTimeView.setText(currentHour + ":" + minute + aMpM);

    }
    /******************end select start date ***********/
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

    public void getInfomationUser() {

        if (Config.currentTeacher != null)
            System.out.println(Config.currentTeacher);
        author = Config.currentTeacher.getFirstName() + " " + Config.currentTeacher.getLastName();
        urlImageAuthor = Config.currentTeacher.getUrlImage();
    }

    private void AddHomeWork() {
        circleProgressBar.show();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMMM dd 'at' hh:mm aaa");
        dateStart = sdf.format(new Date());
        System.out.println("this date"+dateStart);
        title = EdtTitleHomework.getText().toString();
        subject = EdtSubjectHomework.getText().toString();
        description = EdtDescriptionHomework.getText().toString();
        dateEnd = eEndDateView.getText().toString();
        String endTime = eEndTimeView.getText().toString();
        String id = mDBase.child("homeworks").push().getKey();
        new_homework = new HomeWork();
        new_homework.setStartDate(dateStart.toString());
        new_homework.setTitle(title);
        new_homework.setSubject(subject);
        new_homework.setDescription(description);
        new_homework.setEndDate(dateEnd + " at " + endTime);
        new_homework.setUrlImageAuthor(urlImageAuthor);
        new_homework.setAuthor(author);
        new_homework.setIdClassRom("syrine");
        mDBase.child("homeworks").child(id).setValue(new_homework).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    circleProgressBar.dismiss();
                    System.out.println("post added successfully");
                }
            }

        });
    }


}
