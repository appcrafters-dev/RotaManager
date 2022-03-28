package com.deeppatel.rotamanager.admin.ManageStaff;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.deeppatel.rotamanager.R;
import com.deeppatel.rotamanager.helpers.adapters.MemberScheduleAdapter.MemberTimetableAdapter;
import com.deeppatel.rotamanager.models.MemberTimetableModel;
import com.deeppatel.rotamanager.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MemberTimeTable extends AppCompatActivity {
    private ImageView back;
    private TextView memberNameNavbar;
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
        Intent intent = getIntent();
        User user = intent.getExtras().getParcelable("user");
        String Uid = user.getUid();
        String name = user.getName();
        memberNameNavbar = findViewById(R.id.memberNameViewToolbar);
        memberNameNavbar.setText(name);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        List<MemberTimetableModel> Schedule = new ArrayList<MemberTimetableModel>();
        db.collection("users").document(Uid).collection("Schedule").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("SimpleDateFormat")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for (QueryDocumentSnapshot doc : task.getResult()){
                                Timestamp from, to;
                                from = (Timestamp) doc.get("from");
                                to = (Timestamp) doc.get("to");
                                Date x = from.toDate();
                                Date y = to.toDate();
                                Calendar cal = Calendar.getInstance(Locale.US);
                                Calendar cal2 = Calendar.getInstance(Locale.US);
                                cal.setTime(x);
                                cal2.setTime(y);

                                String day = new SimpleDateFormat("EE").format(x);
                                String calenderDate = String.valueOf(cal.get(Calendar.DATE));
                                int calenderFromHour = cal.get(Calendar.HOUR_OF_DAY);
                                int calenderFromMinute = cal.get(Calendar.MINUTE);
                                int calenderToHour = cal2.get(Calendar.HOUR_OF_DAY);
                                int calenderToMinute = cal2.get(Calendar.MINUTE);
                                String calenderMonth = Month.of(cal2.MONTH + 1).name();

                                boolean isPM = (calenderFromHour >= 12);
                                String calenderFrom = String.format("%02d:%02d %s", (calenderFromHour == 12 || calenderFromHour == 0) ? 12 : calenderFromHour % 12, calenderFromMinute, isPM ? "PM" : "AM");
                                boolean isPM2 = (calenderToHour >= 12);
                                String calenderTo = String.format("%02d:%02d %s", (calenderToHour == 12 || calenderToHour == 0) ? 12 : calenderToHour % 12, calenderToMinute, isPM2 ? "PM" : "AM");

                                myListData.add(new MemberTimetableModel(Uid, doc.getId(),day,calenderDate,calenderFrom,calenderTo ,calenderMonth));
                            }
                        }
                    }
                });
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        adapter = new MemberTimetableAdapter(myListData2, MemberTimeTable.this);

//        myListData.add(new MemberTimetableModel("12", "Tue", "1", "9:00 AM", "1:00 PM", "January"));

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
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                    myListData2.add(new MemberTimetableModel(x.uid, x.getSchedid(), x.day, x.date, x.from, x.to, x.month));
                }
            }
            adapter.notifyDataSetChanged();
        }
    }
}