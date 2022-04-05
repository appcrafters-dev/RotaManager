package com.deeppatel.rotamanager.presentation.member.TimeChangeRequest;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deeppatel.rotamanager.R;
import com.deeppatel.rotamanager.helpers.Utils;
import com.deeppatel.rotamanager.models.TimeChangeRequest;
import com.deeppatel.rotamanager.repositories.AuthRepository.AuthRepository;
import com.deeppatel.rotamanager.repositories.AuthRepository.FirebaseAuthRepository;
import com.deeppatel.rotamanager.repositories.TimeChangeRequestRepository.FirebaseTimeChangeRequestRepository;
import com.deeppatel.rotamanager.repositories.TimeChangeRequestRepository.TimeChangeRequestRepository;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TimeChangeRequestsFragment extends Fragment {
    private final List<TimeChangeRequest> allTimeChangeRequests = new ArrayList<>();
    private final List<TimeChangeRequest> timeChangeRequests = new ArrayList<>();
    AuthRepository authRepository;
    TimeChangeRequestRepository timeChangeRequestRepository;
    private FragmentActivity activity;
    private TextView selectMonthTextView;
    private MemberTimeChangeRequestAdapter adapter;
    private DateTime selectedMonth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_member_requests, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.rv_time_entries);
        selectMonthTextView = rootView.findViewById(R.id.textViewToolbar);

        authRepository = FirebaseAuthRepository.getInstance();
        timeChangeRequestRepository = FirebaseTimeChangeRequestRepository.getInstance();

        selectedMonth = DateTime.now();
        selectMonthTextView.setOnClickListener(v -> {
            new DatePickerDialog(
                    getContext(),
                    (view1, year, month, day) -> {
                        selectedMonth = new DateTime(year, month + 1, day, 0, 0);
                        updateTimeChangeRequests();
                    },
                    selectedMonth.getYear(),
                    selectedMonth.getMonthOfYear() - 1,
                    selectedMonth.getDayOfMonth()
            ).show();
        });

        adapter = new MemberTimeChangeRequestAdapter(timeChangeRequests, activity);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        // Initial update
        updateTimeChangeRequests();

        fetchTimeChangeRequests();
        return rootView;
    }

    @SuppressLint("NotifyDataSetChanged")
    private void updateTimeChangeRequests() {
        selectMonthTextView.setText(Utils.dateTimeToMonthString(selectedMonth));
        timeChangeRequests.clear();
        timeChangeRequests.addAll(
                allTimeChangeRequests
                        .stream()
                        .filter(
                                timeChangeRequest -> Utils.dateTimeToMonthString(timeChangeRequest.getTimeEntry().getDate()).equals(Utils.dateTimeToMonthString(selectedMonth))
                        ).collect(Collectors.toList())
        );
        adapter.notifyDataSetChanged();
    }

    private void fetchTimeChangeRequests() {
        timeChangeRequestRepository.getTimeChangeRequestsForUser(authRepository.getCurrentUserId(), result -> {
            if (result.getErrorMessage() != null) {
                Utils.showToastMessage(activity, result.getErrorMessage());
                return;
            }

            allTimeChangeRequests.clear();
            allTimeChangeRequests.addAll(result.getResult());
            updateTimeChangeRequests();
        });
    }
}
