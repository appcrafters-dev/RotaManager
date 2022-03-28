package com.deeppatel.rotamanager.member;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.deeppatel.rotamanager.R;
import com.deeppatel.rotamanager.helpers.Navigate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class RequestTimeChangeRequest extends AppCompatActivity {
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_time_change_request);
        makeRequest("2AMnInXgtgMeqWKiTTuf1spgkw13", "VrTucto4h5sauZH1JxZy");
        back = findViewById(R.id.backButtonToolbar);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigate.replace(RequestTimeChangeRequest.this,MemberHomePage.class);
            }
        });
    }

    public void makeRequest(String uid, String schedid){
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
    }
}