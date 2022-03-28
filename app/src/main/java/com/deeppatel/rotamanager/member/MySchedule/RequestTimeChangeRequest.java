package com.deeppatel.rotamanager.member.MySchedule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.deeppatel.rotamanager.R;
import com.google.firebase.Timestamp;

import java.util.Calendar;
import com.deeppatel.rotamanager.models.NewTimeChangeRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class RequestTimeChangeRequest extends AppCompatActivity {
    private ImageView back;
    private TextView Date,Time;
    private EditText fromTime,toTime,reason;
    Calendar myCalendar = Calendar.getInstance();
    Calendar mcurrentTime = Calendar.getInstance();
    Calendar mcurrentTimeTo = Calendar.getInstance();
    Timestamp fromTimeStamp;
    Timestamp toTimeStamp;

    private Button memberSubmit;
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
                fromTimeStamp = new Timestamp(mcurrentTime.getTime());
                mTimePicker = new TimePickerDialog(RequestTimeChangeRequest.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        boolean isPM = (selectedHour >= 12);
                        fromTime.setText(String.format("%02d:%02d %s", (selectedHour == 12 || selectedHour == 0) ? 12 : selectedHour % 12, minute, isPM ? "PM" : "AM"));
                        mcurrentTime.set(Calendar.HOUR_OF_DAY, selectedHour);
                        mcurrentTime.set(Calendar.MINUTE, selectedMinute);
                        mcurrentTime.set(Calendar.DATE, Integer.valueOf(date));
                        fromTimeStamp = new Timestamp(mcurrentTime.getTime());
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
                        mcurrentTimeTo.set(Calendar.DATE, Integer.valueOf(date));
                        toTimeStamp = new Timestamp(mcurrentTimeTo.getTime());
                    }
                }, hour, minute, false);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });


        back = findViewById(R.id.backButtonToolbar);
        memberSubmit = findViewById(R.id.member_submit);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        memberSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                print(fromTimeStamp + " " + toTimeStamp);
                NewTimeChangeRequest doc = new NewTimeChangeRequest(uid, scheduleID, fromTimeStamp, toTimeStamp, "Pending",reason.getText().toString());
                makeRequest(uid,scheduleID, doc);
                finish();
            }
        });
    }

    public void print(String st){
        Log.i("imp", st);
    }

    public void makeRequest(String uid, String schedid, NewTimeChangeRequest request){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(uid).collection("Schedule").document(schedid).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()) {
                            DocumentSnapshot doc = task.getResult();
                            Log.i("doc", doc.getData().toString());
                        }
                        else
                            Log.i("doc", "Doc not found");
                    }
                });

        db.collection("TimeRequest").document().set(request.toHashMap())
        .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(RequestTimeChangeRequest.this, "New Request Made", Toast.LENGTH_SHORT).show();
                Log.i("New", "Request made");
            }
        });
    }
}