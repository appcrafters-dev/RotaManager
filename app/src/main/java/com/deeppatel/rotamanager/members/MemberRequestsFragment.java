package com.deeppatel.rotamanager.members;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.deeppatel.rotamanager.R;
import com.deeppatel.rotamanager.admin.StaffMemberList;
import com.deeppatel.rotamanager.helpers.MemberTimeChangeRequestAdapter;
import com.deeppatel.rotamanager.helpers.MemberTimeChangeRequestModel;
import com.deeppatel.rotamanager.helpers.StaffMemberDataAdapter;
import com.deeppatel.rotamanager.helpers.StaffMemberDataModel;
import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MemberRequestsFragment extends Fragment {
    static FragmentManager currentActivityFragment;
    private int currentYear;
    private int yearSelected;
    private int monthSelected;
    private TextView textViewToolbar;
    final Calendar myCalendar= Calendar.getInstance();
    private TextView dateView;
    public MemberRequestsFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_member_requests, container, false);
        currentActivityFragment = getActivity().getSupportFragmentManager();
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);


        MemberTimeChangeRequestModel[] myListData = new MemberTimeChangeRequestModel[]{
                new MemberTimeChangeRequestModel("12","Tue","Approved","12","9:30 AM to 1:00 PM","month"),
                new MemberTimeChangeRequestModel("12","Tue","Approved","12","9:30 AM to 1:00 PM","month"),
                new MemberTimeChangeRequestModel("12","Tue","Approved","12","9:30 AM to 1:00 PM","month"),
                new MemberTimeChangeRequestModel("12","Tue","Approved","12","9:30 AM to 1:00 PM","month"),
                new MemberTimeChangeRequestModel("12","Tue","Approved","12","9:30 AM to 1:00 PM","month"),
                new MemberTimeChangeRequestModel("12","Tue","Approved","12","9:30 AM to 1:00 PM","month"),
                new MemberTimeChangeRequestModel("12","Tue","Approved","12","9:30 AM to 1:00 PM","month"),
                new MemberTimeChangeRequestModel("12","Tue","Approved","12","9:30 AM to 1:00 PM","month")
        };

        MemberTimeChangeRequestAdapter adapter = new MemberTimeChangeRequestAdapter(myListData, getActivity());
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

    private void updateLabel(){
        String myFormat="MMMM, yyyy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        dateView.setText(dateFormat.format(myCalendar.getTime()));
    }
//
//    private DatePickerDialog createDialogWithoutDateField() {
//        DatePickerDialog dpd = new DatePickerDialog(getContext(), null, 2014, 1, 24);
//        try {
//            java.lang.reflect.Field[] datePickerDialogFields = dpd.getClass().getDeclaredFields();
//
//            for (java.lang.reflect.Field datePickerDialogField : datePickerDialogFields) {
//                if (datePickerDialogField.getName().equals("mDatePicker")) {
//                    datePickerDialogField.setAccessible(true);
//                    DatePicker datePicker = (DatePicker) datePickerDialogField.get(dpd);
//                    java.lang.reflect.Field[] datePickerFields = datePickerDialogField.getType().getDeclaredFields();
//                    for (java.lang.reflect.Field datePickerField : datePickerFields) {
//                        Log.e("tes!!!!!!!!!!!t", datePickerField.getName());
//                        if ("mDaySpinner".equals(datePickerField.getName())) {
//                            datePickerField.setAccessible(true);
//                            Object dayPicker = datePickerField.get(datePicker);
//                            ((View) dayPicker).setVisibility(View.INVISIBLE);
//                        }
//                    }
//                }
//            }
//        }
//        catch (Exception ex) {
//        }
//        return dpd;
//    }
}