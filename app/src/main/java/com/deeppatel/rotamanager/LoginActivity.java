package com.deeppatel.rotamanager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.deeppatel.rotamanager.admin.AdminDashboard;
import com.deeppatel.rotamanager.helpers.AdminUser;
import com.deeppatel.rotamanager.helpers.RedirectToActivity;
import com.deeppatel.rotamanager.helpers.Utils;
import com.deeppatel.rotamanager.members.MemberHomePage;
import com.deeppatel.rotamanager.models.RepositoryResult;
import com.deeppatel.rotamanager.models.User;
import com.deeppatel.rotamanager.repositories.AuthRepository.AuthRepository;
import com.deeppatel.rotamanager.repositories.AuthRepository.FirebaseAuthRepository;
import com.deeppatel.rotamanager.repositories.OnRepositoryTaskCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        authRepository.login(email, inviteCode, new OnRepositoryTaskCompleteListener<User>() {
            @Override
            public void onComplete(@NonNull RepositoryResult<User> result) {
                User user = result.getResult();
                String errorMessage = result.getErrorMessage();
                if(user != null){
                    RedirectToActivity.redirectBasedOnUser(LoginActivity.this, user);
                } else if (errorMessage != null) {
                    Utils.showToastMessage(LoginActivity.this, errorMessage);
                }
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
