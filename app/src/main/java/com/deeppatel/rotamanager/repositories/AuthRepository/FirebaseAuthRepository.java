package com.deeppatel.rotamanager.repositories.AuthRepository;

import androidx.annotation.NonNull;

import com.deeppatel.rotamanager.models.RepositoryResult;
import com.deeppatel.rotamanager.models.User;
import com.deeppatel.rotamanager.repositories.OnRepositoryTaskCompleteListener;
import com.deeppatel.rotamanager.repositories.UserRepository.FirebaseUserRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class FirebaseAuthRepository extends FirebaseUserRepository implements AuthRepository {
    FirebaseAuth firebaseAuth;

    private static FirebaseAuthRepository instance;
    public static FirebaseAuthRepository getInstance() {
        if(instance == null) {
            instance = new FirebaseAuthRepository();
        }
        return instance;
    }

    FirebaseAuthRepository() {
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void getCurrentUser(OnRepositoryTaskCompleteListener<User> onCompleteListener) {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser == null) {
            onCompleteListener.onComplete(new RepositoryResult<>());
            return;
        }
        getUser(firebaseUser.getUid(), onCompleteListener);
    }

    @Override
    public void login(String email, String inviteCode, OnRepositoryTaskCompleteListener<User> onCompleteListener) {
        firebaseAuth.signInWithEmailAndPassword(email, inviteCode)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        RepositoryResult<User> result = new RepositoryResult<>(null, "Authentication failed");
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = task.getResult().getUser();
                            if (firebaseUser != null) {
                                getUser(firebaseUser.getUid(), onCompleteListener);
                                return;
                            }
                        }

                        // if task fails
                        Exception exception = task.getException();
                        if (exception != null) {
                            if (exception.getClass() == FirebaseAuthInvalidUserException.class) {
                                result.setErrorMessage("User not found");
                            } else if (exception.getClass() == FirebaseAuthInvalidCredentialsException.class) {
                                result.setErrorMessage("Invalid credentials");
                            }
                        }

                        onCompleteListener.onComplete(result);
                    }
                });
    }
}
