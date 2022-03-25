package com.deeppatel.rotamanager.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.deeppatel.rotamanager.R;
import com.deeppatel.rotamanager.helpers.RedirectToActivity;
import com.deeppatel.rotamanager.helpers.StaffMemberDataAdapter;
import com.deeppatel.rotamanager.helpers.StaffMemberDataModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class StaffMemberList extends AppCompatActivity {
    private ImageView back;
    private FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_member_list);
        getData();
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

    private void getData(){
        Log.d("List?", "are we even hereeeeeeeeeeeeeee");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        List<StaffMemberDataModel> staffList = new ArrayList<StaffMemberDataModel>();
        db.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<QueryDocumentSnapshot> list = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        list.add(document);
                        staffList.add(new StaffMemberDataModel(document.getId(), document.get("name").toString(), document.get("email").toString(), "https://firebasestorage.googleapis.com/v0/b/inertia-28428.appspot.com/o/images%2F5Hto4ApUgzdZCqyRYXjB3XOTm3Y2%2FprofilePic.jpg?alt=media&token=da713f7a-f7c1-46ac-874d-718cfc6a03cd", document.get("phone").toString(), document.get("designation").toString(), document.get("gender").toString()));
                    }

                    // Load UI
                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
                    StaffMemberDataAdapter adapter = new StaffMemberDataAdapter(staffList, StaffMemberList.this);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(StaffMemberList.this));
                    recyclerView.setAdapter(adapter);
                    Log.d("FireStore Data", list.toString());
                } else {
                    Log.d("FireStore Data", "Error getting documents: ", task.getException());
                }
            }
        });
    }
}