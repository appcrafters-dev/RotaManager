package com.deeppatel.rotamanager.members;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.deeppatel.rotamanager.R;
import com.deeppatel.rotamanager.admin.EditStaffMember;
import com.deeppatel.rotamanager.helpers.MemberTimeChangeRequestAdapter;
import com.deeppatel.rotamanager.helpers.MemberTimeChangeRequestModel;
import com.deeppatel.rotamanager.helpers.MemberTimetableAdapter;
import com.deeppatel.rotamanager.helpers.MemberTimetableModel;
import com.deeppatel.rotamanager.helpers.RedirectToActivity;

public class MemberScheduleFragment extends Fragment {
    static FragmentManager currentActivityFragment;
    public MemberScheduleFragment() { }
    static CardView card;

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


        MemberTimetableModel[] myListData = new MemberTimetableModel[]{
                new MemberTimetableModel("12","Tue","12","9:00 AM","1:00 PM"),
                new MemberTimetableModel("12","Tue","12","9:00 AM","1:00 PM"),
                new MemberTimetableModel("12","Tue","12","9:00 AM","1:00 PM"),
                new MemberTimetableModel("12","Tue","12","9:00 AM","1:00 PM"),
                new MemberTimetableModel("12","Tue","12","9:00 AM","1:00 PM"),
                new MemberTimetableModel("12","Tue","12","9:00 AM","1:00 PM"),
                new MemberTimetableModel("12","Tue","12","9:00 AM","1:00 PM"),
                new MemberTimetableModel("12","Tue","12","9:00 AM","1:00 PM"),
                new MemberTimetableModel("12","Tue","12","9:00 AM","1:00 PM")
        };

        MemberTimetableAdapter adapter = new MemberTimetableAdapter(myListData, getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);


        return rootView;
    }
}