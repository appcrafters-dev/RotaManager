package com.deeppatel.rotamanager.presentation.admin.ManageStaff;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deeppatel.rotamanager.R;
import com.deeppatel.rotamanager.helpers.Navigate;
import com.deeppatel.rotamanager.helpers.Utils;
import com.deeppatel.rotamanager.models.User;
import com.deeppatel.rotamanager.repositories.UserRepository.FirebaseUserRepository;
import com.deeppatel.rotamanager.repositories.UserRepository.UserRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class StaffMembersActivity extends AppCompatActivity {
    ImageView backButton;
    FloatingActionButton addNewStaffMemberFloatingActionButton;
    RecyclerView staffMembersRecyclerView;
    StaffMembersAdapter staffMemberAdapter;

    List<User> members = new ArrayList<>();
    UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_member_list);

        backButton = findViewById(R.id.btn_back);
        addNewStaffMemberFloatingActionButton = findViewById(R.id.fab_new_staff_member);
        staffMembersRecyclerView = findViewById(R.id.rv_staff_members);

        addNewStaffMemberFloatingActionButton.setOnClickListener(v -> Navigate.to(StaffMembersActivity.this, NewStaffMemberActivity.class));
        backButton.setOnClickListener(view -> finish());

        staffMemberAdapter = new StaffMembersAdapter(members, StaffMembersActivity.this);
        staffMembersRecyclerView.setLayoutManager(new LinearLayoutManager(StaffMembersActivity.this));
        staffMembersRecyclerView.setAdapter(staffMemberAdapter);

        userRepository = FirebaseUserRepository.getInstance();
        fetchStaffMembers();
    }

    @SuppressLint("NotifyDataSetChanged")
    void fetchStaffMembers() {
        userRepository.getStaffMembers(result -> {
            String errorMessage = result.getErrorMessage();
            if (result.getResult() != null) {
                members.clear();
                members.addAll(result.getResult());
                staffMemberAdapter.notifyDataSetChanged();
            } else if (errorMessage != null) {
                Utils.showToastMessage(StaffMembersActivity.this, errorMessage);
            }
        });
    }
}