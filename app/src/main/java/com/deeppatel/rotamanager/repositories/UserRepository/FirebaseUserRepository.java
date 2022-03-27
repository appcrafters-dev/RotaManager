package com.deeppatel.rotamanager.repositories.UserRepository;

import com.deeppatel.rotamanager.models.User;
import com.deeppatel.rotamanager.repositories.FirebaseRepository;
import com.deeppatel.rotamanager.repositories.OnRepositoryTaskCompleteListener;


public class FirebaseUserRepository extends FirebaseRepository implements UserRepository {
    public void getUser(String uid, OnRepositoryTaskCompleteListener<User> onCompleteListener) {
        getDocument("users", uid, User.class, onCompleteListener);
    }
}
