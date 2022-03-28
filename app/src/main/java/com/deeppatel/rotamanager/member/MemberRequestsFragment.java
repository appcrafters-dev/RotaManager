package com.deeppatel.rotamanager.member;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.deeppatel.rotamanager.R;
import com.deeppatel.rotamanager.helpers.adapters.MemberScheduleAdapter.MemberTimeChangeRequestAdapter;
import com.deeppatel.rotamanager.models.MemberTimeChangeRequestModel;

import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MemberRequestsFragment extends Fragment {
    static FragmentManager currentActivityFragment;
    private int currentYear;
    private int yearSelected;
    private int monthSelected;
    private TextView textViewToolbar;
    final Calendar myCalendar = Calendar.getInstance();
    private TextView dateView;
    String myFormat = "MMMM, yyyy";
    String monthFormatt = "MMMM";
    SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
    SimpleDateFormat monthFormat = new SimpleDateFormat(monthFormatt, Locale.US);

    List<MemberTimeChangeRequestModel> myListData = new ArrayList<>();
    List<MemberTimeChangeRequestModel> myListData2 = new ArrayList<>();

    MemberTimeChangeRequestAdapter adapter = new MemberTimeChangeRequestAdapter(myListData2, getActivity());

    public MemberRequestsFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_member_requests, container, false);
        currentActivityFragment = getActivity().getSupportFragmentManager();
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);

        myListData.add(new MemberTimeChangeRequestModel("12adadsad", "Denied", "John", "Tue", "9:30 AM","1:30 PM","12","March"));
        myListData.add(new MemberTimeChangeRequestModel("12adadsad", "Denied", "John", "Tue", "9:30 AM","1:30 PM","12","March"));
        myListData.add(new MemberTimeChangeRequestModel("12adadsad", "Denied", "John", "Tue", "9:30 AM","1:30 PM","12","March"));
        myListData.add(new MemberTimeChangeRequestModel("12adadsad", "Denied", "John", "Tue", "9:30 AM","1:30 PM","12","March"));
        myListData.add(new MemberTimeChangeRequestModel("12adadsad", "Denied", "John", "Tue", "9:30 AM","1:30 PM","12","March"));


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);
                updateLabel();
            }
        };
        dateView = rootView.findViewById(R.id.textViewToolbar);

        dateView.setText(Month.of(myCalendar.MONTH+1).name().toUpperCase() + ", "+ myCalendar.get(Calendar.YEAR));
        updateCard();
        dateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        return rootView;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void updateLabel() {
        dateView.setText(dateFormat.format(myCalendar.getTime()).toUpperCase());
        updateCard();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void updateCard(){
        String selectedMonth = dateView.getText().toString().toUpperCase();
        String[] arrOfStr = selectedMonth.split(", ", 2);

        myListData2.clear();
        for (MemberTimeChangeRequestModel x : myListData){
            if (x.month.toUpperCase().equals(arrOfStr[0])){
                myListData2.add(new MemberTimeChangeRequestModel(x.uid, x.status, x.name,x.day,x.from,x.to ,x.date, x.month));
            }
        }
        adapter.notifyDataSetChanged();
    }
}
