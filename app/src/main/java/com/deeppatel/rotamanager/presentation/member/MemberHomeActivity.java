package com.deeppatel.rotamanager.presentation.member;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.deeppatel.rotamanager.R;
import com.deeppatel.rotamanager.presentation.member.MySchedule.MemberTimeEntriesFragment;
import com.deeppatel.rotamanager.presentation.member.TimeChangeRequest.TimeChangeRequestsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;

public class MemberHomeActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_home_page);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        fragmentManager = getSupportFragmentManager();
        loadFragment(new MemberTimeEntriesFragment());
//        dataLoad.makeRequestMember();

        final HashMap<Integer, Fragment> fragments = new HashMap<>();
        fragments.put(R.id.schedule, new MemberTimeEntriesFragment());
        fragments.put(R.id.requests, new TimeChangeRequestsFragment());
        fragments.put(R.id.profile, new ProfileFragment());
        bottomNavigationView.setOnItemSelectedListener(
                item -> {
                    loadFragment(fragments.get(item.getItemId()));
                    item.setChecked(true);
                    return true;
                }
        );
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        int selectedItemId = bottomNavigationView.getSelectedItemId();
        if (selectedItemId == R.id.schedule) {
            finish();
        } else {
            bottomNavigationView.setSelectedItemId(R.id.schedule);
        }
    }

}