package com.deeppatel.rotamanager.repositories.AuthRepository;

import com.deeppatel.rotamanager.models.User;
import com.deeppatel.rotamanager.repositories.OnRepositoryTaskCompleteListener;
import com.deeppatel.rotamanager.repositories.UserRepository.UserRepository;


public interface AuthRepository extends UserRepository {
    void getCurrentUser(OnRepositoryTaskCompleteListener<User> onCompleteListener);
    void login(String email, String inviteCode, OnRepositoryTaskCompleteListener<User> onCompleteListener);
}


