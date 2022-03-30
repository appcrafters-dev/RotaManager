package com.deeppatel.rotamanager.admin.ManageStaff;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.deeppatel.rotamanager.R;
import com.deeppatel.rotamanager.helpers.Navigate;
import com.deeppatel.rotamanager.member.MySchedule.MemberTimeEntriesFragment;
import com.deeppatel.rotamanager.models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditStaffMemberActivity extends AppCompatActivity {
    private ImageView back, memberDpView;
    private ImageView memberSchedule;

    private TextView nameView, emailView, designationView, phoneView,memberNameViewToolbar;
    private Button update,share;

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
        String inviteCode = user.getInviteCode();

        nameView = findViewById(R.id.member_name);
        emailView = findViewById(R.id.member_email);
        memberNameViewToolbar = findViewById(R.id.memberNameViewToolbar);
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
        memberNameViewToolbar.setText(name);
        designationView.setText(designation);

        back = findViewById(R.id.backButtonToolbar);
        memberSchedule = findViewById(R.id.memberSchedule);
        share = findViewById(R.id.member_share);

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
                Toast.makeText(EditStaffMemberActivity.this, "User Updated", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        memberSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Navigate.to(EditStaffMemberActivity.this, MemberTimeEntriesActivity.class, "user", user);
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                Toast.makeText(getApplicationContext(),"Invite code copied to clipboard.",Toast.LENGTH_SHORT).show();
                ClipData clip = ClipData.newPlainText("Invite code", inviteCode);
                clipboard.setPrimaryClip(clip);
            }
        });

    }
}