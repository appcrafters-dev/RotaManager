package com.deeppatel.rotamanager.member.MySchedule;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.deeppatel.rotamanager.R;
import com.deeppatel.rotamanager.helpers.Utils;
import com.deeppatel.rotamanager.models.TimeChangeRequest;
import com.deeppatel.rotamanager.models.TimeEntry;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.joda.time.DateTime;

public class RequestTimeChangeActivity extends AppCompatActivity {
    Timestamp fromTimeStamp;
    Timestamp toTimeStamp;
    private DateTime fromTime;
    private DateTime toTime;
    private ImageView back;
    private TextView dateTextVIew, timeTextView;
    private EditText fromTimeEditText, toTimeEditText, reasonEditText;
    private Button sbmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_time_change_request);


        Intent intent = getIntent();
        TimeEntry timeEntry = intent.getParcelableExtra("time_entry");

        dateTextVIew = findViewById(R.id.Date);
        timeTextView = findViewById(R.id.Time);
        fromTimeEditText = findViewById(R.id.fromTime);
        toTimeEditText = findViewById(R.id.toTime);
        reasonEditText = findViewById(R.id.reason);

        DateTime date = timeEntry.getDate();
        dateTextVIew.setText(Utils.dateTimeToString(date, "e MMMM, yyyy"));
        timeTextView.setText(String.format("%S to %S", timeEntry.getFromString(), timeEntry.getToString()));

        fromTimeEditText.setText(timeEntry.getFromString());
        fromTimeEditText.setClickable(false);
        fromTimeEditText.setFocusable(false);
        fromTimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromTime = new DateTime(date);
                TimePickerDialog fromTimePicker = new TimePickerDialog(RequestTimeChangeActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        fromTime = new DateTime(fromTime).withTime(selectedHour, selectedMinute, 0, 0);
                        fromTimeEditText.setText(Utils.dateTimeToString(fromTime, Utils.TIME_FORMAT));
                    }
                }, fromTime.getHourOfDay(), fromTime.getMinuteOfHour(), false);
                fromTimePicker.setTitle("Select Time");
                fromTimePicker.show();
            }
        });

        toTimeEditText.setText(timeEntry.getToString());
        toTimeEditText.setClickable(false);
        toTimeEditText.setFocusable(false);
        toTimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toTime = new DateTime(date);
                TimePickerDialog toTimePicker = new TimePickerDialog(RequestTimeChangeActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        toTime = new DateTime(toTime).withTime(selectedHour, selectedMinute, 0, 0);
                        toTimeEditText.setText(Utils.dateTimeToString(toTime, Utils.TIME_FORMAT));
                    }
                }, toTime.getHourOfDay(), toTime.getMinuteOfHour(), false);
                toTimePicker.setTitle("Select Time");
                toTimePicker.show();
            }
        });


        back = findViewById(R.id.iv_back_button);
        sbmitButton = findViewById(R.id.member_submit);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        sbmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                TimeChangeRequest doc = new TimeChangeRequest(uid, name, scheduleID, fromTimeStamp, toTimeStamp, "Pending", reasonEditText.getText().toString());
//                makeRequest(uid, scheduleID, doc);
//                finish();
            }
        });
    }

    public void print(String st) {
        Log.i("imp", st);
    }

    public void makeRequest(String uid, String schedid, TimeChangeRequest request) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(uid).collection("Schedule").document(schedid).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot doc = task.getResult();
                            Log.i("doc", doc.getData().toString());
                        } else
                            Log.i("doc", "Doc not found");
                    }
                });

        db.collection("TimeRequest").document().set(request.toHashMap())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(RequestTimeChangeActivity.this, "New Request Made", Toast.LENGTH_SHORT).show();
                        Log.i("New", "Request made");
                    }
                });
    }
}