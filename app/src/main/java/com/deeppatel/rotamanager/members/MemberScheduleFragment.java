package com.deeppatel.rotamanager.members;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.deeppatel.rotamanager.R;
import com.deeppatel.rotamanager.admin.EditStaffMember;
import com.deeppatel.rotamanager.helpers.MemberTimeChangeRequestAdapter;
import com.deeppatel.rotamanager.helpers.MemberTimeChangeRequestModel;
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

public class MemberScheduleFragment extends Fragment {
    static FragmentManager currentActivityFragment;
    String myFormat = "MMMM, yyyy",monthFormatt = "MMMM",dayFormatt= "DD",yearFormatt= "yyyy",nummonFormatt= "mm";
    SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US),monthFormat = new SimpleDateFormat(monthFormatt, Locale.US),week_dayFormat = new SimpleDateFormat(dayFormatt, Locale.US),week_monFormat = new SimpleDateFormat(nummonFormatt, Locale.US),week_yearFormat = new SimpleDateFormat(yearFormatt, Locale.US);
    static CardView card;
    final Calendar myCalendar= Calendar.getInstance();
    private TextView dateView;

    List<MemberTimetableModel> myListData = new ArrayList<>();
    List<MemberTimetableModel> myListData2 = new ArrayList<>();
    MemberTimetableAdapter adapter = new MemberTimetableAdapter(myListData2, getActivity());


    public MemberScheduleFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_member_schedule, container, false);
        currentActivityFragment = getActivity().getSupportFragmentManager();
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);

        dateView = rootView.findViewById(R.id.textViewToolbar);
        dateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateLabel();
            }
        });

        myListData.add(new MemberTimetableModel("12", "Tue", "1", "9:00 AM", "1:00 PM", "March"));
        myListData.add(new MemberTimetableModel("12", "Tue", "3", "9:00 AM", "1:00 PM", "March"));
        myListData.add(new MemberTimetableModel("12", "Tue", "5", "9:00 AM", "1:00 PM", "March"));
        myListData.add(new MemberTimetableModel("12", "Tue", "7", "9:00 AM", "1:00 PM", "April"));
        myListData.add(new MemberTimetableModel("12", "Tue", "9", "9:00 AM", "1:00 PM", "March"));
        myListData.add(new MemberTimetableModel("12", "Tue", "11", "9:00 AM", "1:00 PM", "March"));
        myListData.add(new MemberTimetableModel("12", "Tue", "13", "9:00 AM", "1:00 PM", "April"));
        myListData.add(new MemberTimetableModel("12", "Tue", "15", "9:00 AM", "1:00 PM", "March"));
        myListData.add(new MemberTimetableModel("12", "Tue", "17", "9:00 AM", "1:00 PM", "March"));
        myListData.add(new MemberTimetableModel("12", "Tue", "19", "9:00 AM", "1:00 PM", "April"));
        myListData.add(new MemberTimetableModel("12", "Tue", "23", "9:00 AM", "1:00 PM", "March"));
        myListData.add(new MemberTimetableModel("12", "Tue", "25", "9:00 AM", "1:00 PM", "March"));
        myListData.add(new MemberTimetableModel("12", "Tue", "17", "9:00 AM", "1:00 PM", "April"));
        myListData.add(new MemberTimetableModel("12", "Tue", "9", "9:00 AM", "1:00 PM", "April"));
        myListData.add(new MemberTimetableModel("12", "Tue", "21", "9:00 AM", "1:00 PM", "April"));
        myListData.add(new MemberTimetableModel("12", "Tue", "26", "9:00 AM", "1:00 PM", "March"));

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Week 1"));
        tabLayout.addTab(tabLayout.newTab().setText("Week 2"));
        tabLayout.addTab(tabLayout.newTab().setText("Week 3"));
        tabLayout.addTab(tabLayout.newTab().setText("Week 4"));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                switch (tab.getPosition()) {
                    case 0:
                        Log.e("!!!!!!!!","0");
                        updateCard();
//                        updateCardTab(tab.getPosition());

                        break;
                    case 1:
                        Log.e("!!!!!!!!","1");
//                        updateCardTab(tab.getPosition());
                        updateCard();

                        break;

                    case 2:
                        Log.e("!!!!!!!!","2");
//                        updateCardTab(tab.getPosition());
                        updateCard();
                        break;

                    case 3:
                        Log.e("!!!!!!!!","3");
//                        updateCardTab(tab.getPosition());
                        updateCard();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel();
            }
        };

        dateView = rootView.findViewById(R.id.textViewToolbar);
        dateView.setText(Month.of(myCalendar.MONTH+1).name().toUpperCase() + ", "+ myCalendar.get(Calendar.YEAR));
        updateCard();

        dateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(),date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        return rootView;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void updateLabel() {
        dateView.setText(dateFormat.format(myCalendar.getTime()));
        updateCard();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void updateCard(){
//        Log.e("@@@@@@@@@@@11111111", dateFormat.format(myCalendar.getTime()));
//        Log.e("@@@@@@@@@@@11111111", monthFormat.format(myCalendar.getTime()));
        String selectedMonth = monthFormat.format(myCalendar.getTime()).toUpperCase();

        myListData2.clear();
        for (MemberTimetableModel x : myListData){
            Log.e("month: ", x.month);
            myCalendar.set(2022,Month.valueOf(x.month.toUpperCase()).getValue(),Integer.valueOf(x.date));
            int weekOfYear = myCalendar.get(Calendar.WEEK_OF_MONTH);
            if (x.month.toUpperCase().equals(selectedMonth)){
                Log.e("@@@@@@@@@@@", selectedMonth +" " +  weekOfYear + " " + " " + x.date + " " + x.month);

                myListData2.add(new MemberTimetableModel(x.uid, x.day,x.date, x.from, x.to,x.month));
            }
        }
        adapter.notifyDataSetChanged();
    }

//    @RequiresApi(api = Build.VERSION_CODES.O)
//    private void updateCardTab(int tabPosition){
//        String selectedMonth = monthFormat.format(myCalendar.getTime()).toUpperCase();
//
//        myListData2.clear();
//        for (MemberTimetableModel x : myListData){
//            myCalendar.set(2022,Month.valueOf(x.month.toUpperCase()).getValue(),Integer.valueOf(x.date));
//            int weekOfYear = myCalendar.get(Calendar.WEEK_OF_MONTH);
//
//
//            if (x.month.toUpperCase().equals(selectedMonth)){
////                if(weekOfYear == tabPosition){
//            Log.e("@@@@@@@@@@@", selectedMonth +" " +  weekOfYear + " "+ tabPosition + " " + x.date + " " + x.month);
//                    myListData2.add(new MemberTimetableModel(x.uid, x.day,x.date, x.from, x.to,x.month));
////                }
//            }
//        }
//        adapter.notifyDataSetChanged();
//    }
}