package com.deeppatel.rotamanager.admin.ManageStaff;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.deeppatel.rotamanager.R;
import com.deeppatel.rotamanager.member.MySchedule.MemberTimeEntriesFragment;
import com.deeppatel.rotamanager.models.User;

public class MemberTimeEntriesActivity extends AppCompatActivity {
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_time_table);

        Intent intent = getIntent();
        User user = intent.getExtras().getParcelable("user");
        TextView memberNameNavbar = findViewById(R.id.memberNameViewToolbar);
        memberNameNavbar.setText(user.getName());

        fragmentManager = getSupportFragmentManager();

        Fragment fragment = new MemberTimeEntriesFragment();
        Bundle fragmentBundle = new Bundle();
        fragmentBundle.putParcelable("user", user);
        fragment.setArguments(fragmentBundle);
        loadFragment(fragment);

        ImageView back = findViewById(R.id.backButtonToolbar);
        back.setOnClickListener(view -> finish());
    }

    void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}