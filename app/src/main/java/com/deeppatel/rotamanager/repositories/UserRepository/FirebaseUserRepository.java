package com.deeppatel.rotamanager.repositories.UserRepository;

import com.deeppatel.rotamanager.models.TimeEntry;
import com.deeppatel.rotamanager.models.User;
import com.deeppatel.rotamanager.repositories.AuthRepository.FirebaseAuthRepository;
import com.deeppatel.rotamanager.repositories.FirebaseRepository;
import com.deeppatel.rotamanager.repositories.OnRepositoryTaskCompleteListener;

import java.util.ArrayList;
import java.util.List;


public class FirebaseUserRepository extends FirebaseRepository implements UserRepository {
    private static FirebaseUserRepository instance;
    public static FirebaseUserRepository getInstance() {
        if(instance == null) {
            instance = new FirebaseUserRepository();
        }
        return instance;
    }

    public void getUser(String uid, OnRepositoryTaskCompleteListener<User> onCompleteListener) {
        getDocumentResult("users", uid, User.class, onCompleteListener);
    }

    @Override
    public void addNewStaffMember(User member, OnRepositoryTaskCompleteListener<User> onCompleteListener) {
        addNewDocument("users", member, onCompleteListener);
    }

    public void getStaffMembers(OnRepositoryTaskCompleteListener<List<User>> onCompleteListener) {
        getQueryResult(
                getCollectionReference("users").whereNotEqualTo("role","admin"),
                User.class,
                onCompleteListener
        );
    }

    public void getTimeEntries(String uid, OnRepositoryTaskCompleteListener<List<TimeEntry>> onCompleteListener) {
        getQueryResult(
                getCollectionReference("users").document(uid).collection("Schedule"),
                TimeEntry.class,
                onCompleteListener
        );
    }
}
