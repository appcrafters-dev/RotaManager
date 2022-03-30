package com.deeppatel.rotamanager.admin.Scheduler;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.deeppatel.rotamanager.R;
import com.deeppatel.rotamanager.helpers.Utils;
import com.deeppatel.rotamanager.models.TimeEntry;
import com.deeppatel.rotamanager.models.TimeEntryUser;
import com.deeppatel.rotamanager.repositories.TimeEntryRepository.FirebaseTimeEntryRepository;
import com.deeppatel.rotamanager.repositories.TimeEntryRepository.TimeEntryRepository;
import com.deeppatel.rotamanager.repositories.UserRepository.FirebaseUserRepository;
import com.deeppatel.rotamanager.repositories.UserRepository.UserRepository;
import com.pchmn.materialchips.ChipsInput;

import org.joda.time.DateTime;

import java.util.List;
import java.util.stream.Collectors;

public class NewTimeEntry extends AppCompatActivity {
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
        dateEditText = findViewById(R.id.et_date);
        fromTimeEditText = findViewById(R.id.ed_from_time);
        toTimeEditText = findViewById(R.id.ed_to_time);

        Intent intent = getIntent();
        String dateString = intent.getStringExtra("date");
        date = dateString != null ? DateTime.parse(dateString) : DateTime.now();
        dateEditText.setText(Utils.dateTimeToDateString(date));

        backButton.setOnClickListener(view -> finish());

        dateEditText.setOnClickListener(v -> new DatePickerDialog(NewTimeEntry.this, (view, year, month, day) -> {
            date = new DateTime(year, month, day, 0, 0);
            dateEditText.setText(Utils.dateTimeToDateString(date));
        }, date.getYear(), date.getMonthOfYear() - 1, date.getDayOfMonth()).show());

        fromTimeEditText.setOnClickListener(view -> {
            fromTime = new DateTime(date);
            TimePickerDialog fromTimePicker = new TimePickerDialog(NewTimeEntry.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    fromTime = new DateTime(fromTime).withTime(selectedHour, selectedMinute, 0, 0);
                    fromTimeEditText.setText(Utils.dateTimeToString(fromTime, Utils.TIME_FORMAT));
                }
            }, fromTime.getHourOfDay(), fromTime.getMinuteOfHour(), false);
            fromTimePicker.setTitle("Select Time");
            fromTimePicker.show();
        });

        toTimeEditText.setOnClickListener(view -> {
            toTime = new DateTime(date);
            TimePickerDialog toTimePicker = new TimePickerDialog(NewTimeEntry.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    toTime = new DateTime(toTime).withTime(selectedHour, selectedMinute, 0, 0);
                    toTimeEditText.setText(Utils.dateTimeToString(toTime, Utils.TIME_FORMAT));
                }
            }, toTime.getHourOfDay(), toTime.getMinuteOfHour(), false);
            toTimePicker.setTitle("Select Time");
            toTimePicker.show();
        });

        submitButton.setOnClickListener(view -> {
            @SuppressWarnings("unchecked")
            List<TimeEntryUser> selectedMembers = (List<TimeEntryUser>) selectMembersView.getSelectedChipList();
            if(selectedMembers.size() < 1) {
                Utils.showToastMessage(NewTimeEntry.this, "At least one member should be selected");
                return;
            }
            List<TimeEntry> timeEntries = selectedMembers
                    .stream().map(member -> new TimeEntry(null, member, date, fromTime, toTime))
                    .collect(Collectors.toList());

            timeEntryRepository.addNewTimeEntries(timeEntries, result -> {
                if (result.getErrorMessage() != null) {
                    Utils.showToastMessage(NewTimeEntry.this, result.getErrorMessage());
                    return;
                }
                Utils.showToastMessage(NewTimeEntry.this, String.format("Added %s new Time Entries.", timeEntries.size()));
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
                Utils.showToastMessage(NewTimeEntry.this, result.getErrorMessage());
            } else if (result.getResult() != null) {
                List<TimeEntryUser> memberChips = result.getResult().stream().map(TimeEntryUser::fromUser).collect(Collectors.toList());
                selectMembersView.setFilterableList(memberChips);
                selectMembersView.requestLayout();
            }
        });
    }
}
