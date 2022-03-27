package com.deeppatel.rotamanager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.deeppatel.rotamanager.admin.AdminDashboard;
import com.deeppatel.rotamanager.helpers.RedirectToActivity;
import com.deeppatel.rotamanager.members.MemberHomePage;
import com.deeppatel.rotamanager.models.RepositoryResult;
import com.deeppatel.rotamanager.models.User;
import com.deeppatel.rotamanager.repositories.AuthRepository.AuthRepository;
import com.deeppatel.rotamanager.repositories.AuthRepository.FirebaseAuthRepository;
import com.deeppatel.rotamanager.repositories.OnRepositoryTaskCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class MainActivity extends AppCompatActivity {
    AuthRepository authRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.hide();

        authRepository = FirebaseAuthRepository.getInstance();
        authenticate();
    }

    private void authenticate() {
        authRepository.getCurrentUser(new OnRepositoryTaskCompleteListener<User>() {
            @Override
            public void onComplete(@NonNull RepositoryResult<User> userResult) {
                RedirectToActivity.redirectBasedOnUser(MainActivity.this, userResult.getResult());
            }
        });
    }
}