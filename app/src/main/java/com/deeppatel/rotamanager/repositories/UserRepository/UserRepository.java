package com.deeppatel.rotamanager.repositories.UserRepository;

import com.deeppatel.rotamanager.models.TimeEntry;
import com.deeppatel.rotamanager.models.User;
import com.deeppatel.rotamanager.repositories.OnRepositoryTaskCompleteListener;

import java.util.List;


public interface UserRepository {
    void getUser(String uid, OnRepositoryTaskCompleteListener<User> onCompleteListener);

    void addNewStaffMember(User member, OnRepositoryTaskCompleteListener<Void> onCompleteListener);

    void updateUser(User user, OnRepositoryTaskCompleteListener<Void> onCompleteListener);

    void getStaffMembers(OnRepositoryTaskCompleteListener<List<User>> onCompleteListener);

    void getTimeEntries(String uid, OnRepositoryTaskCompleteListener<List<TimeEntry>> onCompleteListener);


}
