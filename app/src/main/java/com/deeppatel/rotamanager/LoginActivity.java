package com.deeppatel.rotamanager;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.deeppatel.rotamanager.helpers.Navigate;
import com.deeppatel.rotamanager.helpers.Utils;
import com.deeppatel.rotamanager.models.RepositoryResult;
import com.deeppatel.rotamanager.models.User;
import com.deeppatel.rotamanager.repositories.AuthRepository.AuthRepository;
import com.deeppatel.rotamanager.repositories.AuthRepository.FirebaseAuthRepository;
import com.deeppatel.rotamanager.repositories.OnRepositoryTaskCompleteListener;

public class LoginActivity extends AppCompatActivity {
    AuthRepository authRepository;

    Button loginButton;
    EditText emailEditText;
    EditText inviteCodeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.hide();

        authRepository = FirebaseAuthRepository.getInstance();

        loginButton = findViewById(R.id.activity_login_btn_login);
        emailEditText = findViewById(R.id.activity_login_et_email);
        inviteCodeEditText = findViewById(R.id.activity_login_et_invite_code);

        loginButton.setOnClickListener(view -> performLogin(emailEditText.getText().toString(), inviteCodeEditText.getText().toString()));

    }

    private void performLogin(String email, String inviteCode) {
        String errorMessage = validateForm(email, inviteCode);
        if (errorMessage != null) {
            Utils.showToastMessage(this, errorMessage);
            return;
        }

        authRepository.login(email, inviteCode, result -> {
            User user = result.getResult();
            String errorMessage1 = result.getErrorMessage();
            if(user != null){
                Utils.showToastMessage(LoginActivity.this, "Redirecting...");
                Navigate.redirectBasedOnUser(LoginActivity.this, user);
            } else if (errorMessage1 != null) {
                Utils.showToastMessage(LoginActivity.this, errorMessage1);
            }
        });
    }

    private String validateForm(String email, String inviteCode) {
        if (email.isEmpty()) return "Email is required";
        if (!Utils.isValidEmail(email)) return "Invalid email";
        if (inviteCode.isEmpty()) return "InviteCode is required";
        return null;
    }
}
