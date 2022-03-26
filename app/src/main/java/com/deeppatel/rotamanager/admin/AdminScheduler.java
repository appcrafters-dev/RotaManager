package com.deeppatel.rotamanager.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deeppatel.rotamanager.R;
import com.deeppatel.rotamanager.helpers.MemberTimetableAdapter;
import com.deeppatel.rotamanager.helpers.MemberTimetableModel;
import com.deeppatel.rotamanager.helpers.RedirectToActivity;
import com.deeppatel.rotamanager.helpers.TimeEntryListAdapter;
import com.deeppatel.rotamanager.helpers.TimeEntryListModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;

public class AdminScheduler extends AppCompatActivity {
    private ImageView back;
    CalendarView calendar;
    private FloatingActionButton mFab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_scheduler);

        back = findViewById(R.id.backButtonToolbar);
        mFab = findViewById(R.id.scheduler_fab);

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                new RedirectToActivity().redirectActivityAfterFinish(AdminScheduler.this, NewTimeEntry.class);
            }
        });

        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                new RedirectToActivity().redirectActivityAfterFinish(AdminScheduler.this, AdminDashboard.class);
            }
        });

        calendar = (CalendarView) findViewById(R.id.calendar);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view,
                    int year,
                    int month,
                    int dayOfMonth)
            {
                String Date = dayOfMonth + "-" + (month + 1) + "-" + year;
//                date_view.setText(Date);
            }
        });

        TimeEntryListModel[] myListData = new TimeEntryListModel[]{
                new TimeEntryListModel("12", "Tue", "12", "John Doe", "9:00 AM to 1:00 PM", "March"),
                new TimeEntryListModel("12", "Tue", "12", "John Doe", "9:00 AM to 1:00 PM", "March"),
                new TimeEntryListModel("12", "Tue", "12", "John Doe", "9:00 AM to 1:00 PM", "March"),
                new TimeEntryListModel("12", "Tue", "12", "John Doe", "9:00 AM to 1:00 PM", "March"),
                new TimeEntryListModel("12", "Tue", "12", "John Doe", "9:00 AM to 1:00 PM", "March"),
                new TimeEntryListModel("12", "Tue", "12", "John Doe", "9:00 AM to 1:00 PM", "March"),
                new TimeEntryListModel("12", "Tue", "12", "John Doe", "9:00 AM to 1:00 PM", "March"),
        };

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        TimeEntryListAdapter adapter = new TimeEntryListAdapter(myListData, AdminScheduler.this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(AdminScheduler.this));
        recyclerView.setAdapter(adapter);
    }
}