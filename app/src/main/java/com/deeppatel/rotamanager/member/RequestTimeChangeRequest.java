package com.deeppatel.rotamanager.member;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.deeppatel.rotamanager.R;
import com.deeppatel.rotamanager.admin.TimeChangeRequest;
import com.deeppatel.rotamanager.helpers.Navigate;
import com.deeppatel.rotamanager.models.NewTimeChangeRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class RequestTimeChangeRequest extends AppCompatActivity {
    private ImageView back;
    private TextView Date,Time;
    private EditText fromTime,toTime,reason;
    private Button memberSubmit;
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
        toTime.setText(to);


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

                makeRequest(uid,scheduleID, );
            }
        });
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

            }
        });
    }
}