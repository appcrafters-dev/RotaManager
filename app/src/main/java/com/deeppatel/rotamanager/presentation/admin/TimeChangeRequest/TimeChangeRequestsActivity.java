package com.deeppatel.rotamanager.presentation.admin.TimeChangeRequest;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deeppatel.rotamanager.R;
import com.deeppatel.rotamanager.helpers.Utils;
import com.deeppatel.rotamanager.models.TimeChangeRequestsByDate;
import com.deeppatel.rotamanager.repositories.TimeChangeRequestRepository.FirebaseTimeChangeRequestRepository;
import com.deeppatel.rotamanager.repositories.TimeChangeRequestRepository.TimeChangeRequestRepository;

import java.util.ArrayList;
import java.util.List;

public class TimeChangeRequestsActivity extends AppCompatActivity {
    private final List<TimeChangeRequestsByDate> timeChangeRequests = new ArrayList<>();
    private TimeChangeRequestsByDateAdapter adapter;

    private TimeChangeRequestRepository timeChangeRequestRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_change_request);

        ImageView backButton = findViewById(R.id.iv_back_button);
        RecyclerView parentRecyclerView = findViewById(R.id.parent_recyclerview);

        backButton.setOnClickListener(view -> finish());

        timeChangeRequestRepository = FirebaseTimeChangeRequestRepository.getInstance();
        fetchTimeChangeRequests();

        adapter = new TimeChangeRequestsByDateAdapter(timeChangeRequests, TimeChangeRequestsActivity.this);
        parentRecyclerView.setAdapter(adapter);
        parentRecyclerView.setLayoutManager(new LinearLayoutManager(TimeChangeRequestsActivity.this));
    }

    @SuppressLint("NotifyDataSetChanged")
    void fetchTimeChangeRequests() {
        timeChangeRequestRepository.getAllOpenTimeChangeRequests(result -> {
            if (result.getErrorMessage() != null) {
                Utils.showToastMessage(TimeChangeRequestsActivity.this, result.getErrorMessage());
            } else if (result.getResult() != null) {
                timeChangeRequests.clear();
                timeChangeRequests.addAll(timeChangeRequestRepository.groupTimeChangeRequestsByDate(result.getResult()));
                adapter.notifyDataSetChanged();
            }
        });
    }
}