package com.deeppatel.rotamanager.repositories.TimeEntryRepository;

import com.deeppatel.rotamanager.models.TimeEntry;
import com.deeppatel.rotamanager.repositories.FirebaseRepository;
import com.deeppatel.rotamanager.repositories.OnRepositoryTaskCompleteListener;

import java.util.List;

public class FirebaseTimeEntryRepository extends FirebaseRepository implements TimeEntryRepository {
    static FirebaseTimeEntryRepository instance;
    public static FirebaseTimeEntryRepository getInstance() {
        if (instance == null) {
            instance = new FirebaseTimeEntryRepository();
        }
        return instance;
    }

    final String COLLECTION = "time_entries";

    @Override
    public void getAllTimeEntries(OnRepositoryTaskCompleteListener<List<TimeEntry>> onCompleteListener) {
        getQueryResult(getCollectionReference(COLLECTION), TimeEntry.class, onCompleteListener);
    }

    @Override
    public void getTimeEntriesForDate(String date, OnRepositoryTaskCompleteListener<List<TimeEntry>> onCompleteListener) {
        getQueryResult(
                getCollectionReference(COLLECTION).whereEqualTo("date", date),
                TimeEntry.class,
                onCompleteListener
        );
    }

    @Override
    public void getTimeEntriesForUser(String userId, OnRepositoryTaskCompleteListener<List<TimeEntry>> onCompleteListener) {
        getQueryResult(getCollectionReference(COLLECTION).whereEqualTo("user.uid", userId), TimeEntry.class, onCompleteListener);
    }

    @Override
    public void addNewTimeEntries(List<TimeEntry> timeEntries, OnRepositoryTaskCompleteListener<Void> onCompleteListener) {
        addNewDocuments(COLLECTION, timeEntries, onCompleteListener);
    }

}
