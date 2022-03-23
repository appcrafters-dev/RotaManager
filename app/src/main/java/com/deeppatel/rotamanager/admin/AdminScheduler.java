package com.deeppatel.rotamanager.admin;

import androidx.appcompat.app.AppCompatActivity;
import com.deeppatel.rotamanager.R;
import com.deeppatel.rotamanager.helpers.RedirectToActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AdminScheduler extends AppCompatActivity {
    private ImageView back;
    private FloatingActionButton mFab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_scheduler);

        back = findViewById(R.id.backButtonToolbar);
        mFab = findViewById(R.id.scheduler_fab);

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                new RedirectToActivity().redirectActivityOnly(AdminScheduler.this, NewTimeEntry.class);
            }
        });
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                new RedirectToActivity().redirectActivityAfterFinish(AdminScheduler.this, AdminDashboard.class);
            }
        });
    }
}