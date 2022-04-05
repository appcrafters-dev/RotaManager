package com.deeppatel.rotamanager.presentation.member.TimeChangeRequest;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.deeppatel.rotamanager.LoginActivity;
import com.deeppatel.rotamanager.R;
import com.deeppatel.rotamanager.helpers.Navigate;
import com.deeppatel.rotamanager.helpers.Utils;
import com.deeppatel.rotamanager.helpers.enums.TimeChangeRequestStatus;
import com.deeppatel.rotamanager.models.TimeChangeRequest;
import com.deeppatel.rotamanager.models.TimeEntry;
import com.deeppatel.rotamanager.repositories.AuthRepository.AuthRepository;
import com.deeppatel.rotamanager.repositories.AuthRepository.FirebaseAuthRepository;
import com.deeppatel.rotamanager.repositories.TimeChangeRequestRepository.FirebaseTimeChangeRequestRepository;
import com.deeppatel.rotamanager.repositories.TimeChangeRequestRepository.TimeChangeRequestRepository;
import com.deeppatel.rotamanager.repositories.TimeEntryRepository.FirebaseTimeEntryRepository;
import com.deeppatel.rotamanager.repositories.TimeEntryRepository.TimeEntryRepository;

import org.joda.time.DateTime;

public class TimeChangeRequestActivity extends AppCompatActivity {
    TimeEntry timeEntry;
    TimeChangeRequest timeChangeRequest;
    private DateTime fromTime;
    private DateTime toTime;
    private EditText fromTimeEditText, toTimeEditText, reasonEditText;
    private Button submitButton;
    private Button rejectRequestButton;
    private Button approveRequestButton;

    private AuthRepository authRepository;
    private TimeChangeRequestRepository timeChangeRequestRepository;
    private TimeEntryRepository timeEntryRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_time_change_request);

        authRepository = FirebaseAuthRepository.getInstance();
        if (authRepository.getCurrentUser() == null) {
            // if user does not exists -> redirect to login page
            Navigate.replace(TimeChangeRequestActivity.this, LoginActivity.class);
            return;
        }

        TextView dateTextVIew = findViewById(R.id.tv_date);
        TextView timeTextView = findViewById(R.id.tv_time);
        fromTimeEditText = findViewById(R.id.ed_from_time);
        toTimeEditText = findViewById(R.id.ed_to_time);
        reasonEditText = findViewById(R.id.et_reason);
        ImageView backButton = findViewById(R.id.iv_back_button);
        submitButton = findViewById(R.id.btn_submit);
        approveRequestButton = findViewById(R.id.btn_request_approve);
        rejectRequestButton = findViewById(R.id.btn_request_reject);

        timeChangeRequestRepository = FirebaseTimeChangeRequestRepository.getInstance();
        timeEntryRepository = FirebaseTimeEntryRepository.getInstance();

        Intent intent = getIntent();
        timeEntry = intent.getParcelableExtra("time_entry");
        timeChangeRequest = intent.getParcelableExtra("time_change_request");
        if (timeChangeRequest != null) {
            timeEntry = timeChangeRequest.getTimeEntry();
            setupAdminView();
        } else {
            setupMemberView();
        }

        DateTime date = timeEntry.getDate();
        dateTextVIew.setText(Utils.dateTimeToString(date, "e MMMM, yyyy"));
        timeTextView.setText(String.format("%S to %S", timeEntry.getFromString(), timeEntry.getToString()));
        fromTimeEditText.setText(timeEntry.getFromString());
        toTimeEditText.setText(timeEntry.getToString());

        backButton.setOnClickListener(view -> finish());
    }

    private void setupAdminView() {
        reasonEditText.setText(timeChangeRequest.getReason());


        submitButton.setVisibility(View.INVISIBLE);
        approveRequestButton.setVisibility(View.VISIBLE);
        rejectRequestButton.setVisibility(View.VISIBLE);

        fromTimeEditText.setInputType(InputType.TYPE_NULL);
        toTimeEditText.setInputType(InputType.TYPE_NULL);
        reasonEditText.setInputType(InputType.TYPE_NULL);

        approveRequestButton.setOnClickListener(view -> {
            timeChangeRequestRepository.approveTimeChangeRequest(timeChangeRequest.getId(), result -> {
                if (result.getErrorMessage() != null) {
                    Utils.showToastMessage(TimeChangeRequestActivity.this, result.getErrorMessage());
                    return;
                }

                timeEntryRepository.updateTimeEntry(timeChangeRequest, result1 -> {
                    if (result1.getErrorMessage() != null) {
                        Utils.showToastMessage(TimeChangeRequestActivity.this, result1.getErrorMessage());
                        return;
                    }

                    Utils.showToastMessage(TimeChangeRequestActivity.this, String.format("Updated Time Entry for %s",
                            timeChangeRequest.getTimeEntry().getUser().getName()));
                    finish();
                });
            });
        });

        rejectRequestButton.setOnClickListener(view -> {
            timeChangeRequestRepository.rejectTimeChangeRequest(timeChangeRequest.getId(), result -> {
                if (result.getErrorMessage() != null) {
                    Utils.showToastMessage(TimeChangeRequestActivity.this, result.getErrorMessage());
                } else {
                    Utils.showToastMessage(TimeChangeRequestActivity.this, "Time Change Request is rejected");
                    finish();
                }
            });
        });
    }

    private void setupMemberView() {
        fromTime = timeEntry.getFrom();
        toTime = timeEntry.getTo();

        fromTimeEditText.setOnClickListener(v -> {
            fromTime = new DateTime(timeEntry.getFrom());
            TimePickerDialog fromTimePicker = new TimePickerDialog(
                    TimeChangeRequestActivity.this,
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
        toTimeEditText.setOnClickListener(v -> {
            TimePickerDialog toTimePicker = new TimePickerDialog(
                    TimeChangeRequestActivity.this,
                    (timePicker, selectedHour, selectedMinute) -> {
                        toTime = new DateTime(toTime).withTime(selectedHour, selectedMinute, 0, 0);
                        toTimeEditText.setText(Utils.dateTimeToString(toTime, Utils.TIME_FORMAT));
                    },
                    toTime.getHourOfDay(),
                    toTime.getMinuteOfHour(),
                    false
            );
            toTimePicker.setTitle("Select Time");
            toTimePicker.show();
        });

        submitButton.setOnClickListener(view -> {
            TimeChangeRequest timeChangeRequest = new TimeChangeRequest(null, TimeChangeRequestStatus.PENDING,
                    fromTime, toTime, reasonEditText.getText().toString(), timeEntry);
            timeChangeRequestRepository.createTimeChangeRequest(timeChangeRequest, result -> {
                if (result.getErrorMessage() != null) {
                    Utils.showToastMessage(TimeChangeRequestActivity.this, result.getErrorMessage());
                    return;
                }
                Utils.showToastMessage(TimeChangeRequestActivity.this, "New Time Change Request created " +
                        "successfully.");
                finish();
            });
        });
    }
}