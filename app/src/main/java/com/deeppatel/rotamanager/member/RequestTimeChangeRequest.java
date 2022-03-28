package com.deeppatel.rotamanager.member;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.deeppatel.rotamanager.R;
import com.deeppatel.rotamanager.admin.NewTimeEntry;
import com.deeppatel.rotamanager.helpers.Navigate;
import com.google.firebase.Timestamp;

import java.util.Calendar;

public class RequestTimeChangeRequest extends AppCompatActivity {
    private ImageView back;
    private TextView Date,Time;
    private EditText fromTime,toTime,reason;
    Calendar myCalendar = Calendar.getInstance();
    Calendar mcurrentTime = Calendar.getInstance();
    Calendar mcurrentTimeTo = Calendar.getInstance();
    Timestamp fromTimeStamp;
    Timestamp toTimeStamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_time_change_request);

        String uid,date,day,month,oldFrom,oldTo,scheduleID;
        Bundle extras = getIntent().getExtras();
        scheduleID= extras.getString("scheduleID");
        date= extras.getString("date");
        day= extras.getString("day");
        month= extras.getString("month");
        oldFrom= extras.getString("from");
        oldTo= extras.getString("to");

        uid= extras.getString("uid");

        Log.e("@@@@@@@@@@@@@@@@@",scheduleID);

        Date = findViewById(R.id.Date);
        Time = findViewById(R.id.Time);
        fromTime = findViewById(R.id.fromTime);
        toTime= findViewById(R.id.toTime);
        reason= findViewById(R.id.reason);

        Date.setText(date + " " + month);
        Time.setText(oldFrom + " to " + oldTo);

        fromTime.setText(oldFrom);
        fromTime.setClickable(false);
        fromTime.setFocusable(false);
        fromTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mcurrentTime.set(myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DATE));
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(RequestTimeChangeRequest.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        boolean isPM = (selectedHour >= 12);
                        fromTime.setText(String.format("%02d:%02d %s", (selectedHour == 12 || selectedHour == 0) ? 12 : selectedHour % 12, minute, isPM ? "PM" : "AM"));
                        mcurrentTime.set(Calendar.HOUR_OF_DAY, selectedHour);
                        mcurrentTime.set(Calendar.MINUTE, selectedMinute);
                    }
                }, hour, minute, false);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        toTime.setText(oldTo);
        toTime.setClickable(false);
        toTime.setFocusable(false);
        toTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mcurrentTimeTo.set(myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DATE));

                int hour = mcurrentTimeTo.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTimeTo.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                toTimeStamp = new Timestamp(mcurrentTimeTo.getTime());
                mTimePicker = new TimePickerDialog(RequestTimeChangeRequest.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        boolean isPM = (selectedHour >= 12);
                        toTime.setText(String.format("%02d:%02d %s", (selectedHour == 12 || selectedHour == 0) ? 12 : selectedHour % 12, minute, isPM ? "PM" : "AM"));
                        mcurrentTimeTo.set(Calendar.HOUR_OF_DAY, selectedHour);
                        mcurrentTimeTo.set(Calendar.MINUTE, selectedMinute);
                    }
                }, hour, minute, false);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });


        back = findViewById(R.id.backButtonToolbar);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Navigate.replace(RequestTimeChangeRequest.this,MemberHomePage.class);
            }
        });
    }
}