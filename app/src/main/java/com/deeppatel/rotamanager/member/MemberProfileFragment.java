package com.deeppatel.rotamanager.member;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.deeppatel.rotamanager.LoginActivity;
import com.deeppatel.rotamanager.R;
import com.deeppatel.rotamanager.helpers.Navigate;
import com.google.firebase.auth.FirebaseAuth;

public class MemberProfileFragment extends Fragment {
    private FirebaseAuth mAuth;
    private Button logout;
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

        mAuth = FirebaseAuth.getInstance();
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Navigate.replace(getActivity(), LoginActivity.class);
            }
        });

        return rootView;
    }
}