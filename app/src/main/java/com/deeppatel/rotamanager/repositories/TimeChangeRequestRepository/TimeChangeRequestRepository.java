package com.deeppatel.rotamanager.repositories.TimeChangeRequestRepository;

import com.deeppatel.rotamanager.models.TimeChangeRequest;
import com.deeppatel.rotamanager.models.TimeChangeRequestsByDate;
import com.deeppatel.rotamanager.repositories.OnRepositoryTaskCompleteListener;

import java.util.List;

public interface TimeChangeRequestRepository {
    void getAllOpenTimeChangeRequests(OnRepositoryTaskCompleteListener<List<TimeChangeRequest>> onRepositoryTaskCompleteListener);

    void getTimeChangeRequestsForUser(String userId,
                                      OnRepositoryTaskCompleteListener<List<TimeChangeRequest>> onRepositoryTaskCompleteListener);

    List<TimeChangeRequestsByDate> groupTimeChangeRequestsByDate(List<TimeChangeRequest> timeChangeRequests);

    void createTimeChangeRequest(TimeChangeRequest timeChangeRequest,
                                 OnRepositoryTaskCompleteListener<TimeChangeRequest> onRepositoryTaskCompleteListener);

    void approveTimeChangeRequest(String id, OnRepositoryTaskCompleteListener<Void> onRepositoryTaskCompleteListener);

    void rejectTimeChangeRequest(String id, OnRepositoryTaskCompleteListener<Void> onRepositoryTaskCompleteListener);
}
