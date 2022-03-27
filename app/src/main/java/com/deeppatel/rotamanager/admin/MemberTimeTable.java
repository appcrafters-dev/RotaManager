package com.deeppatel.rotamanager.admin;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.deeppatel.rotamanager.R;
import com.deeppatel.rotamanager.helpers.MemberTimetableAdapter;
import com.deeppatel.rotamanager.helpers.MemberTimetableModel;
import com.deeppatel.rotamanager.helpers.RedirectToActivity;
import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MemberTimeTable extends AppCompatActivity {
    private ImageView back;
    String myFormat = "MMMM, yyyy", monthFormatt = "MMMM";
    SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US), monthFormat = new SimpleDateFormat(monthFormatt, Locale.US);
    final Calendar myCalendar = Calendar.getInstance(Locale.US);
    Calendar calendar = Calendar.getInstance(Locale.US);
    private TextView dateView;

    List<MemberTimetableModel> myListData = new ArrayList<>();
    List<MemberTimetableModel> myListData2 = new ArrayList<>();
    MemberTimetableAdapter adapter;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_time_table);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        adapter = new MemberTimetableAdapter(myListData2, MemberTimeTable.this);

        myListData.add(new MemberTimetableModel("12", "Tue", "1", "9:00 AM", "1:00 PM", "January"));
        myListData.add(new MemberTimetableModel("12", "Tue", "1", "9:00 AM", "1:00 PM", "February"));
        myListData.add(new MemberTimetableModel("12", "Tue", "1", "9:00 AM", "1:00 PM", "March"));
        myListData.add(new MemberTimetableModel("12", "Tue", "1", "9:00 AM", "1:00 PM", "April"));
        myListData.add(new MemberTimetableModel("12", "Tue", "1", "9:00 AM", "1:00 PM", "May"));
        myListData.add(new MemberTimetableModel("12", "Tue", "1", "9:00 AM", "1:00 PM", "June"));
        myListData.add(new MemberTimetableModel("12", "Tue", "1", "9:00 AM", "1:00 PM", "July"));
        myListData.add(new MemberTimetableModel("12", "Tue", "1", "9:00 AM", "1:00 PM", "August"));
        myListData.add(new MemberTimetableModel("12", "Tue", "1", "9:00 AM", "1:00 PM", "September"));
        myListData.add(new MemberTimetableModel("12", "Tue", "1", "9:00 AM", "1:00 PM", "October"));
        myListData.add(new MemberTimetableModel("12", "Tue", "31", "9:00 AM", "1:00 PM", "October"));
        myListData.add(new MemberTimetableModel("12", "Tue", "1", "9:00 AM", "1:00 PM", "November"));
        myListData.add(new MemberTimetableModel("12", "Tue", "1", "9:00 AM", "1:00 PM", "December"));

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MemberTimeTable.this));
        recyclerView.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Week 1"));
        tabLayout.addTab(tabLayout.newTab().setText("Week 2"));
        tabLayout.addTab(tabLayout.newTab().setText("Week 3"));
        tabLayout.addTab(tabLayout.newTab().setText("Week 4"));
        tabLayout.addTab(tabLayout.newTab().setText("Week 5"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                switch (tab.getPosition()+1) {
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                        updateCardTab(tab.getPosition());
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        dateView = findViewById(R.id.textViewToolbar);
        dateView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                updateLabel(tabLayout.getSelectedTabPosition());
            }
        });

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);
                updateLabel(tabLayout.getSelectedTabPosition());
            }
        };

        dateView = findViewById(R.id.textViewToolbar);
        dateView.setText(Month.of(myCalendar.MONTH + 1).name().toUpperCase() + ", " + myCalendar.get(Calendar.YEAR));

        updateCardTab(tabLayout.getSelectedTabPosition());

        dateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(MemberTimeTable.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        back = findViewById(R.id.backButtonToolbar);
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                finish();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void updateLabel(int tabposition) {
        dateView.setText(dateFormat.format(myCalendar.getTime()).toUpperCase());
        updateCardTab(tabposition);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void updateCardTab(int tabPosition) {
        String selectedMonth = dateView.getText().toString().toUpperCase();
        String[] arrOfStr = selectedMonth.split(", ", 2);

        myListData2.clear();
        for (MemberTimetableModel x : myListData) {
            calendar.set(2022, Month.valueOf(x.month.toUpperCase()).getValue()-1, Integer.valueOf(x.date));
            int weekOfYear = calendar.get(Calendar.WEEK_OF_MONTH) - 1;

            if (weekOfYear == 5) {
                weekOfYear = 4;
            }
            if (x.month.toUpperCase().equals(arrOfStr[0])) {
                if (weekOfYear == tabPosition) {
                    myListData2.add(new MemberTimetableModel(x.uid, x.day, x.date, x.from, x.to, x.month));
                }
            }
            adapter.notifyDataSetChanged();
        }
    }
}