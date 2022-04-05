package com.deeppatel.rotamanager.presentation.admin.Scheduler;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.deeppatel.rotamanager.R;
import com.deeppatel.rotamanager.helpers.Utils;
import com.deeppatel.rotamanager.models.ChildUser;
import com.deeppatel.rotamanager.models.TimeEntry;
import com.deeppatel.rotamanager.repositories.TimeEntryRepository.FirebaseTimeEntryRepository;
import com.deeppatel.rotamanager.repositories.TimeEntryRepository.TimeEntryRepository;
import com.deeppatel.rotamanager.repositories.UserRepository.FirebaseUserRepository;
import com.deeppatel.rotamanager.repositories.UserRepository.UserRepository;
import com.pchmn.materialchips.ChipsInput;

import org.joda.time.DateTime;

import java.util.List;
import java.util.stream.Collectors;

public class NewTimeEntryActivity extends AppCompatActivity {
    private ChipsInput selectMembersView;
    private EditText fromTimeEditText, toTimeEditText, dateEditText;

    private DateTime date;
    private DateTime fromTime;
    private DateTime toTime;

    private UserRepository userRepository;
    private TimeEntryRepository timeEntryRepository;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_time_entry);

        selectMembersView = findViewById(R.id.ci_select_members);
        Button submitButton = findViewById(R.id.btn_submit);
        ImageView backButton = findViewById(R.id.iv_back_button);
        dateEditText = findViewById(R.id.tv_date);
        fromTimeEditText = findViewById(R.id.ed_from_time);
        toTimeEditText = findViewById(R.id.ed_to_time);

        Intent intent = getIntent();
        String dateString = intent.getStringExtra("date");
        date = dateString != null ? DateTime.parse(dateString) : DateTime.now();
        dateEditText.setText(Utils.dateTimeToDateString(date));

        backButton.setOnClickListener(view -> finish());

        dateEditText.setOnClickListener(v -> new DatePickerDialog(NewTimeEntryActivity.this,
                (view, year, month, day) -> {
                    date = new DateTime(year, month, day, 0, 0);
                    dateEditText.setText(Utils.dateTimeToDateString(date));
                }, date.getYear(), date.getMonthOfYear() - 1, date.getDayOfMonth()).show());

        fromTimeEditText.setOnClickListener(view -> {
            fromTime = new DateTime(date);
            TimePickerDialog fromTimePicker = new TimePickerDialog(
                    NewTimeEntryActivity.this,
                    (timePicker, selectedHour, selectedMinute) -> {
                        fromTime = new DateTime(fromTime).withTime(selectedHour, selectedMinute, 0, 0);
                        fromTimeEditText.setText(Utils.dateTimeToString(fromTime, Utils.TIME_FORMAT));
                    },
                    fromTime.getHourOfDay(),
                    fromTime.getMinuteOfHour(),
                    false
            );
            fromTimePicker.setTitle("Select Time");
            fromTimePicker.show();
        });

        toTimeEditText.setOnClickListener(view -> {
            toTime = new DateTime(date);
            TimePickerDialog toTimePicker = new TimePickerDialog(
                    NewTimeEntryActivity.this,
                    (timePicker, selectedHour, selectedMinute) -> {
                        toTime = new DateTime(toTime).withTime(selectedHour, selectedMinute, 0, 0);
                        toTimeEditText.setText(Utils.dateTimeToString(toTime, Utils.TIME_FORMAT));
                    },
                    toTime.getHourOfDay()
                    , toTime.getMinuteOfHour(),
                    false);
            toTimePicker.setTitle("Select Time");
            toTimePicker.show();
        });

        submitButton.setOnClickListener(view -> {
            @SuppressWarnings("unchecked")
            List<ChildUser> selectedMembers = (List<ChildUser>) selectMembersView.getSelectedChipList();
            if (selectedMembers.size() < 1) {
                Utils.showToastMessage(NewTimeEntryActivity.this, "At least one member should be selected");
                return;
            }
            List<TimeEntry> timeEntries = selectedMembers
                    .stream().map(member -> new TimeEntry(null, member, date, fromTime, toTime))
                    .collect(Collectors.toList());

            timeEntryRepository.addNewTimeEntries(timeEntries, result -> {
                if (result.getErrorMessage() != null) {
                    Utils.showToastMessage(NewTimeEntryActivity.this, result.getErrorMessage());
                    return;
                }
                Utils.showToastMessage(NewTimeEntryActivity.this, String.format("Added %s new Time Entries.",
                        timeEntries.size()));
                finish();
            });
        });

        userRepository = FirebaseUserRepository.getInstance();
        timeEntryRepository = FirebaseTimeEntryRepository.getInstance();

        fetchStaffMembers();
    }

    void fetchStaffMembers() {
        userRepository.getStaffMembers(result -> {
            if (result.getErrorMessage() != null) {
                Utils.showToastMessage(NewTimeEntryActivity.this, result.getErrorMessage());
            } else if (result.getResult() != null) {
                List<ChildUser> memberChips =
                        result.getResult().stream().map(ChildUser::fromUser).collect(Collectors.toList());
                selectMembersView.setFilterableList(memberChips);
                selectMembersView.requestLayout();
            }
        });
    }
}
