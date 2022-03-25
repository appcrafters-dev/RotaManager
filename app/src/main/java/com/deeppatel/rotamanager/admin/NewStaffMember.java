package com.deeppatel.rotamanager.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.deeppatel.rotamanager.LoginActivity;
import com.deeppatel.rotamanager.helpers.RedirectToActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.nio.ByteBuffer;
import com.deeppatel.rotamanager.R;


public class NewStaffMember extends AppCompatActivity {

    private Button submit;
    private ImageView back;

    private EditText memberNameView, emailView, phoneView, designationView;
    private RadioButton gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_staff_member);

        memberNameView = (EditText) findViewById(R.id.member_name);
        emailView = findViewById(R.id.member_email);
        phoneView = findViewById(R.id.member_phoneNum);
        designationView = findViewById(R.id.member_designation);
        submit = findViewById(R.id.member_submit);




        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Staff", "hereeeee");
                String memberName = memberNameView.getText().toString();
                String email = emailView.getText().toString();
                String phone = phoneView.getText().toString();
                String designation = designationView.getText().toString();
                FirebaseAuth mAuth;
                mAuth = FirebaseAuth.getInstance();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                String inviteCode = Long.toString(ByteBuffer.wrap(UUID.randomUUID().toString().getBytes()).getLong(), Character.MAX_RADIX);
                Map<String, Object> user = new HashMap<>();
                user.put("name", memberName);
                user.put("email", email);
                user.put("phone", phone);
                user.put("gender", designation);
                user.put("inviteCode", inviteCode);
                Log.d("member name", user.toString());
                mAuth.createUserWithEmailAndPassword(email, inviteCode)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Log.d("FireBaseAuth", "User successfully written!");
                                    FirebaseUser userFireBase = mAuth.getCurrentUser();
                                    db.collection("users")
                                            .document(userFireBase.getUid())
                                            .set(user)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Log.d("FireStore", "DocumentSnapshot successfully written!");
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.d("Staff", "Error adding document", e);
                                                }
                                            });
                                    Toast.makeText(NewStaffMember.this, "New User Created", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    // If sign in fails, display a message to the user.
                                    Log.w("AuthFailure", "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(NewStaffMember.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        back = findViewById(R.id.backButtonToolbar);
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                new RedirectToActivity().redirectActivityAfterFinish(NewStaffMember.this, StaffMemberList.class);
            }
        });
    }
}