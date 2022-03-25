package com.deeppatel.rotamanager.members;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;

import com.deeppatel.rotamanager.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MemberHomePage extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    private static Context mContext;

    public static FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_home_page);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        fragmentManager = getSupportFragmentManager();
        loadFragment(new MemberScheduleFragment());
        mContext = this;

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment fragment;
                        switch (item.getItemId()) {
                            case R.id.schedule:
                                item.setChecked(true);
                                fragment = new MemberScheduleFragment();
                                loadFragment(fragment);
                                break;
                            case R.id.requests:
                                item.setChecked(true);
                                fragment = new MemberRequestsFragment();
                                loadFragment(fragment);
                                break;
                            case R.id.profile:
                                item.setChecked(true);
                                fragment = new MemberProfileFragment();
                                loadFragment(fragment);
                                break;
                        }
                        return true;
                    }
                });

    }

    static public void loadFragment(Fragment fragment) {
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