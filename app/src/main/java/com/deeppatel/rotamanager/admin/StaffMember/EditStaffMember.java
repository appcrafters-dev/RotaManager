package com.deeppatel.rotamanager.admin.StaffMember;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.deeppatel.rotamanager.R;
import com.deeppatel.rotamanager.admin.MemberTimeTable;
import com.deeppatel.rotamanager.helpers.Navigate;
import com.deeppatel.rotamanager.models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class EditStaffMember extends AppCompatActivity {
    private ImageView back, memberDpView;
    private ImageView share;

    private TextView nameView, emailView, designationView, phoneView;
    private Button update;

    //:TODO RadioButton loading

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_staff_member);
            Intent intent = getIntent();
        User user = intent.getExtras().getParcelable("member");
        String name = user.getName();
        String email = user.getEmail();
        String phone = user.getPhone();
        String designation = user.getDesignation();
        String gender = user.getGender();
        String uid = user.getUid();

        nameView = findViewById(R.id.member_name);
        emailView = findViewById(R.id.member_email);
        emailView.setClickable(false);
        emailView.setFocusable(false);
        phoneView = findViewById(R.id.member_phoneNum);
        designationView = findViewById(R.id.member_designation);
        memberDpView = findViewById(R.id.uploadDP);
        update = findViewById(R.id.member_submit);
        Log.d("Intent", name + " " + email + " " + phone + " " + designation + " " + gender + " ");

        nameView.setText(name);
        emailView.setText(email);
        phoneView.setText(phone);
        designationView.setText(designation);

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
                        .update(userUpdate)
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
                finish();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Navigate.to(EditStaffMember.this, MemberTimeTable.class, "user", user);
            }
        });

    }
}