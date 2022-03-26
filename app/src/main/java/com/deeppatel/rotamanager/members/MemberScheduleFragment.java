package com.deeppatel.rotamanager.members;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MemberScheduleFragment extends Fragment {
    static FragmentManager currentActivityFragment;

    static CardView card;
    final Calendar myCalendar= Calendar.getInstance();
    private TextView dateView;

    public MemberScheduleFragment() {
    }

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


        MemberTimetableModel[] myListData = new MemberTimetableModel[]{
                new MemberTimetableModel("12", "Tue", "12", "9:00 AM", "1:00 PM", "March"),
                new MemberTimetableModel("12", "Tue", "12", "9:00 AM", "1:00 PM", "March"),
                new MemberTimetableModel("12", "Tue", "12", "9:00 AM", "1:00 PM", "March"),
                new MemberTimetableModel("12", "Tue", "12", "9:00 AM", "1:00 PM", "March"),
                new MemberTimetableModel("12", "Tue", "12", "9:00 AM", "1:00 PM", "March"),
                new MemberTimetableModel("12", "Tue", "12", "9:00 AM", "1:00 PM", "March"),
                new MemberTimetableModel("12", "Tue", "12", "9:00 AM", "1:00 PM", "March"),
                new MemberTimetableModel("12", "Tue", "12", "9:00 AM", "1:00 PM", "March"),
                new MemberTimetableModel("12", "Tue", "12", "9:00 AM", "1:00 PM", "March")
        };

        MemberTimetableAdapter adapter = new MemberTimetableAdapter(myListData, getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

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
        String myFormat = "MMMM,yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        dateView.setText(dateFormat.format(myCalendar.getTime()));
    }
}