package com.deeppatel.rotamanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.deeppatel.rotamanager.helpers.Navigate;
import com.deeppatel.rotamanager.models.RepositoryResult;
import com.deeppatel.rotamanager.models.User;
import com.deeppatel.rotamanager.repositories.AuthRepository.AuthRepository;
import com.deeppatel.rotamanager.repositories.AuthRepository.FirebaseAuthRepository;
import com.deeppatel.rotamanager.repositories.OnRepositoryTaskCompleteListener;


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
                Navigate.redirectBasedOnUser(MainActivity.this, userResult.getResult());
            }
        });
    }
}