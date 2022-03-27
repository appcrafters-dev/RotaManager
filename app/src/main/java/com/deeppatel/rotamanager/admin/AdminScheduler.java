package com.deeppatel.rotamanager.admin;

import android.os.Build;
import android.os.Bundle;
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
import com.deeppatel.rotamanager.helpers.TimeEntryListAdapter;
import com.deeppatel.rotamanager.helpers.TimeEntryListModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class AdminScheduler extends AppCompatActivity {
    CalendarView calendar;
    private ImageView back;
    private FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_scheduler);

        back = findViewById(R.id.backButtonToolbar);
        mFab = findViewById(R.id.scheduler_fab);

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigate.to(AdminScheduler.this, NewTimeEntry.class);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        List<TimeEntryListModel> myListData = new ArrayList<>();
        List<TimeEntryListModel> myListData2 = new ArrayList<>();
//        myListData2 = myListData;

        myListData.add(new TimeEntryListModel("12", "Tue", "12", "John Doe", "9:00 AM to 1:00 PM", "March"));
        myListData.add(new TimeEntryListModel("12", "Tue", "12", "John Doe", "9:00 AM to 1:00 PM", "March"));
        myListData.add(new TimeEntryListModel("12", "Tue", "13", "John Doe", "9:00 AM to 1:00 PM", "March"));
        myListData.add(new TimeEntryListModel("12", "Tue", "14", "John Doe", "9:00 AM to 1:00 PM", "March"));

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        TimeEntryListAdapter adapter = new TimeEntryListAdapter(myListData2, AdminScheduler.this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(AdminScheduler.this));
        recyclerView.setAdapter(adapter);

        calendar = findViewById(R.id.calendar);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view,
                                            int year,
                                            int month,
                                            int dayOfMonth) {
                String Date = dayOfMonth + "-" + Month.of(month + 1).name().toUpperCase();

                myListData2.clear();
                for (TimeEntryListModel x : myListData) {
                    String fu = x.getDate() + "-" + x.getMonth().toUpperCase();
                    if (fu.equals(Date)) {
                        myListData2.add(new TimeEntryListModel("12", "Tue", "12", "John Doe", "9:00 AM to 1:00 PM", "March"));
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });


    }
}