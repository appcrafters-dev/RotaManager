package com.deeppatel.rotamanager.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.deeppatel.rotamanager.R;
import com.deeppatel.rotamanager.helpers.RedirectToActivity;
import com.squareup.picasso.Picasso;

public class EditStaffMember extends AppCompatActivity {
    private ImageView back, memberDpView;
    private ImageView share;
    private TextView nameView,emailView,designationView,phoneView;

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
        Log.d("Intent", name + email + phone + designation + gender + photoURI);

        nameView = findViewById(R.id.member_name);
        emailView = findViewById(R.id.member_email);
        phoneView = findViewById(R.id.member_phoneNum);
        designationView = findViewById(R.id.member_designation);
        memberDpView = findViewById(R.id.uploadDP);

        nameView.setText(name);
        emailView.setText(email);
        phoneView.setText(phone);
        designationView.setText(designation);
        Picasso.get().load(photoURI).into(memberDpView);

        back = findViewById(R.id.backButtonToolbar);
        share = findViewById(R.id.memberSchedule);
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