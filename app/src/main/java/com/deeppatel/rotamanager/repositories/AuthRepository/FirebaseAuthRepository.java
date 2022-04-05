package com.deeppatel.rotamanager.repositories.AuthRepository;

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

public class FirebaseAuthRepository extends FirebaseUserRepository implements AuthRepository {
    static User currentUser;
    private static FirebaseAuthRepository instance;
    FirebaseAuth firebaseAuth;

    FirebaseAuthRepository() {
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public static FirebaseAuthRepository getInstance() {
        if (instance == null) {
            instance = new FirebaseAuthRepository();
        }
        return instance;
    }

    public String getCurrentUserId() {
        if (currentUser != null && currentUser.getUid() != null) {
            return currentUser.getUid();
        }

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            return firebaseUser.getUid();
        }
        return null;
    }

    public void getCurrentUser(OnRepositoryTaskCompleteListener<User> onCompleteListener) {
        if (currentUser != null) {
            onCompleteListener.onComplete(new RepositoryResult<>(currentUser, null));
            return;
        }
        fetchCurrentUser(onCompleteListener);
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void fetchCurrentUser(OnRepositoryTaskCompleteListener<User> onCompleteListener) {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser == null) {
            onCompleteListener.onComplete(new RepositoryResult<>());
            return;
        }
        getUser(firebaseUser.getUid(), result -> {
            if (result.getResult() != null) {
                currentUser = result.getResult();
            }
            onCompleteListener.onComplete(result);

        });
    }

    @Override
    public void login(String email, String inviteCode, OnRepositoryTaskCompleteListener<User> onCompleteListener) {
        firebaseAuth.signInWithEmailAndPassword(email, inviteCode)
                .addOnCompleteListener(task -> {
                    RepositoryResult<User> result = new RepositoryResult<>(null, "Authentication failed");
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = task.getResult().getUser();
                        if (firebaseUser != null) {
                            fetchCurrentUser(onCompleteListener);
                            return;
                        }
                    }

                    Exception exception = task.getException();
                    if (exception != null) {
                        if (exception.getClass() == FirebaseAuthInvalidUserException.class) {
                            result.setErrorMessage("User not found");
                        } else if (exception.getClass() == FirebaseAuthInvalidCredentialsException.class) {
                            result.setErrorMessage("Invalid credentials");
                        }
                    }
                    onCompleteListener.onComplete(result);
                });
    }

    @Override
    public void logout() {
        firebaseAuth.signOut();
        currentUser = null;
    }

    @Override
    public void createNewUser(User user, OnRepositoryTaskCompleteListener<User> onCompleteListener) {
        getCurrentUser(result -> {
            if (result.getResult() == null) {
                onCompleteListener.onComplete(new RepositoryResult<>(null, "You are not allowed to create a new member."));
                return;
            }

            firebaseAuth.createUserWithEmailAndPassword(user.getEmail(), user.getInviteCode()).addOnCompleteListener(task -> {
                FirebaseUser firebaseUser = task.getResult().getUser();
                if (!task.isSuccessful() || firebaseUser == null) {
                    Exception exception = task.getException();
                    String errorMessage = exception != null ? exception.getMessage() : "Something went wrong, please try again later";
                    onCompleteListener.onComplete(new RepositoryResult<>(null, errorMessage));
                    return;
                }

                user.setId(firebaseUser.getUid());
                firebaseAuth.signOut();
                login(currentUser.getEmail(), currentUser.getInviteCode(), loginResult -> {
                    if (loginResult.getErrorMessage() != null) {
                        loginResult.setErrorMessage("Could not login back as admin: " + loginResult.getErrorMessage());
                        onCompleteListener.onComplete(loginResult);
                        return;
                    }
                    addNewStaffMember(user, result2 -> {
                        onCompleteListener.onComplete(new RepositoryResult<>(null, result2.getErrorMessage()));
                    });
                });
            });
        });
    }
}
