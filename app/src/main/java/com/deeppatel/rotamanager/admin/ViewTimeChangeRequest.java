package com.deeppatel.rotamanager.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.deeppatel.rotamanager.R;
import com.google.firebase.Timestamp;

import java.util.Calendar;
import java.util.Locale;

public class ViewTimeChangeRequest extends AppCompatActivity {
    private ImageView back;
    private TextView Date,Time;
    private EditText fromTime,toTime,reason;
    private Button request_reject,request_approve;
    Calendar myCalendar = Calendar.getInstance();
    Calendar mcurrentTime = Calendar.getInstance();
    Calendar mcurrentTimeTo = Calendar.getInstance();
    Timestamp fromTimeStamp;
    Timestamp toTimeStamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_time_change_request);

        String uid,date,day,month,oldFrom,oldTo,scheduleID;
        Bundle extras = getIntent().getExtras();
        scheduleID= extras.getString("scheduleID");
        date= extras.getString("date");
        day= extras.getString("day");
        month= extras.getString("month");
        oldFrom= extras.getString("from");
        oldTo= extras.getString("to");
        uid= extras.getString("uid");

//        Log.e("@@@@@@@@@@@@@@@@@",scheduleID);

        Date = findViewById(R.id.Date);
        Time = findViewById(R.id.Time);
        fromTime = findViewById(R.id.fromTime);
        toTime= findViewById(R.id.toTime);
        reason= findViewById(R.id.reason);

        Date.setText(date + month.toUpperCase(Locale.ROOT));
        Time.setText(oldFrom +"to " + oldTo);

        fromTime.setText(oldFrom);
        fromTime.setClickable(false);
        fromTime.setFocusable(false);

        toTime.setText(oldTo);
        toTime.setClickable(false);
        toTime.setFocusable(false);

        reason.setClickable(false);
        reason.setFocusable(false);

        request_reject = findViewById(R.id.request_reject);
        request_reject.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                finish();
            }
        });

        request_approve = findViewById(R.id.request_approve);
        request_approve.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                finish();
            }
        });

        back = findViewById(R.id.backButtonToolbar);
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                finish();
            }
        });
    }
}