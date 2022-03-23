package com.deeppatel.rotamanager.admin;

import androidx.appcompat.app.AppCompatActivity;
import com.deeppatel.rotamanager.R;
import com.deeppatel.rotamanager.helpers.RedirectToActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class TimeChangeRequest extends AppCompatActivity {
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_change_request);

        back = findViewById(R.id.backButtonToolbar);
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                new RedirectToActivity().redirectActivityAfterFinish(TimeChangeRequest.this, AdminDashboard.class);
            }
        });
    }
}