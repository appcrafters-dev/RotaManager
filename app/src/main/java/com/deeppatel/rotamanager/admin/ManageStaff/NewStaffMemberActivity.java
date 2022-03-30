package com.deeppatel.rotamanager.admin.ManageStaff;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.deeppatel.rotamanager.R;
import com.deeppatel.rotamanager.helpers.Utils;
import com.deeppatel.rotamanager.models.RepositoryResult;
import com.deeppatel.rotamanager.models.User;
import com.deeppatel.rotamanager.repositories.AuthRepository.AuthRepository;
import com.deeppatel.rotamanager.repositories.AuthRepository.FirebaseAuthRepository;
import com.deeppatel.rotamanager.repositories.OnRepositoryTaskCompleteListener;

import java.nio.ByteBuffer;
import java.util.UUID;


public class NewStaffMemberActivity extends AppCompatActivity {
    Button submit;
    ImageView back;

    EditText memberNameView, emailView, phoneView, designationView;
    RadioGroup genderView;
    RadioButton genderButton;

    AuthRepository authRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_staff_member);

        memberNameView = findViewById(R.id.member_name);
        emailView = findViewById(R.id.member_email);
        phoneView = findViewById(R.id.member_phoneNum);
        designationView = findViewById(R.id.member_designation);
        genderView = findViewById(R.id.radioGroup);
        submit = findViewById(R.id.member_submit);
        genderButton = findViewById(genderView.getCheckedRadioButtonId());


        authRepository = FirebaseAuthRepository.getInstance();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: validate Form
                User member = new User();
                member.setName(memberNameView.getText().toString());
                member.setEmail(emailView.getText().toString());
                member.setPhone(phoneView.getText().toString());
                member.setDesignation(designationView.getText().toString());
                member.setGender(genderButton.getText().toString());
                member.setRole("staff");
                member.setInviteCode(Long.toString(ByteBuffer.wrap(UUID.randomUUID().toString().getBytes()).getLong(), Character.MAX_RADIX));

                authRepository.createNewUser(member, new OnRepositoryTaskCompleteListener<User>() {
                    @Override
                    public void onComplete(@NonNull RepositoryResult<User> result) {
                        if (result.getErrorMessage() != null){
                            Utils.showToastMessage(NewStaffMemberActivity.this, result.getErrorMessage());
                            return;
                        }
                        Utils.showToastMessage(NewStaffMemberActivity.this, "Added new staff member successfully");
                        finish();
                    }
                });
            }
        });

        back = findViewById(R.id.iv_back_button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}