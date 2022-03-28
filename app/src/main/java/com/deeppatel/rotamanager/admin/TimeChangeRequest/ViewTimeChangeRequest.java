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
import com.deeppatel.rotamanager.models.NewTimeChangeRequest;
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
        // TODO: TimeStamps to be updated
        fromTimeStamp = new Timestamp(mcurrentTime.getTime());

        toTime.setText(oldTo);
        toTime.setClickable(false);
        toTime.setFocusable(false);

        reason.setClickable(false);
        reason.setFocusable(false);

        NewTimeChangeRequest update = new NewTimeChangeRequest(uid, scheduleID, fromTimeStamp, toTimeStamp, "Pending", reason.getText().toString());
        request_reject = findViewById(R.id.request_reject);
        request_reject.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                update.setStatus("Rejected");
                makeRequest("txVYO4pjLioZQusSAhDy", update);
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
//                makeRequest("txVYO4pjLioZQusSAhDy", update);
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

    private void makeRequest(String uidTime, NewTimeChangeRequest updateStatus){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("TimeRequest").document(uidTime).update("status", updateStatus.getStatus())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(updateStatus.getStatus() == "Approve"){

                            HashMap<String, Object> x = new HashMap<>();
                            x.put("from", updateStatus.getFrom());
                            x.put("to", updateStatus.getTo());

                            db.collection("user").document(updateStatus.getUidUser()).collection("Schedule").document(updateStatus.getUidSchedule())
                                    .update(x)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Log.i("Done", "Update");
                                        }
                                    });
                        }
                        Toast.makeText(ViewTimeChangeRequest.this, "New Request Made", Toast.LENGTH_SHORT).show();
                        Log.i("New", "Request made");
                    }
                });
    }
}