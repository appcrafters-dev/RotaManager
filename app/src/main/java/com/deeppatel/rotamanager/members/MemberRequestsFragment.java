package com.deeppatel.rotamanager.members;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.deeppatel.rotamanager.R;
import com.deeppatel.rotamanager.admin.StaffMemberList;
import com.deeppatel.rotamanager.helpers.MemberTimeChangeRequestAdapter;
import com.deeppatel.rotamanager.helpers.MemberTimeChangeRequestModel;
import com.deeppatel.rotamanager.helpers.StaffMemberDataAdapter;
import com.deeppatel.rotamanager.helpers.StaffMemberDataModel;

public class MemberRequestsFragment extends Fragment {
    static FragmentManager currentActivityFragment;
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
                new MemberTimeChangeRequestModel("12","Tue","Approved","12","9:30 AM to 1:00 PM"),
                new MemberTimeChangeRequestModel("12","Tue","Approved","12","9:30 AM to 1:00 PM"),
                new MemberTimeChangeRequestModel("12","Tue","Approved","12","9:30 AM to 1:00 PM"),                new MemberTimeChangeRequestModel("12","Tue","Approved","12","9:30 AM to 1:00 PM"),
                new MemberTimeChangeRequestModel("12","Tue","Approved","12","9:30 AM to 1:00 PM"),
                new MemberTimeChangeRequestModel("12","Tue","Approved","12","9:30 AM to 1:00 PM"),
                new MemberTimeChangeRequestModel("12","Tue","Approved","12","9:30 AM to 1:00 PM"),
                new MemberTimeChangeRequestModel("12","Tue","Approved","12","9:30 AM to 1:00 PM"),
                new MemberTimeChangeRequestModel("12","Tue","Approved","12","9:30 AM to 1:00 PM"),
                new MemberTimeChangeRequestModel("12","Tue","Approved","12","9:30 AM to 1:00 PM"),
                new MemberTimeChangeRequestModel("12","Tue","Approved","12","9:30 AM to 1:00 PM"),
                new MemberTimeChangeRequestModel("12","Tue","Approved","12","9:30 AM to 1:00 PM"),
                new MemberTimeChangeRequestModel("12","Tue","Approved","12","9:30 AM to 1:00 PM")
        };

        MemberTimeChangeRequestAdapter adapter = new MemberTimeChangeRequestAdapter(myListData, getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        return rootView;
    }
}