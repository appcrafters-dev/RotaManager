package com.deeppatel.rotamanager.repositories.UserRepository;

import com.deeppatel.rotamanager.models.TimeEntry;
import com.deeppatel.rotamanager.models.User;
import com.deeppatel.rotamanager.repositories.OnRepositoryTaskCompleteListener;

import java.util.List;


public interface UserRepository {
    void getUser(String uid, OnRepositoryTaskCompleteListener<User> onCompleteListener);

    void getStaffMembers(OnRepositoryTaskCompleteListener<List<User>> onCompleteListener);
    void createNewStaffMember(User user, OnRepositoryTaskCompleteListener<User> onCompleteListener);

    void getTimeEntries(String uid, OnRepositoryTaskCompleteListener<List<TimeEntry>> onCompleteListener);

}
