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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    private final String TAG = "LoginActivity";
    Toast errorToast;
    private FirebaseAuth mAuth;
    private Button admin;

    @Override
    protected void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            new RedirectToActivity().redirectActivityAfterFinish(LoginActivity.this, AdminDashboard.class);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        admin = findViewById(R.id.adminDashboardRedirect);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        mAuth = FirebaseAuth.getInstance();

        Button loginBtn = findViewById(R.id.activity_login_btn_login);
        EditText emailEt = findViewById(R.id.activity_login_et_email);
        EditText inviteCodeEt = findViewById(R.id.activity_login_et_invite_code);
        loginBtn.setOnClickListener(view -> performLogin(emailEt.getText().toString(), inviteCodeEt.getText().toString(), false));

        admin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                performLogin(emailEt.getText().toString(), inviteCodeEt.getText().toString(), true);
            }
        });
    }

    private void performLogin(String email, String inviteCode, Boolean admin) {
        String errorMessage = validateForm(email, inviteCode);
        if (!errorMessage.isEmpty()) {
            showErrorMessage(errorMessage);
            return;
        }

        mAuth.signInWithEmailAndPassword(email, inviteCode)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            new AdminUser(email,inviteCode);
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(LoginActivity.this, "Done", Toast.LENGTH_LONG).show();
                            if(admin){
                                new RedirectToActivity().redirectActivityAfterFinish(LoginActivity.this, AdminDashboard.class);
                            }
//                          TODO: redirect
                        } else {
                            // If sign in fails, display a message to the user.
                            Exception exception = task.getException();
                            if (exception.getClass() == FirebaseAuthInvalidUserException.class) {
                                showErrorMessage("User not found");
                            } else if (exception.getClass() == FirebaseAuthInvalidCredentialsException.class) {
                                showErrorMessage("Invalid credentials");
                            } else {
                                showErrorMessage("Authentication failed");
                                Log.w(TAG, "signInWithEmail:failure", exception);
                            }
                            Toast.makeText(LoginActivity.this, "Not Done", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private String validateForm(String email, String inviteCode) {
        if (email.isEmpty()) return "Email is required";
        if (!isValidEmail(email)) return "Invalid email";
        if (inviteCode.isEmpty()) return "InviteCode is required";
        return "";
    }

    private Boolean isValidEmail(String value) {
        Pattern pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(value);
        return matcher.find();
    }

    private void showErrorMessage(String message) {
        if (message == null || message.isEmpty()) return;
        if (errorToast != null) errorToast.cancel();
        errorToast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        errorToast.show();
    }

}
