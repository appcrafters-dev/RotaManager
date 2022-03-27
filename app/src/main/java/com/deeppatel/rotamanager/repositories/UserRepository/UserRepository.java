package com.deeppatel.rotamanager.repositories.UserRepository;

import com.deeppatel.rotamanager.models.User;
import com.deeppatel.rotamanager.repositories.OnRepositoryTaskCompleteListener;


public interface UserRepository {
    void getUser(String uid, OnRepositoryTaskCompleteListener<User> onCompleteListener);
}
