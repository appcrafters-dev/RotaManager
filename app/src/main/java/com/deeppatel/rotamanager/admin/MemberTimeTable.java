package com.deeppatel.rotamanager.admin;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.deeppatel.rotamanager.R;
import com.deeppatel.rotamanager.helpers.RedirectToActivity;

public class MemberTimeTable extends AppCompatActivity {
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_time_table);

        back = findViewById(R.id.backButtonToolbar);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new RedirectToActivity().redirectActivityAfterFinish(MemberTimeTable.this, EditStaffMember.class);
            }
        });
    }
}