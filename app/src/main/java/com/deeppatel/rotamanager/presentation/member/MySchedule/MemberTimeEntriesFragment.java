package com.deeppatel.rotamanager.presentation.member.MySchedule;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deeppatel.rotamanager.R;
import com.deeppatel.rotamanager.helpers.Utils;
import com.deeppatel.rotamanager.models.TimeEntry;
import com.deeppatel.rotamanager.models.User;
import com.deeppatel.rotamanager.repositories.AuthRepository.AuthRepository;
import com.deeppatel.rotamanager.repositories.AuthRepository.FirebaseAuthRepository;
import com.deeppatel.rotamanager.repositories.TimeEntryRepository.FirebaseTimeEntryRepository;
import com.deeppatel.rotamanager.repositories.TimeEntryRepository.TimeEntryRepository;
import com.google.android.material.tabs.TabLayout;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public class MemberTimeEntriesFragment extends Fragment {
    private final List<TimeEntry> timeEntries = new ArrayList<>();
    private List<TimeEntry> allTimeEntries = new ArrayList<>();
    private FragmentActivity activity;
    private RecyclerView timeEntriesRecyclerView;
    private MemberTimeEntriesAdapter timeEntriesAdapter;
    private TabLayout tabLayout;
    private TextView selectMonthTextView;

    private DateTime selectedMonth;
    private int selectedWeek;
    private User user;
    private boolean isAdminView = false;

    private AuthRepository authRepository;
    private TimeEntryRepository timeEntryRepository;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_member_schedule, container, false);

        Bundle arguments = getArguments();
        if (arguments != null) {
            user = arguments.getParcelable("user");
            if (user != null) {
                isAdminView = true;
            }
        }

        timeEntriesRecyclerView = view.findViewById(R.id.rv_time_entries);
        selectMonthTextView = view.findViewById(R.id.textViewToolbar);
        selectMonthTextView.setOnClickListener(v -> new DatePickerDialog(
                getContext(),
                (view1, year, month, day) -> {
                    selectedMonth = new DateTime(year, month + 1, day, 0, 0);
                    updateWeekView();
                },
                selectedMonth.getYear(),
                selectedMonth.getMonthOfYear() - 1,
                selectedMonth.getDayOfMonth()
        ).show());

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
        selectMonthTextView.setText(Utils.dateTimeToMonthString(selectedMonth));
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
        timeEntriesAdapter = new MemberTimeEntriesAdapter(timeEntries, activity, isAdminView);

        timeEntriesRecyclerView.setHasFixedSize(true);
        timeEntriesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        timeEntriesRecyclerView.setAdapter(timeEntriesAdapter);
    }

    void fetchTimeEntries() {
        String uid = null;
        if (!isAdminView) {
            uid = authRepository.getCurrentUserId();
        } else if (user.getUid() != null) {
            uid = user.getUid();
        }

        if (uid == null) {
            Utils.showToastMessage(activity, "User is required to fetch Time Entries");
            return;
        }

        timeEntryRepository.getTimeEntriesForUser(uid, result -> {
            if (result.getErrorMessage() != null) {
                Utils.showToastMessage(activity, result.getErrorMessage());
                return;
            }
            allTimeEntries = result.getResult();
            updateWeekView();
            TabLayout.Tab tab = tabLayout.getTabAt(Utils.getWeekOfMonth(selectedMonth) - 1);
            if (tab != null) tab.select();
        });

    }
}