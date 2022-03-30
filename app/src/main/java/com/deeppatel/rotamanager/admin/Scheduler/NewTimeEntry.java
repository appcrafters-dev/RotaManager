package com.deeppatel.rotamanager.admin.Scheduler;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.deeppatel.rotamanager.R;
import com.deeppatel.rotamanager.helpers.Utils;
import com.deeppatel.rotamanager.models.RepositoryResult;
import com.deeppatel.rotamanager.models.TimeEntry;
import com.deeppatel.rotamanager.models.TimeEntryUser;
import com.deeppatel.rotamanager.models.User;
import com.deeppatel.rotamanager.repositories.OnRepositoryTaskCompleteListener;
import com.deeppatel.rotamanager.repositories.TimeEntryRepository.FirebaseTimeEntryRepository;
import com.deeppatel.rotamanager.repositories.TimeEntryRepository.TimeEntryRepository;
import com.deeppatel.rotamanager.repositories.UserRepository.FirebaseUserRepository;
import com.deeppatel.rotamanager.repositories.UserRepository.UserRepository;
import com.pchmn.materialchips.ChipsInput;

import org.joda.time.DateTime;

import java.util.List;
import java.util.stream.Collectors;

public class NewTimeEntry extends AppCompatActivity {
    ChipsInput chipsInput;
    ImageView back;
    TextView fromTimeTextView, toTimeTextView, dateTextView;
    Button submit;

    DateTime date;
    DateTime fromTime;
    DateTime toTime;

    UserRepository userRepository;
    TimeEntryRepository timeEntryRepository;


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_time_entry);

        chipsInput = findViewById(R.id.chips_input);
        submit = findViewById(R.id.member_submit);
        back = findViewById(R.id.iv_back_button);
        dateTextView = findViewById(R.id.Date);
        fromTimeTextView = findViewById(R.id.fromTime);
        toTimeTextView = findViewById(R.id.toTime);

        dateTextView.setFocusable(false);
        dateTextView.setClickable(false);

        fromTimeTextView.setClickable(false);
        fromTimeTextView.setFocusable(false);

        toTimeTextView.setClickable(false);
        toTimeTextView.setFocusable(false);


        Intent intent = getIntent();
        String dateString = intent.getStringExtra("date");
        date = dateString != null ? DateTime.parse(dateString) : DateTime.now();
        dateTextView.setText(Utils.dateTimeToDateString(date));

        userRepository = FirebaseUserRepository.getInstance();
        timeEntryRepository = FirebaseTimeEntryRepository.getInstance();
        fetchStaffMembers();

        dateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(NewTimeEntry.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        date = new DateTime(year, month, day, 0, 0);
                        dateTextView.setText(Utils.dateTimeToDateString(date));
                    }
                }, date.getYear(), date.getMonthOfYear() - 1, date.getDayOfMonth()).show();
            }
        });

        fromTimeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fromTime = new DateTime(date);
                TimePickerDialog fromTimePicker = new TimePickerDialog(NewTimeEntry.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        fromTime = new DateTime(fromTime).withTime(selectedHour, selectedMinute, 0, 0);
                        fromTimeTextView.setText(Utils.dateTimeToString(fromTime, Utils.TIME_FORMAT));
                    }
                }, fromTime.getHourOfDay(), fromTime.getMinuteOfHour(), false);
                fromTimePicker.setTitle("Select Time");
                fromTimePicker.show();
            }
        });

        toTimeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toTime = new DateTime(date);
                TimePickerDialog toTimePicker = new TimePickerDialog(NewTimeEntry.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        toTime = new DateTime(toTime).withTime(selectedHour, selectedMinute, 0, 0);
                        toTimeTextView.setText(Utils.dateTimeToString(toTime, Utils.TIME_FORMAT));
                    }
                }, toTime.getHourOfDay(), toTime.getMinuteOfHour(), false);
                toTimePicker.setTitle("Select Time");
                toTimePicker.show();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                @SuppressWarnings("unchecked")
                List<TimeEntryUser> selectedMembers = (List<TimeEntryUser>) chipsInput.getSelectedChipList();
                List<TimeEntry> timeEntries = selectedMembers
                        .stream().map(member -> new TimeEntry(null, member, date, fromTime, toTime))
                        .collect(Collectors.toList());

                timeEntryRepository.addNewTimeEntries(timeEntries, new OnRepositoryTaskCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull RepositoryResult<Void> result) {
                        if (result.getErrorMessage() != null) {
                            Utils.showToastMessage(NewTimeEntry.this, result.getErrorMessage());
                            return;
                        }
                        Utils.showToastMessage(NewTimeEntry.this, String.format("Added %s new Time Entries.", timeEntries.size()));
                        finish();
                    }
                });
            }
        });
    }

    void fetchStaffMembers() {
        userRepository.getStaffMembers(new OnRepositoryTaskCompleteListener<List<User>>() {
            @Override
            public void onComplete(@NonNull RepositoryResult<List<User>> result) {
                if (result.getErrorMessage() != null) {
                    Utils.showToastMessage(NewTimeEntry.this, result.getErrorMessage());
                } else if (result.getResult() != null) {
                    List<TimeEntryUser> memberChips = result.getResult().stream().map(TimeEntryUser::fromUser).collect(Collectors.toList());
                    chipsInput.setFilterableList(memberChips);
                    chipsInput.requestLayout();
                }
            }
        });
    }
}
