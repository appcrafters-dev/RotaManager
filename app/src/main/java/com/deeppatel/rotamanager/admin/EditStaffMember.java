package com.deeppatel.rotamanager.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.deeppatel.rotamanager.R;
import com.deeppatel.rotamanager.helpers.RedirectToActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.squareup.picasso.Picasso;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditStaffMember extends AppCompatActivity {
    private ImageView back, memberDpView;
    private ImageView share;
    private TextView nameView,emailView,designationView,phoneView;
    private Button update;

    //:TODO RadioButton loading

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_staff_member);
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String email = intent.getStringExtra("email");
        String phone = intent.getStringExtra("phone");
        String designation = intent.getStringExtra("designation");
        String gender = intent.getStringExtra("gender");
        String photoURI = intent.getStringExtra("photoURI");
        String uid = intent.getStringExtra("uid");

        nameView = findViewById(R.id.member_name);
        emailView = findViewById(R.id.member_email);
        phoneView = findViewById(R.id.member_phoneNum);
        designationView = findViewById(R.id.member_designation);
        memberDpView = findViewById(R.id.uploadDP);
        update = findViewById(R.id.member_submit);
        Log.d("Intent", name + " " + email + " " + phone + " " + designation + " " + gender + " " + photoURI + " ");

        nameView.setText(name);
        emailView.setText(email);
        phoneView.setText(phone);
        designationView.setText(designation);
        Picasso.get().load(photoURI).into(memberDpView);

        back = findViewById(R.id.backButtonToolbar);
        share = findViewById(R.id.memberSchedule);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String updatedName = nameView.getText().toString();
                String updatedEmail = emailView.getText().toString();
                String updatedPhone = phoneView.getText().toString();
                String updatedDesignation = designationView.getText().toString();

                Map<String, Object> userUpdate = new HashMap<>();
                userUpdate.put("name", updatedName);
                userUpdate.put("email", updatedEmail);
                userUpdate.put("phone", updatedPhone);
                userUpdate.put("gender", gender);
                userUpdate.put("designation", updatedDesignation);
                Log.i("Update To Be Values", userUpdate.toString());
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("users").document(uid)
                        .set(userUpdate)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d("FireStore", "DocumentSnapshot successfully re-written!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("Staff", "Error adding document", e);
                            }
                        });
                Toast.makeText(EditStaffMember.this, "User Updated", Toast.LENGTH_SHORT).show();

            }
        });

        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                new RedirectToActivity().redirectActivityAfterFinish(EditStaffMember.this, StaffMemberList.class);
            }
        });
        share.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                new RedirectToActivity().redirectActivityAfterFinish(EditStaffMember.this, MemberTimeTable.class);
            }
        });

    }
}