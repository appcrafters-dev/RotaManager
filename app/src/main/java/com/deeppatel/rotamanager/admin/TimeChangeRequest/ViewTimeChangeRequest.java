package com.deeppatel.rotamanager.admin.TimeChangeRequest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.deeppatel.rotamanager.R;
import com.deeppatel.rotamanager.models.TimeChangeRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
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

        Date = findViewById(R.id.et_date);
        Time = findViewById(R.id.Time);
        fromTime = findViewById(R.id.ed_from_time);
        toTime= findViewById(R.id.ed_to_time);
        reason= findViewById(R.id.reason);

        Date.setText(date + month.toUpperCase(Locale.ROOT));
        Time.setText(oldFrom +" to " + oldTo);

        fromTime.setText(oldFrom);
        fromTime.setClickable(false);
        fromTime.setFocusable(false);
        mcurrentTime.set(Calendar.DAY_OF_MONTH, Integer.valueOf(date));
        String str = oldFrom;
        String[] arrOfStr = str.split(":", -2);

        mcurrentTime.set(Calendar.HOUR, Integer.valueOf(arrOfStr[0]));
        String str2 = arrOfStr[1];
        String[] arrOfStr2 = str2.split(" ", -2);
        mcurrentTime.set(Calendar.MINUTE, Integer.valueOf(arrOfStr2[0]));
        // TODO: TimeStamps to be updated
        fromTimeStamp = new Timestamp(mcurrentTime.getTime());

        toTime.setText(oldTo);
        toTime.setClickable(false);
        toTime.setFocusable(false);
        String str3 = oldTo;
        String[] arrOfStr3 = str3.split(":", -2);

        mcurrentTimeTo.set(Calendar.HOUR, Integer.valueOf(arrOfStr3[0]));
        String str4 = arrOfStr3[1];
        String[] arrOfStr4 = str4.split(" ", -2);
        mcurrentTimeTo.set(Calendar.MINUTE, Integer.valueOf(arrOfStr4[0]));
        // TODO: TimeStamps to be updated
        toTimeStamp = new Timestamp(mcurrentTimeTo.getTime());

        reason.setClickable(false);
        reason.setFocusable(false);

        TimeChangeRequest update = new TimeChangeRequest(uid, "name", scheduleID, fromTimeStamp, toTimeStamp, "Pending", reason.getText().toString());
        request_reject = findViewById(R.id.request_reject);
        request_reject.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                update.setStatus("Rejected");
                makeRequest("Ju3qU20n4xBtOTuRsVF5", update);
                finish();
            }
        });

        request_approve = findViewById(R.id.request_approve);
        request_approve.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                update.setStatus("Approve");
                Log.i("asd", update.toHashMap().toString());
                Log.i("asd2", update.getStatus().toString() + " " + update.getUidUser() + " " + update.getUidSchedule());
                makeRequest("Ju3qU20n4xBtOTuRsVF5", update);
                finish();
            }
        });

        back = findViewById(R.id.iv_back_button);
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                finish();
            }
        });
    }

    private void makeRequest(String uidTime, TimeChangeRequest updateStatus){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("TimeRequest").document(uidTime).update("status", updateStatus.getStatus())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(updateStatus.getStatus() == "Approve"){

                            HashMap<String, Object> x = new HashMap<>();
                            x.put("from", updateStatus.getFrom());
                            x.put("to", updateStatus.getTo());
                            Log.i("UID SCHED", updateStatus.getUidSchedule());
                            db.collection("users").document(updateStatus.getUidUser()).collection("Schedule").document(updateStatus.getUidSchedule())
                                    .update(x)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()) {
                                                Log.i("Done", "Update");
                                            }else{
                                                Log.i("Not Done", "Update");
                                            }
                                        }
                                    });
                        }
                        Toast.makeText(ViewTimeChangeRequest.this, "New Request Made", Toast.LENGTH_SHORT).show();
                        Log.i("New", "Request made");
                    }
                });
    }
}