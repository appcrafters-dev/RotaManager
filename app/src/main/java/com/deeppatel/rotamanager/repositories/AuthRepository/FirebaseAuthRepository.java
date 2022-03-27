package com.deeppatel.rotamanager.repositories.AuthRepository;

import android.content.SharedPreferences;
import android.util.Log;

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
    static User currentUser;

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
        if (currentUser != null){
            onCompleteListener.onComplete(new RepositoryResult<>(currentUser, null));
            return;
        }
        fetchCurrentUser(onCompleteListener);
    }

    public void fetchCurrentUser(OnRepositoryTaskCompleteListener<User> onCompleteListener){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser == null) {
            onCompleteListener.onComplete(new RepositoryResult<>());
            return;
        }
        getUser(firebaseUser.getUid(), new OnRepositoryTaskCompleteListener<User>() {
            @Override
            public void onComplete(@NonNull RepositoryResult<User> result) {
                if(result.getResult() != null){
                    currentUser = result.getResult();
                }
                onCompleteListener.onComplete(result);

            }
        });
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

    @Override
    public void createNewUser(User user, OnRepositoryTaskCompleteListener<User> onCompleteListener) {
        Log.d("createNewUser", "stop 1");

        getCurrentUser(new OnRepositoryTaskCompleteListener<User>() {
            @Override
            public void onComplete(@NonNull RepositoryResult<User> result) {
                Log.d("createNewUser", "currentUser" + currentUser.toString());

                if (result.getResult() == null){
                    Log.d("createNewUser", "stop 2: no current user");
                    onCompleteListener.onComplete(new RepositoryResult<>(null, "You are not allowed to create a new member."));
                    return;
                }

                firebaseAuth.createUserWithEmailAndPassword(user.getEmail(), user.getInviteCode()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Log.d("createNewUser", "Error");
                            onCompleteListener.onComplete(new RepositoryResult<>(null, task.getException().getMessage()));
                            return;
                        }
                        Log.d("createNewUser", "stop 3");
                        firebaseAuth.signOut();
                        Log.d("createNewUser", "stop 4");
                        login(currentUser.getEmail(), currentUser.getInviteCode(), new OnRepositoryTaskCompleteListener<User>() {
                            @Override
                            public void onComplete(@NonNull RepositoryResult<User> result) {
                                Log.d("createNewUser", "stop 5");

                                if(result.getErrorMessage() != null){
                                    Log.d("createNewUser", "stop 5.5");

                                    result.setErrorMessage("Could not login back as admin: " + result.getErrorMessage());
                                    onCompleteListener.onComplete(result);
                                    return;
                                }
                                Log.d("createNewUser", "stop 6");
                                addNewStaffMember(user, onCompleteListener);
                            }
                        });
                    }
                });
            }
        });
    }
}
