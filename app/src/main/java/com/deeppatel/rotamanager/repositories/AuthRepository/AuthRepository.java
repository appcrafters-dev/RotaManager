package com.deeppatel.rotamanager.repositories.AuthRepository;

import com.deeppatel.rotamanager.models.User;
import com.deeppatel.rotamanager.repositories.OnRepositoryTaskCompleteListener;
import com.deeppatel.rotamanager.repositories.UserRepository.UserRepository;


public interface AuthRepository extends UserRepository {
    String getCurrentUserId();

    void getCurrentUser(OnRepositoryTaskCompleteListener<User> onCompleteListener);
    User getCurrentUser();

    void login(String email, String inviteCode, OnRepositoryTaskCompleteListener<User> onCompleteListener);
    void logout();

    void createNewUser(User user, OnRepositoryTaskCompleteListener<User> onCompleteListener);
}


