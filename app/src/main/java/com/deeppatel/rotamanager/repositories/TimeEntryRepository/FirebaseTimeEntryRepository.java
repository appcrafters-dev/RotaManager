package com.deeppatel.rotamanager.repositories.TimeEntryRepository;

import com.deeppatel.rotamanager.models.TimeChangeRequest;
import com.deeppatel.rotamanager.models.TimeEntry;
import com.deeppatel.rotamanager.repositories.FirebaseRepository;
import com.deeppatel.rotamanager.repositories.OnRepositoryTaskCompleteListener;

import java.util.HashMap;
import java.util.List;

public class FirebaseTimeEntryRepository extends FirebaseRepository implements TimeEntryRepository {
    static FirebaseTimeEntryRepository instance;
    final String COLLECTION = "time_entries";

    public static FirebaseTimeEntryRepository getInstance() {
        if (instance == null) {
            instance = new FirebaseTimeEntryRepository();
        }
        return instance;
    }

    @Override
    public void getAllTimeEntries(OnRepositoryTaskCompleteListener<List<TimeEntry>> onCompleteListener) {
        getQueryResult(getCollectionReference(COLLECTION), TimeEntry.class, onCompleteListener);
    }

    @Override
    public void getTimeEntriesForDate(String date,
                                      OnRepositoryTaskCompleteListener<List<TimeEntry>> onCompleteListener) {
        getQueryResult(
                getCollectionReference(COLLECTION).whereEqualTo("date", date),
                TimeEntry.class,
                onCompleteListener
        );
    }

    @Override
    public void getTimeEntriesForUser(String userId,
                                      OnRepositoryTaskCompleteListener<List<TimeEntry>> onCompleteListener) {
        getQueryResult(getCollectionReference(COLLECTION).whereEqualTo("user.uid", userId), TimeEntry.class,
                onCompleteListener);
    }

    @Override
    public void addNewTimeEntries(List<TimeEntry> timeEntries,
                                  OnRepositoryTaskCompleteListener<Void> onCompleteListener) {
        addNewDocuments(COLLECTION, timeEntries, onCompleteListener);
    }

    @Override
    public void updateTimeEntry(TimeChangeRequest timeChangeRequest,
                                OnRepositoryTaskCompleteListener<Void> onCompleteListener) {
        updateDocument(COLLECTION, timeChangeRequest.getTimeEntry().getId(), new HashMap<String, Object>() {{
            put("from", timeChangeRequest.getFrom().toString());
            put("to", timeChangeRequest.getFrom().toString());
        }}, onCompleteListener);
    }

}
