package com.deeppatel.rotamanager.member;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class MemberProfileFragment extends Fragment {
    private FirebaseAuth mAuth;
    private Button logout,update;
    private EditText name, email, phone, designation;
    private TextView nameToolbar;

    public MemberProfileFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_member_profile, container, false);
        logout = rootView.findViewById(R.id.member_logout);
        name = rootView.findViewById(R.id.member_name);
        email = rootView.findViewById(R.id.member_email);
        phone = rootView.findViewById(R.id.member_phoneNum);
        designation = rootView.findViewById(R.id.member_designation);
        update = rootView.findViewById(R.id.btn_submit);
        nameToolbar = rootView.findViewById(R.id.textViewToolbar);
        logout = rootView.findViewById(R.id.member_logout);

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
                        .update(userUpdate)
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
                Toast.makeText(getContext(), "User Updated", Toast.LENGTH_SHORT).show();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Navigate.replace(getActivity(), LoginActivity.class);
            }
        });

        return rootView;
    }

    private void loadUI(DocumentSnapshot doc) {
        name.setText(doc.get("name").toString());
        nameToolbar.setText(doc.get("name").toString());
        email.setText(doc.get("email").toString());
        phone.setText(doc.get("phone").toString());
        designation.setText(doc.get("designation").toString());
    }
}