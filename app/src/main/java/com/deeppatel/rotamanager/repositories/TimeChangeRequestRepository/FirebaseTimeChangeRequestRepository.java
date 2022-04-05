package com.deeppatel.rotamanager.repositories.TimeChangeRequestRepository;

import com.deeppatel.rotamanager.helpers.Utils;
import com.deeppatel.rotamanager.helpers.enums.TimeChangeRequestStatus;
import com.deeppatel.rotamanager.models.RepositoryResult;
import com.deeppatel.rotamanager.models.TimeChangeRequest;
import com.deeppatel.rotamanager.models.TimeChangeRequestsByDate;
import com.deeppatel.rotamanager.repositories.FirebaseRepository;
import com.deeppatel.rotamanager.repositories.OnRepositoryTaskCompleteListener;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


public class FirebaseTimeChangeRequestRepository extends FirebaseRepository implements TimeChangeRequestRepository {
    private static FirebaseTimeChangeRequestRepository instance;
    private final String COLLECTION = "time_change_requests";

    public static FirebaseTimeChangeRequestRepository getInstance() {
        if (instance == null) {
            instance = new FirebaseTimeChangeRequestRepository();
        }
        return instance;
    }


    @Override
    public void getAllOpenTimeChangeRequests(OnRepositoryTaskCompleteListener<List<TimeChangeRequest>> taskCompleteListener) {
        getQueryResult(
                getCollectionReference(COLLECTION).whereEqualTo("status", TimeChangeRequestStatus.PENDING.getStatus()),
                TimeChangeRequest.class,
                taskCompleteListener
        );
    }

    @Override
    public void getTimeChangeRequestsForUser(String userId,
                                             OnRepositoryTaskCompleteListener<List<TimeChangeRequest>> taskCompleteListener) {
        getQueryResult(
                getCollectionReference(COLLECTION).whereEqualTo("timeEntry.user.uid", userId),
                TimeChangeRequest.class,
                taskCompleteListener
        );
    }

    @Override
    public void createTimeChangeRequest(TimeChangeRequest timeChangeRequest,
                                        OnRepositoryTaskCompleteListener<TimeChangeRequest> taskCompleteListener) {
        addNewDocument(COLLECTION, timeChangeRequest, taskCompleteListener);
    }

    @Override
    public void approveTimeChangeRequest(String id, OnRepositoryTaskCompleteListener<Void> taskCompleteListener) {
        setTimeChangeRequestStatus(id, TimeChangeRequestStatus.APPROVED, taskCompleteListener);
    }

    @Override
    public void rejectTimeChangeRequest(String id, OnRepositoryTaskCompleteListener<Void> taskCompleteListener) {
        setTimeChangeRequestStatus(id, TimeChangeRequestStatus.REJECTED, taskCompleteListener);
    }

    private void setTimeChangeRequestStatus(String id, TimeChangeRequestStatus status, OnRepositoryTaskCompleteListener<Void>  taskCompleteListener){
        updateDocument(COLLECTION, id, new HashMap<String, Object>(){{
            put("status", status.getStatus());
        }}, taskCompleteListener);
    }


    public List<TimeChangeRequestsByDate> groupTimeChangeRequestsByDate(List<TimeChangeRequest> timeChangeRequests) {
        HashMap<String, List<TimeChangeRequest>> timeChangeRequestsByDate =
                new HashMap<>();
        for (TimeChangeRequest timeChangeRequest : timeChangeRequests) {
            String key =
                    Utils.dateTimeToDateString(timeChangeRequest.getTimeEntry().getDate());
            timeChangeRequestsByDate
                    .computeIfAbsent(key, k -> new ArrayList<>())
                    .add(timeChangeRequest);
        }
        return timeChangeRequestsByDate
                .entrySet()
                .stream()
                .map(entry -> new TimeChangeRequestsByDate(entry.getKey(), entry.getValue())
                )
                .collect(Collectors.toList());
    }
}
