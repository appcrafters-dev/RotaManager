package com.deeppatel.rotamanager.member;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.deeppatel.rotamanager.R;
import com.deeppatel.rotamanager.helpers.Navigate;

public class RequestTimeChangeRequest extends AppCompatActivity {
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_time_change_request);

        back = findViewById(R.id.backButtonToolbar);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigate.replace(RequestTimeChangeRequest.this,MemberHomePage.class);
            }
        });
    }
}