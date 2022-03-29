package com.deeppatel.rotamanager.member.MySchedule;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deeppatel.rotamanager.R;
import com.deeppatel.rotamanager.helpers.Utils;
import com.deeppatel.rotamanager.helpers.adapters.MemberScheduleAdapter.MemberTimetableAdapter;
import com.deeppatel.rotamanager.models.RepositoryResult;
import com.deeppatel.rotamanager.models.TimeEntry;
import com.deeppatel.rotamanager.repositories.AuthRepository.AuthRepository;
import com.deeppatel.rotamanager.repositories.AuthRepository.FirebaseAuthRepository;
import com.deeppatel.rotamanager.repositories.OnRepositoryTaskCompleteListener;
import com.deeppatel.rotamanager.repositories.TimeEntryRepository.FirebaseTimeEntryRepository;
import com.deeppatel.rotamanager.repositories.TimeEntryRepository.TimeEntryRepository;
import com.google.android.material.tabs.TabLayout;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public class MemberScheduleFragment extends Fragment {
    List<TimeEntry> allTimeEntries = new ArrayList<>();
    List<TimeEntry> timeEntries = new ArrayList<>();

    private DateTime selectedMonth;
    private int selectedWeek;

    private FragmentActivity activity;
    private View view;
    private RecyclerView timeEntriesRecyclerView;
    private MemberTimetableAdapter timeEntriesAdapter;
    private TabLayout tabLayout;
    private TextView monthSelectTextView;

    private AuthRepository authRepository;
    private TimeEntryRepository timeEntryRepository;

    public MemberScheduleFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_member_schedule, container, false);


        timeEntriesRecyclerView = view.findViewById(R.id.recyclerView);
        monthSelectTextView = view.findViewById(R.id.textViewToolbar);
        monthSelectTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(
                        getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int day) {
                                selectedMonth = new DateTime(year, month + 1, day, 0, 0);
                                updateWeekView();
                            }
                        },
                        selectedMonth.getYear(),
                        selectedMonth.getMonthOfYear() - 1,
                        selectedMonth.getDayOfMonth()
                ).show();
            }
        });

        tabLayout = view.findViewById(R.id.tabLayout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                selectedWeek = tab.getPosition() + 1;
                updateTimeEntries();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        selectedMonth = DateTime.now();
        selectedWeek = Utils.getWeekOfMonth(selectedMonth);

        setupTimeEntriesAdapter();

        authRepository = FirebaseAuthRepository.getInstance();
        timeEntryRepository = FirebaseTimeEntryRepository.getInstance();
        fetchTimeEntries();

        return view;
    }

    private void updateWeekView() {
        tabLayout.removeAllTabs();
        int numberOfWeeks = (int) Math.ceil(selectedMonth.dayOfMonth().getMaximumValue() / 7d);
        for (int week = 1; week <= numberOfWeeks; week++) {
            tabLayout.addTab(tabLayout.newTab().setText("Week " + week));
        }
        monthSelectTextView.setText(Utils.dateTimeToMonthString(selectedMonth));
        updateTimeEntries();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void updateTimeEntries() {
        timeEntries.clear();
        for (TimeEntry timeEntry : allTimeEntries) {
            final DateTime date = timeEntry.getDate();
            if (Utils.dateTimeToMonthString(date).equals(Utils.dateTimeToMonthString(selectedMonth)) && Utils.getWeekOfMonth(date) == selectedWeek) {
                timeEntries.add(timeEntry);
            }
        }

        timeEntriesAdapter.notifyDataSetChanged();
    }

    void setupTimeEntriesAdapter() {
        timeEntriesAdapter = new MemberTimetableAdapter(timeEntries, activity);

        timeEntriesRecyclerView.setHasFixedSize(true);
        timeEntriesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        timeEntriesRecyclerView.setAdapter(timeEntriesAdapter);
    }

    void fetchTimeEntries() {
        String uid = authRepository.getCurrentUserId();
        if (uid == null) {
//            TODO: redirect to login screen
            return;
        }

        timeEntryRepository.getTimeEntriesForUser(uid, new OnRepositoryTaskCompleteListener<List<TimeEntry>>() {
            @Override
            public void onComplete(@NonNull RepositoryResult<List<TimeEntry>> result) {
                if (result.getErrorMessage() != null) {
                    Utils.showToastMessage(activity, result.getErrorMessage());
                    return;
                }
                allTimeEntries = result.getResult();
                updateWeekView();
                TabLayout.Tab tab = tabLayout.getTabAt(Utils.getWeekOfMonth(selectedMonth) - 1);
                tab.select();
            }
        });

    }
}