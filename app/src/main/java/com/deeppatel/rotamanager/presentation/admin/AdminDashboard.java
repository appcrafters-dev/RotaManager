package com.deeppatel.rotamanager.presentation.admin;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.deeppatel.rotamanager.R;
import com.deeppatel.rotamanager.presentation.admin.AdminProfile.AdminProfile;
import com.deeppatel.rotamanager.presentation.admin.Scheduler.AdminSchedulerActivity;
import com.deeppatel.rotamanager.presentation.admin.ManageStaff.StaffMembersActivity;
import com.deeppatel.rotamanager.presentation.admin.TimeChangeRequest.TimeChangeRequestsActivity;
import com.deeppatel.rotamanager.helpers.Navigate;

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


        manageStaff.setOnClickListener(view -> Navigate.to(AdminDashboard.this, StaffMembersActivity.class));

        scheduler.setOnClickListener(view -> Navigate.to(AdminDashboard.this, AdminSchedulerActivity.class));

        timeChangeRequest.setOnClickListener(view -> Navigate.to(AdminDashboard.this, TimeChangeRequestsActivity.class));

        profile.setOnClickListener(view -> Navigate.to(AdminDashboard.this, AdminProfile.class));

    }
}