package com.deeppatel.rotamanager.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.deeppatel.rotamanager.LoginActivity;
import com.deeppatel.rotamanager.R;
import com.deeppatel.rotamanager.helpers.RedirectToActivity;

public class AdminDashboard extends AppCompatActivity {

    private Button manageStaff,scheduler,timeChangeRequest,profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        manageStaff = findViewById(R.id.manageStaff);
        scheduler = findViewById(R.id.scheduler);
        timeChangeRequest = findViewById(R.id.timeChangeRequest);
        profile = findViewById(R.id.profile);


        manageStaff.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                new RedirectToActivity().redirectActivityOnly(AdminDashboard.this, StaffMemberList.class);
            }
        });

        scheduler.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                new RedirectToActivity().redirectActivityOnly(AdminDashboard.this, AdminScheduler.class);
            }
        });

        timeChangeRequest.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                new RedirectToActivity().redirectActivityOnly(AdminDashboard.this, TimeChangeRequest.class);
            }
        });

        profile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                new RedirectToActivity().redirectActivityOnly(AdminDashboard.this, AdminProfile.class);
            }
        });
    }
}