package com.deeppatel.rotamanager.admin.ManageStaff;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deeppatel.rotamanager.R;
import com.deeppatel.rotamanager.helpers.Navigate;
import com.deeppatel.rotamanager.helpers.Utils;
import com.deeppatel.rotamanager.helpers.adapters.StaffMemberAdapter.StaffMemberDataAdapter;
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
    StaffMemberDataAdapter staffMemberAdapter;

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

        staffMemberAdapter = new StaffMemberDataAdapter(members, StaffMembersActivity.this);
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
                Log.d("members", String.valueOf(result.getResult().size()));
                members.clear();
                members.addAll(result.getResult());
                Log.d("members 2", String.valueOf(members.size()));
                staffMemberAdapter.notifyDataSetChanged();

                Log.d("members 3", String.valueOf(staffMemberAdapter.getItemCount()));
            } else if (errorMessage != null) {
                Utils.showToastMessage(StaffMembersActivity.this, errorMessage);
            }
        });
    }
}