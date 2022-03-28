package com.deeppatel.rotamanager.admin.Scheduler;

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
import com.deeppatel.rotamanager.helpers.adapters.AdminScheduleAdapter.TimeEntryListAdapter;
import com.deeppatel.rotamanager.models.TimeEntryListModel;
import com.deeppatel.rotamanager.models.RepositoryResult;
import com.deeppatel.rotamanager.models.User;
import com.deeppatel.rotamanager.repositories.OnRepositoryTaskCompleteListener;
import com.deeppatel.rotamanager.repositories.UserRepository.FirebaseUserRepository;
import com.deeppatel.rotamanager.repositories.UserRepository.UserRepository;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AdminScheduler extends AppCompatActivity {
    CalendarView calendar;
    private ImageView back;
    private FloatingActionButton mFab;
    TimeEntryListAdapter adapter;
    RecyclerView recyclerView;
    List<TimeEntryListModel> myListData2;
    List<TimeEntryListModel> allTimeEntries;

    UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_scheduler);

        recyclerView = findViewById(R.id.recyclerView);
        back = findViewById(R.id.backButtonToolbar);
        mFab = findViewById(R.id.scheduler_fab);
        calendar = findViewById(R.id.calendar);

        allTimeEntries = new ArrayList<>();
        myListData2 = new ArrayList<>();
        userRepository = FirebaseUserRepository.getInstance();

        getAllSchedule();


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

        allTimeEntries.add(new TimeEntryListModel("12", "Tue", "12", "John Doe", "9:00 AM to 1:00 PM", "March"));
        allTimeEntries.add(new TimeEntryListModel("12", "Tue", "12", "John Doe", "9:00 AM to 12:00 PM", "March"));
        allTimeEntries.add(new TimeEntryListModel("12", "Tue", "13", "John Doe", "9:00 AM to 14:00 PM", "March"));
        allTimeEntries.add(new TimeEntryListModel("12", "Tue", "14", "John Doe", "9:00 AM to 15:00 PM", "March"));
        allTimeEntries.add(new TimeEntryListModel("12", "Tue", "15", "John Doe", "9:00 AM to 15:00 PM", "March"));

//        adapter = new TimeEntryListAdapter(myListData2, AdminScheduler.this);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(AdminScheduler.this));
//        recyclerView.setAdapter(adapter);

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view,
                                            int year,
                                            int month,
                                            int dayOfMonth) {
                updateTimeEntries(month, dayOfMonth);
            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    void updateTimeEntries(int month, int dayOfMonth){
        String Date = dayOfMonth + "-" + Month.of(month + 1).name().toUpperCase();

        myListData2.clear();
        Log.d("onSelectedDayChange", String.valueOf(allTimeEntries.size()));
        for (TimeEntryListModel x : allTimeEntries) {
            String fu = x.getDate() + "-" + x.getMonth().toUpperCase();
            Log.d("onSelectedDayChange: fu", fu);

            if (fu.equals(Date)) {
                myListData2.add(new TimeEntryListModel(x.uid,x.day, x.date, x.name, x.time, x.month));
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void getAllSchedule(){
        userRepository.getUser("dvdsvsdv", new OnRepositoryTaskCompleteListener<User>() {
            @Override
            public void onComplete(@NonNull RepositoryResult<User> result) {
                result.getErrorMessage();
            }
        });

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collectionGroup("Schedule").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot doc: queryDocumentSnapshots){
                    Log.i("sched", doc.getId());
                    Log.i("sched", doc.toString());
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


                    allTimeEntries.add(new TimeEntryListModel("2AMnInXgtgMeqWKiTTuf1spgkw13", day, calenderDate, "asd",calenderFrom + calenderTo, calenderMonth));

                    Log.i("doc", calenderDate + " " + day + " " + "asd" + " " + calenderFrom + " " + calenderTo + " " + calenderMonth + " ");
                }

                adapter = new TimeEntryListAdapter(myListData2, AdminScheduler.this);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(AdminScheduler.this));
                recyclerView.setAdapter(adapter);
                Date today = new Date();
                Calendar cal = Calendar.getInstance(Locale.US);
                cal.setTime(today);
                updateTimeEntries(cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
            }
        });
//
//        db.collection("users").get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        for (QueryDocumentSnapshot doc: task.getResult()){
//                            db.collection("users").document(doc.getId()).collection("Schedule").get()
//                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                                        @RequiresApi(api = Build.VERSION_CODES.O)
//                                        @Override
//                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                            for (QueryDocumentSnapshot subdoc: task.getResult()) {
//                                                if(!task.getResult().isEmpty()) {
//                                                    Timestamp from, to;
//                                                    from = (Timestamp) subdoc.get("from");
//                                                    to = (Timestamp) subdoc.get("to");
//                                                    Date x = from.toDate();
//                                                    Date y = to.toDate();
//                                                    Calendar cal = Calendar.getInstance(Locale.US);
//                                                    Calendar cal2 = Calendar.getInstance(Locale.US);
//                                                    cal.setTime(x);
//                                                    cal2.setTime(y);
//
//                                                    String day = new SimpleDateFormat("EE").format(x);
//                                                    String calenderDate = String.valueOf(cal.get(Calendar.DATE));
//                                                    int calenderFromHour = cal.get(Calendar.HOUR_OF_DAY);
//                                                    int calenderFromMinute = cal.get(Calendar.MINUTE);
//                                                    int calenderToHour = cal2.get(Calendar.HOUR_OF_DAY);
//                                                    int calenderToMinute = cal2.get(Calendar.MINUTE);
//                                                    String calenderMonth = Month.of(cal2.MONTH + 1).name();
//
//                                                    boolean isPM = (calenderFromHour >= 12);
//                                                    String calenderFrom = String.format("%02d:%02d %s", (calenderFromHour == 12 || calenderFromHour == 0) ? 12 : calenderFromHour % 12, calenderFromMinute, isPM ? "PM" : "AM");
//                                                    boolean isPM2 = (calenderToHour >= 12);
//                                                    String calenderTo = String.format("%02d:%02d %s", (calenderToHour == 12 || calenderToHour == 0) ? 12 : calenderToHour % 12, calenderToMinute, isPM2 ? "PM" : "AM");
//
//                                                    allTimeEntries.add(new TimeEntryListModel(doc.getId().toString(), day, calenderDate, doc.get("name").toString(), calenderFrom + calenderTo, calenderMonth));
//
//                                                    Log.i("doc", calenderDate + " " + day + " " + doc.get("name").toString() + " " + calenderFrom + " " + calenderTo + " " + calenderMonth + " ");
//                                                }
//
//                                            }
//                                        }
//                                    });
//                        }
//                    }
//                });
    }
}