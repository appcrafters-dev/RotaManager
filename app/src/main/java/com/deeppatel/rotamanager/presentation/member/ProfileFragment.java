package com.deeppatel.rotamanager.presentation.member;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.deeppatel.rotamanager.LoginActivity;
import com.deeppatel.rotamanager.R;
import com.deeppatel.rotamanager.helpers.Navigate;
import com.deeppatel.rotamanager.helpers.Utils;
import com.deeppatel.rotamanager.models.User;
import com.deeppatel.rotamanager.repositories.AuthRepository.AuthRepository;
import com.deeppatel.rotamanager.repositories.AuthRepository.FirebaseAuthRepository;

public class ProfileFragment extends Fragment {
    private User user;
    private AuthRepository authRepository;

    public ProfileFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_member_profile, container, false);

        Button logoutButton = rootView.findViewById(R.id.member_logout);
        EditText name = rootView.findViewById(R.id.member_name);
        EditText email = rootView.findViewById(R.id.member_email);
        EditText phone = rootView.findViewById(R.id.member_phoneNum);
        EditText designation = rootView.findViewById(R.id.member_designation);
        Button updateButton = rootView.findViewById(R.id.btn_submit);
        TextView nameToolbar = rootView.findViewById(R.id.textViewToolbar);

        authRepository = FirebaseAuthRepository.getInstance();
        user = authRepository.getCurrentUser();

        name.setText(user.getName());
        nameToolbar.setText(user.getName());
        email.setText(user.getEmail());
        phone.setText(user.getPhone());
        designation.setText(user.getDesignation());

        updateButton.setOnClickListener(view -> {
            User updatedUser = new User(
                    user.getUid(),
                    name.getText().toString(),
                    email.getText().toString(),
                    designation.getText().toString(),
                    phone.getText().toString(),
                    user.getGender(),
                    user.getRole(),
                    user.getInviteCode()
            );
            authRepository.updateUser(updatedUser, result -> {
                if (result.getErrorMessage() != null) {
                    Utils.showToastMessage(getContext(), result.getErrorMessage());
                    return;
                }
                Utils.showToastMessage(getContext(), "User updated successfully");

            });
        });

        logoutButton.setOnClickListener(view -> {
            authRepository.logout();
            Navigate.replace(getActivity(), LoginActivity.class);
        });

        return rootView;
    }

}