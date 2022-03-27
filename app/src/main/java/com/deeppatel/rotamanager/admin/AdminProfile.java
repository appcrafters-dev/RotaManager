package com.deeppatel.rotamanager.admin;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.deeppatel.rotamanager.LoginActivity;
import com.deeppatel.rotamanager.R;
import com.deeppatel.rotamanager.helpers.Navigate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AdminProfile extends AppCompatActivity {
    private ImageView back;
    private FirebaseAuth mAuth;
    private EditText name, email, phone, designation;
    private Button update, logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile);

        name = findViewById(R.id.admin_name);
        email = findViewById(R.id.admin_email);
        phone = findViewById(R.id.admin_phoneNum);
        designation = findViewById(R.id.admin_designation);
        update = findViewById(R.id.admin_submit);
        logout = findViewById(R.id.admin_logout);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users").document(currentUser.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            loadUI(document);
                        }
                    }

                });

        back = findViewById(R.id.backButtonToolbar);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String updatedName = name.getText().toString();
                String updatedEmail = email.getText().toString();
                String updatedPhone = phone.getText().toString();
                String updatedDesignation = designation.getText().toString();

                Map<String, Object> userUpdate = new HashMap<>();
                userUpdate.put("name", updatedName);
                userUpdate.put("email", updatedEmail);
                userUpdate.put("phone", updatedPhone);
                userUpdate.put("designation", updatedDesignation);
                Log.i("Update To Be Values", userUpdate.toString());
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("users").document(currentUser.getUid())
                        .set(userUpdate)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d("FireStore", "DocumentSnapshot successfully re-written!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("Staff", "Error adding document", e);
                            }
                        });
                Toast.makeText(AdminProfile.this, "User Updated", Toast.LENGTH_SHORT).show();

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Navigate.replace(AdminProfile.this, LoginActivity.class);
            }
        });
    }

    private void loadUI(DocumentSnapshot doc) {
        name.setText(doc.get("name").toString());
        email.setText(doc.get("email").toString());
        phone.setText(doc.get("phone").toString());
        designation.setText(doc.get("designation").toString());
    }
}