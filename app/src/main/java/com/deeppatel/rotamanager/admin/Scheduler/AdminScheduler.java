package com.deeppatel.rotamanager.admin.Scheduler;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deeppatel.rotamanager.R;
import com.deeppatel.rotamanager.helpers.Navigate;
import com.deeppatel.rotamanager.helpers.Utils;
import com.deeppatel.rotamanager.helpers.adapters.AdminScheduleAdapter.TimeEntryListAdapter;
import com.deeppatel.rotamanager.models.RepositoryResult;
import com.deeppatel.rotamanager.models.TimeEntry;
import com.deeppatel.rotamanager.repositories.OnRepositoryTaskCompleteListener;
import com.deeppatel.rotamanager.repositories.TimeEntryRepository.FirebaseTimeEntryRepository;
import com.deeppatel.rotamanager.repositories.TimeEntryRepository.TimeEntryRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public class AdminScheduler extends AppCompatActivity {
    CalendarView calendar;
    ImageView backButton;
    FloatingActionButton addNewTimeEntriesFloatingActionButton;
    RecyclerView timeEntriesRecyclerView;

    TimeEntryListAdapter timeEntriesAdapter;
    List<TimeEntry> timeEntries;
    List<TimeEntry> allTimeEntries;
    DateTime selectedDate;

    TimeEntryRepository timeEntryRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_scheduler);

        timeEntriesRecyclerView = findViewById(R.id.recyclerView);
        backButton = findViewById(R.id.backButtonToolbar);
        addNewTimeEntriesFloatingActionButton = findViewById(R.id.scheduler_fab);
        calendar = findViewById(R.id.calendar);

        selectedDate = DateTime.now();
        allTimeEntries = new ArrayList<>();
        timeEntries = new ArrayList<>();
        timeEntryRepository = FirebaseTimeEntryRepository.getInstance();
        getAllTimeEntries();


        addNewTimeEntriesFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigate.to(AdminScheduler.this, NewTimeEntry.class, "date", selectedDate.toString());
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                selectedDate = new DateTime(year, month + 1, dayOfMonth, 0, 0);
                updateTimeEntries(selectedDate);
            }
        });
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
        timeEntriesAdapter = new TimeEntryListAdapter(timeEntries, AdminScheduler.this);
        timeEntriesRecyclerView.setHasFixedSize(true);
        timeEntriesRecyclerView.setLayoutManager(new LinearLayoutManager(AdminScheduler.this));
        timeEntriesRecyclerView.setAdapter(timeEntriesAdapter);
    }

    void getAllTimeEntries() {
        timeEntryRepository.getAllTimeEntries(new OnRepositoryTaskCompleteListener<List<TimeEntry>>() {
            @Override
            public void onComplete(@NonNull RepositoryResult<List<TimeEntry>> result) {
                if (result.getErrorMessage() != null) {
                    Utils.showToastMessage(AdminScheduler.this, result.getErrorMessage());
                } else if (result.getResult() != null) {
                    allTimeEntries = result.getResult();
                    setupTimeEntriesAdapter();
                    updateTimeEntries(DateTime.now());
                }
            }
        });
    }
}