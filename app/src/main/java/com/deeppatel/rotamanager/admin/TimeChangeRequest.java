package com.deeppatel.rotamanager.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deeppatel.rotamanager.R;
import com.deeppatel.rotamanager.helpers.TimeChangeRequestChildModel;
import com.deeppatel.rotamanager.helpers.TimeChangeRequestParentModel;
import com.deeppatel.rotamanager.helpers.TimeChangeRequestParentAdapter;
import com.deeppatel.rotamanager.helpers.RedirectToActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class TimeChangeRequest extends AppCompatActivity {
    private ImageView back;
    private Activity currentActivity;

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

        RecyclerView ParentRecyclerViewItem = findViewById(R.id.parent_recyclerview);

        LinearLayoutManager layoutManager = new LinearLayoutManager(TimeChangeRequest.this);
        TimeChangeRequestParentAdapter timeChangeRequestParentAdapter = new TimeChangeRequestParentAdapter(ParentItemList() , TimeChangeRequest.this);

        ParentRecyclerViewItem.setAdapter(timeChangeRequestParentAdapter);
        ParentRecyclerViewItem.setLayoutManager(layoutManager);
    }

    private List<TimeChangeRequestParentModel> ParentItemList()
    {
        List<TimeChangeRequestParentModel> itemList = new ArrayList<>();
        TimeChangeRequestParentModel item = new TimeChangeRequestParentModel("17th March 2022", ChildItemList());
        itemList.add(item);
        TimeChangeRequestParentModel item1 = new TimeChangeRequestParentModel("17th March 2022", ChildItemList());
        itemList.add(item1);
        TimeChangeRequestParentModel item2 = new TimeChangeRequestParentModel("17th March 2022", ChildItemList());
        itemList.add(item2);
        TimeChangeRequestParentModel item3 = new TimeChangeRequestParentModel("17th March 2022", ChildItemList());
        itemList.add(item3);

        return itemList;
    }

    private List<TimeChangeRequestChildModel> ChildItemList()
    {
        List<TimeChangeRequestChildModel> timeChangeRequestChildModelList = new ArrayList<>();

        timeChangeRequestChildModelList.add(new TimeChangeRequestChildModel("Tue","12","march","John Doe","9:30 AM to 1:00 PM"));
        timeChangeRequestChildModelList.add(new TimeChangeRequestChildModel("Tue","12","march","John Doe","9:30 AM to 1:00 PM"));

        return timeChangeRequestChildModelList;
    }
}