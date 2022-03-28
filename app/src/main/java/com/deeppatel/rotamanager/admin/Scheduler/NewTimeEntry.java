package com.deeppatel.rotamanager.admin.Scheduler;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.deeppatel.rotamanager.R;
import com.deeppatel.rotamanager.models.ContactChip;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.pchmn.materialchips.ChipsInput;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class NewTimeEntry extends AppCompatActivity {
    Calendar myCalendar = Calendar.getInstance();
    Calendar mcurrentTime = Calendar.getInstance();
    Calendar mcurrentTimeTo = Calendar.getInstance();
    List<ContactChip> contactList = new ArrayList<>();
    List<String> names = new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    List<ContactChip> contactsSelected;
    Timestamp dateTime;
    Timestamp fromTimeStamp;
    Timestamp toTimeStamp;
    private ImageView back;
    private TextView fromTime, toTime, dateView;
    private Button submit;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_time_entry);


        ChipsInput chipsInput = findViewById(R.id.chips_input);
        db.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<QueryDocumentSnapshot> list = new ArrayList<>();

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        list.add(document);
                        contactList.add(new ContactChip(document.getId(), document.get("name").toString(), document.get("email").toString()));
                    }
                    Log.d("FireStore Data", list.toString());
                    Log.e("Chips Loader", contactList.toString());

                    chipsInput.setFilterableList(contactList);
                    chipsInput.requestLayout();
                    contactsSelected = (List<ContactChip>) chipsInput.getSelectedChipList();


                } else {
                    Log.d("FireStore Data", "Error getting documents: ", task.getException());
                }
            }
        });

        dateView = findViewById(R.id.Date);
        dateView.setFocusable(false);
        dateView.setClickable(false);
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);
                dateTime = new Timestamp(myCalendar.getTime());
                updateLabel();
            }
        };
        dateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(NewTimeEntry.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        fromTime = findViewById(R.id.fromTime);
        fromTime.setClickable(false);
        fromTime.setFocusable(false);
        fromTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mcurrentTime.set(myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DATE));
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(NewTimeEntry.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        boolean isPM = (selectedHour >= 12);
                        fromTime.setText(String.format("%02d:%02d %s", (selectedHour == 12 || selectedHour == 0) ? 12 : selectedHour % 12, minute, isPM ? "PM" : "AM"));
                        mcurrentTime.set(Calendar.HOUR_OF_DAY, selectedHour);
                        mcurrentTime.set(Calendar.MINUTE, selectedMinute);
                    }
                }, hour, minute, false);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        toTime = findViewById(R.id.toTime);
        toTime.setClickable(false);
        toTime.setFocusable(false);
        toTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mcurrentTimeTo.set(myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DATE));

                int hour = mcurrentTimeTo.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTimeTo.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                toTimeStamp = new Timestamp(mcurrentTimeTo.getTime());
                mTimePicker = new TimePickerDialog(NewTimeEntry.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        boolean isPM = (selectedHour >= 12);
                        toTime.setText(String.format("%02d:%02d %s", (selectedHour == 12 || selectedHour == 0) ? 12 : selectedHour % 12, minute, isPM ? "PM" : "AM"));
                        mcurrentTimeTo.set(Calendar.HOUR_OF_DAY, selectedHour);
                        mcurrentTimeTo.set(Calendar.MINUTE, selectedMinute);
                    }
                }, hour, minute, false);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        back = findViewById(R.id.backButtonToolbar);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
            }
        });

        submit = findViewById(R.id.member_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < contactsSelected.size(); i++) {
                    names.add(contactsSelected.get(i).getName());
                }
                fromTimeStamp = new Timestamp(mcurrentTime.getTime());
                toTimeStamp = new Timestamp(mcurrentTimeTo.getTime());
                Map<String, Object> schedule = new HashMap<>();
                schedule.put("date", dateTime);
                schedule.put("from", fromTimeStamp);
                schedule.put("to", toTimeStamp);

                db.collection("users").whereIn("name", names)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                Log.i("names", names.toString());
                                if (task.isSuccessful()) {
                                    Log.i("Inside", "Query Snapshot " + task.getResult().size());
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        db.collection("users").document(document.getId()).collection("Schedule").document()
                                                .set(schedule)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        Log.d("FireStore", "DocumentSnapshot successfully written!");
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Log.d("Staff", "Error adding document", e);
                                                    }
                                                });
                                        Log.i("New Entry Names", document.getId() + " => " + document.getData());
                                    }
                                } else {
                                    Log.i("New Entry Names", "Error getting documents: ", task.getException());
                                }

                            }
                        });
                Log.e("AAAAAe!!!!!!!!!!", contactsSelected.get(0).getName());
                Toast.makeText(NewTimeEntry.this, "weeeeeee", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void checkScheduleTime() {
        Log.i("TO DO", "Check if the time slot exists");
    }

    private void updateLabel() {
        String myFormat = "dd/MMMM/yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        dateView.setText(dateFormat.format(myCalendar.getTime()));
    }
}