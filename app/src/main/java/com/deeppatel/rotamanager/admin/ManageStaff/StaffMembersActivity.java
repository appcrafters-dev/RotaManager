package com.deeppatel.rotamanager.admin.ManageStaff;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deeppatel.rotamanager.R;
import com.deeppatel.rotamanager.helpers.Navigate;
import com.deeppatel.rotamanager.helpers.adapters.StaffMemberAdapter.StaffMemberDataAdapter;
import com.deeppatel.rotamanager.helpers.Utils;
import com.deeppatel.rotamanager.models.User;
import com.deeppatel.rotamanager.models.RepositoryResult;
import com.deeppatel.rotamanager.repositories.OnRepositoryTaskCompleteListener;
import com.deeppatel.rotamanager.repositories.UserRepository.FirebaseUserRepository;
import com.deeppatel.rotamanager.repositories.UserRepository.UserRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class StaffMembersActivity extends AppCompatActivity {
    ImageView backButton;
    FloatingActionButton addNewStaffMemberFloatingActionButton;
    RecyclerView staffMembersRecyclerView;

    UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_member_list);

        backButton = findViewById(R.id.btn_back);
        addNewStaffMemberFloatingActionButton = findViewById(R.id.fab_new_staff_member);
        staffMembersRecyclerView = findViewById(R.id.rv_staff_members);

        addNewStaffMemberFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigate.to(StaffMembersActivity.this, NewStaffMemberActivity.class);
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        userRepository = FirebaseUserRepository.getInstance();
        fetchStaffMembers();
    }

    void fetchStaffMembers(){
        userRepository.getStaffMembers(new OnRepositoryTaskCompleteListener<List<com.deeppatel.rotamanager.models.User>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onComplete(@NonNull RepositoryResult<List<User>> result) {
                List<User> members = result.getResult();
                String errorMessage = result.getErrorMessage();
                if (members != null){
                    StaffMemberDataAdapter adapter = new StaffMemberDataAdapter(members, StaffMembersActivity.this);
                    staffMembersRecyclerView.setHasFixedSize(true);
                    staffMembersRecyclerView.setLayoutManager(new LinearLayoutManager(StaffMembersActivity.this));
                    staffMembersRecyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else if (errorMessage != null) {
                    Utils.showToastMessage(StaffMembersActivity.this, errorMessage);
                }
            }
        });
    }
}