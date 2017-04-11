package com.education.innov.innoveducation.Activities;

import android.app.DatePickerDialog;
import android.content.res.Configuration;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.education.innov.innoveducation.R;
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
    private EditText eStartDateView;
    private EditText eEndDateView;
    private EditText eStartTimeView;
    private EditText eEndTimeView;
    String dateStart, dateEnd;
    Date startDate , endDate ;
    private int yearStart, monthStart, dayStart;
    private int hour, minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_home_work);

        eStartDateView =(EditText)findViewById(R.id.txtDateStart);
        eStartTimeView =(EditText)findViewById(R.id.txtTimeStart);
        eEndDateView =(EditText)findViewById(R.id.txtDateEnd);
        eEndTimeView =(EditText)findViewById(R.id.txtTimeEnd);
        views();
        initViews();
    }






    private void views(){
        eStartDateView.setInputType(InputType.TYPE_NULL);
        eStartTimeView.setInputType(InputType.TYPE_NULL);
        eEndDateView.setInputType(InputType.TYPE_NULL);
        eEndTimeView.setInputType(InputType.TYPE_NULL);

        eStartDateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                new DatePickerDialog(this, myStartDateListener,
                        mYear, mMonth, mDay).show();
            }
        });

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
            eStartDateView.setText("");
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
            dateStart = sdf.format(startDate);
            eStartDateView.setText(dateStart);
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
}
