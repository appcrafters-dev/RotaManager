package com.deeppatel.rotamanager.admin.Scheduler;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deeppatel.rotamanager.R;
import com.deeppatel.rotamanager.helpers.Navigate;
import com.deeppatel.rotamanager.helpers.Utils;
import com.deeppatel.rotamanager.helpers.adapters.AdminScheduleAdapter.TimeEntryListAdapter;
import com.deeppatel.rotamanager.models.TimeEntry;
import com.deeppatel.rotamanager.repositories.TimeEntryRepository.FirebaseTimeEntryRepository;
import com.deeppatel.rotamanager.repositories.TimeEntryRepository.TimeEntryRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public class AdminSchedulerActivity extends AppCompatActivity {
    private RecyclerView timeEntriesRecyclerView;
    private TimeEntryListAdapter timeEntriesAdapter;

    private final List<TimeEntry> timeEntries = new ArrayList<>();
    private List<TimeEntry> allTimeEntries = new ArrayList<>();
    private DateTime selectedDate;

    private TimeEntryRepository timeEntryRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_scheduler);

        timeEntriesRecyclerView = findViewById(R.id.rv_time_entries);
        ImageView backButton = findViewById(R.id.iv_back_button);
        FloatingActionButton addNewTimeEntryFAB = findViewById(R.id.fab_new_time_entry);
        CalendarView calendar = findViewById(R.id.cv_calendar);

        backButton.setOnClickListener(view -> finish());
        addNewTimeEntryFAB.setOnClickListener(v -> Navigate.to(AdminSchedulerActivity.this, NewTimeEntry.class, "date", selectedDate.toString()));
        calendar.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            selectedDate = new DateTime(year, month + 1, dayOfMonth, 0, 0);
            updateTimeEntries(selectedDate);
        });
        setupTimeEntriesAdapter();

        selectedDate = DateTime.now();
        timeEntryRepository = FirebaseTimeEntryRepository.getInstance();
        getAllTimeEntries();
    }

    @SuppressLint("NotifyDataSetChanged")
    void updateTimeEntries(DateTime selectedDate) {
        timeEntries.clear();
        for (TimeEntry timeEntry : allTimeEntries) {
            timeEntry.getDateString();
            if (timeEntry.getDateString().equals(Utils.dateTimeToDateString(selectedDate))) {
                timeEntries.add(timeEntry);
            }
        }
        timeEntriesAdapter.notifyDataSetChanged();
    }

    void setupTimeEntriesAdapter() {
        timeEntriesAdapter = new TimeEntryListAdapter(timeEntries, AdminSchedulerActivity.this);
        timeEntriesRecyclerView.setHasFixedSize(true);
        timeEntriesRecyclerView.setLayoutManager(new LinearLayoutManager(AdminSchedulerActivity.this));
        timeEntriesRecyclerView.setAdapter(timeEntriesAdapter);
    }

    void getAllTimeEntries() {
        timeEntryRepository.getAllTimeEntries(result -> {
            if (result.getErrorMessage() != null) {
                Utils.showToastMessage(AdminSchedulerActivity.this, result.getErrorMessage());
            } else if (result.getResult() != null) {
                allTimeEntries = result.getResult();
                setupTimeEntriesAdapter();
                updateTimeEntries(DateTime.now());
            }
        });
    }
}