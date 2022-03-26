package com.deeppatel.rotamanager.members;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;

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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MemberScheduleFragment extends Fragment {
    static FragmentManager currentActivityFragment;

    static CardView card;
    final Calendar myCalendar= Calendar.getInstance();
    private TextView dateView;

    public MemberScheduleFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

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

        List<MemberTimetableModel> myListData = new ArrayList<>();

        MemberTimetableAdapter adapter = new MemberTimetableAdapter(myListData, getActivity());
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
                        myListData.clear();
                        myListData.add(new MemberTimetableModel("12", "Tue", "1", "9:00 AM", "1:00 PM", "March"));
                        adapter.notifyDataSetChanged();

                        break;
                    case 1:
                        Log.e("!!!!!!!!","1");
                        myListData.clear();
                        myListData.add(new MemberTimetableModel("12", "Tue", "12", "9:00 AM", "1:00 PM", "March"));
                        adapter.notifyDataSetChanged();
                        break;

                    case 2:
                        Log.e("!!!!!!!!","2");
                        myListData.clear();
                        myListData.add(new MemberTimetableModel("12", "Tue", "23", "9:00 AM", "1:00 PM", "March"));
                        adapter.notifyDataSetChanged();
                        break;

                    case 3:
                        Log.e("!!!!!!!!","3");
                        myListData.clear();
                        adapter.notifyDataSetChanged();
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

        dateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(),date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        return rootView;
    }


    private void updateLabel() {
        String myFormat = "MMMM, yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        dateView.setText(dateFormat.format(myCalendar.getTime()));
    }
}