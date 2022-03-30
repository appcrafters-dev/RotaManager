package com.deeppatel.rotamanager.repositories.TimeEntryRepository;

import com.deeppatel.rotamanager.models.TimeEntry;
import com.deeppatel.rotamanager.repositories.OnRepositoryTaskCompleteListener;

import java.util.List;

public interface TimeEntryRepository {
    void getAllTimeEntries(OnRepositoryTaskCompleteListener<List<TimeEntry>> onCompleteListener);

    void getTimeEntriesForDate(String date, OnRepositoryTaskCompleteListener<List<TimeEntry>> onCompleteListener);

    void getTimeEntriesForUser(String userId, OnRepositoryTaskCompleteListener<List<TimeEntry>> onCompleteListener);

    void addNewTimeEntries(List<TimeEntry> timeEntries, OnRepositoryTaskCompleteListener<Void> onCompleteListener);
}
