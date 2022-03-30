package com.deeppatel.rotamanager.admin;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.deeppatel.rotamanager.R;
import com.deeppatel.rotamanager.admin.AdminProfile.AdminProfile;
import com.deeppatel.rotamanager.admin.Scheduler.AdminScheduler;
import com.deeppatel.rotamanager.admin.ManageStaff.StaffMembersActivity;
import com.deeppatel.rotamanager.admin.TimeChangeRequest.TimeChangeRequest;
import com.deeppatel.rotamanager.helpers.Navigate;
import com.deeppatel.rotamanager.helpers.dataLoad;

public class AdminDashboard extends AppCompatActivity {

    Button manageStaff, scheduler, timeChangeRequest, profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        manageStaff = findViewById(R.id.manageStaff);
        scheduler = findViewById(R.id.scheduler);
        timeChangeRequest = findViewById(R.id.timeChangeRequest);
        profile = findViewById(R.id.profile);
        dataLoad.makeRequest();


        manageStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigate.to(AdminDashboard.this, StaffMembersActivity.class);
            }
        });

        scheduler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigate.to(AdminDashboard.this, AdminScheduler.class);
            }
        });

        timeChangeRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("check", dataLoad.timeChangeRequestChildModelList.get(0).getUid());
                Navigate.to(AdminDashboard.this, TimeChangeRequest.class);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigate.to(AdminDashboard.this, AdminProfile.class);
            }
        });
    }
}