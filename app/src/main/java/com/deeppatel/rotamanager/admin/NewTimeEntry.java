package com.deeppatel.rotamanager.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.deeppatel.rotamanager.R;
import com.deeppatel.rotamanager.helpers.ContactChip;
import com.deeppatel.rotamanager.helpers.RedirectToActivity;
import com.pchmn.materialchips.ChipsInput;
import com.pchmn.materialchips.model.ChipInterface;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class NewTimeEntry extends AppCompatActivity {
    private ImageView back;
    private TextView fromTime,toTime;
    private Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_time_entry);


        ChipsInput chipsInput = (ChipsInput) findViewById(R.id.chips_input);
        List<ContactChip> contactList = new ArrayList<>();
        contactList.add(new ContactChip("AAAAAAAAAAAAA","John Doe","AAAA"));
        contactList.add(new ContactChip("AABB","AABBBBBBBBBBBB","AABB"));
        contactList.add(new ContactChip("AACC","AACC","AACC"));
        contactList.add(new ContactChip("BBBB","BBBB","BBBB"));
        contactList.add(new ContactChip("CCCC","CCCC","CCCC"));
        contactList.add(new ContactChip("DDDD","DDDD","DDDD"));

        chipsInput.setFilterableList(contactList);
        List<ContactChip> contactsSelected = (List<ContactChip>) chipsInput.getSelectedChipList();


        fromTime = findViewById(R.id.fromTime);
        fromTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;

                mTimePicker = new TimePickerDialog(NewTimeEntry.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        fromTime.setText("From : " + selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, false);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        toTime = findViewById(R.id.toTime);
        toTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(NewTimeEntry.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        toTime.setText("To : " + selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, false);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        back = findViewById(R.id.backButtonToolbar);
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                new RedirectToActivity().redirectActivityAfterFinish(NewTimeEntry.this, AdminScheduler.class);
            }
        });

        submit = findViewById(R.id.member_submit);
        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Log.e("AAAAAe!!!!!!!!!!", contactsSelected.toString());
            }
        });
    }

}

