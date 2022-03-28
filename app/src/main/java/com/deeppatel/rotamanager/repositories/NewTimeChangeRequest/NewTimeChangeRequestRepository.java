package com.deeppatel.rotamanager.repositories.NewTimeChangeRequest;

import com.deeppatel.rotamanager.models.User;
import com.deeppatel.rotamanager.repositories.OnRepositoryTaskCompleteListener;

import java.util.List;

public interface NewTimeChangeRequestRepository {
    void getUser(String uid, OnRepositoryTaskCompleteListener<User> onCompleteListener);

    void addNewStaffMember(User member, OnRepositoryTaskCompleteListener<User> onCompleteListener);

    void getStaffMembers(OnRepositoryTaskCompleteListener<List<User>> onCompleteListener);

}
