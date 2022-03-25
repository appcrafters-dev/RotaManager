package com.deeppatel.rotamanager.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.deeppatel.rotamanager.LoginActivity;
import com.deeppatel.rotamanager.R;
import com.deeppatel.rotamanager.helpers.RedirectToActivity;
import com.deeppatel.rotamanager.helpers.StaffMemberDataAdapter;
import com.deeppatel.rotamanager.helpers.StaffMemberDataModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class StaffMemberList extends AppCompatActivity {
    private ImageView back;
    private FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_member_list);

        StaffMemberDataModel[] myListData = new StaffMemberDataModel[]{
                new StaffMemberDataModel("Email","Name","Email", "https://firebasestorage.googleapis.com/v0/b/inertia-28428.appspot.com/o/images%2F5Hto4ApUgzdZCqyRYXjB3XOTm3Y2%2FprofilePic.jpg?alt=media&token=da713f7a-f7c1-46ac-874d-718cfc6a03cd"),
                new StaffMemberDataModel("Email","Name","Email", "https://firebasestorage.googleapis.com/v0/b/inertia-28428.appspot.com/o/images%2F5Hto4ApUgzdZCqyRYXjB3XOTm3Y2%2FprofilePic.jpg?alt=media&token=da713f7a-f7c1-46ac-874d-718cfc6a03cd"),
                new StaffMemberDataModel("Email","Name","Email", "https://firebasestorage.googleapis.com/v0/b/inertia-28428.appspot.com/o/images%2F5Hto4ApUgzdZCqyRYXjB3XOTm3Y2%2FprofilePic.jpg?alt=media&token=da713f7a-f7c1-46ac-874d-718cfc6a03cd"),
                new StaffMemberDataModel("Email","Name","Email", "https://firebasestorage.googleapis.com/v0/b/inertia-28428.appspot.com/o/images%2F5Hto4ApUgzdZCqyRYXjB3XOTm3Y2%2FprofilePic.jpg?alt=media&token=da713f7a-f7c1-46ac-874d-718cfc6a03cd"),
                new StaffMemberDataModel("Email","Name","Email", "https://firebasestorage.googleapis.com/v0/b/inertia-28428.appspot.com/o/images%2F5Hto4ApUgzdZCqyRYXjB3XOTm3Y2%2FprofilePic.jpg?alt=media&token=da713f7a-f7c1-46ac-874d-718cfc6a03cd"),
                new StaffMemberDataModel("Email","Name","Email", "https://firebasestorage.googleapis.com/v0/b/inertia-28428.appspot.com/o/images%2F5Hto4ApUgzdZCqyRYXjB3XOTm3Y2%2FprofilePic.jpg?alt=media&token=da713f7a-f7c1-46ac-874d-718cfc6a03cd"),
                new StaffMemberDataModel("Email","Name","Email", "https://firebasestorage.googleapis.com/v0/b/inertia-28428.appspot.com/o/images%2F5Hto4ApUgzdZCqyRYXjB3XOTm3Y2%2FprofilePic.jpg?alt=media&token=da713f7a-f7c1-46ac-874d-718cfc6a03cd"),
                new StaffMemberDataModel("Email","Name","Email", "https://firebasestorage.googleapis.com/v0/b/inertia-28428.appspot.com/o/images%2F5Hto4ApUgzdZCqyRYXjB3XOTm3Y2%2FprofilePic.jpg?alt=media&token=da713f7a-f7c1-46ac-874d-718cfc6a03cd"),
                new StaffMemberDataModel("Email","Name","Email", "https://firebasestorage.googleapis.com/v0/b/inertia-28428.appspot.com/o/images%2F5Hto4ApUgzdZCqyRYXjB3XOTm3Y2%2FprofilePic.jpg?alt=media&token=da713f7a-f7c1-46ac-874d-718cfc6a03cd"),
                new StaffMemberDataModel("Email","Name","Email", "https://firebasestorage.googleapis.com/v0/b/inertia-28428.appspot.com/o/images%2F5Hto4ApUgzdZCqyRYXjB3XOTm3Y2%2FprofilePic.jpg?alt=media&token=da713f7a-f7c1-46ac-874d-718cfc6a03cd"),
                new StaffMemberDataModel("Email","Name","Email", "https://firebasestorage.googleapis.com/v0/b/inertia-28428.appspot.com/o/images%2F5Hto4ApUgzdZCqyRYXjB3XOTm3Y2%2FprofilePic.jpg?alt=media&token=da713f7a-f7c1-46ac-874d-718cfc6a03cd"),
                new StaffMemberDataModel("Email","Name","Email", "https://firebasestorage.googleapis.com/v0/b/inertia-28428.appspot.com/o/images%2F5Hto4ApUgzdZCqyRYXjB3XOTm3Y2%2FprofilePic.jpg?alt=media&token=da713f7a-f7c1-46ac-874d-718cfc6a03cd"),
                new StaffMemberDataModel("Email","Name","Email", "https://firebasestorage.googleapis.com/v0/b/inertia-28428.appspot.com/o/images%2F5Hto4ApUgzdZCqyRYXjB3XOTm3Y2%2FprofilePic.jpg?alt=media&token=da713f7a-f7c1-46ac-874d-718cfc6a03cd"),
                new StaffMemberDataModel("Email","Name","Email", "https://firebasestorage.googleapis.com/v0/b/inertia-28428.appspot.com/o/images%2F5Hto4ApUgzdZCqyRYXjB3XOTm3Y2%2FprofilePic.jpg?alt=media&token=da713f7a-f7c1-46ac-874d-718cfc6a03cd"),
                new StaffMemberDataModel("Email","Name","Email", "https://firebasestorage.googleapis.com/v0/b/inertia-28428.appspot.com/o/images%2F5Hto4ApUgzdZCqyRYXjB3XOTm3Y2%2FprofilePic.jpg?alt=media&token=da713f7a-f7c1-46ac-874d-718cfc6a03cd")

        };

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        StaffMemberDataAdapter adapter = new StaffMemberDataAdapter(myListData,StaffMemberList.this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


    back = findViewById(R.id.backButtonToolbar);
    mFab = findViewById(R.id.staff_members_fab);
    mFab.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v){
            new RedirectToActivity().redirectActivityAfterFinish(StaffMemberList.this, NewStaffMember.class);
        }
    });
    back.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View view){
            new RedirectToActivity().redirectActivityAfterFinish(StaffMemberList.this, AdminDashboard.class);
        }
    });
    }
}