package com.deeppatel.rotamanager.member;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.deeppatel.rotamanager.R;
import com.deeppatel.rotamanager.helpers.Navigate;

public class RequestTimeChangeRequest extends AppCompatActivity {
    private ImageView back;
    private TextView Date,Time;
    private EditText fromTime,toTime,reason;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_time_change_request);

        String uid,date,day,month,from,to,scheduleID;
        Bundle extras = getIntent().getExtras();
        scheduleID= extras.getString("scheduleID");
        date= extras.getString("date");
        day= extras.getString("day");
        month= extras.getString("month");
        from= extras.getString("from");
        to= extras.getString("to");

        uid= extras.getString("uid");

        Log.e("@@@@@@@@@@@@@@@@@",scheduleID);

        Date = findViewById(R.id.Date);
        Time = findViewById(R.id.Time);
        fromTime = findViewById(R.id.fromTime);
        toTime= findViewById(R.id.toTime);
        reason= findViewById(R.id.reason);

        Date.setText(date + " " + month);
        Time.setText(from + " to " + to);

        fromTime.setText(from);
        fromTime.setFocusable(false);
        fromTime.setClickable(false);
        toTime.setText(to);
        toTime.setFocusable(false);
        toTime.setClickable(false);


        back = findViewById(R.id.backButtonToolbar);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigate.replace(RequestTimeChangeRequest.this,MemberHomePage.class);
            }
        });
    }
}