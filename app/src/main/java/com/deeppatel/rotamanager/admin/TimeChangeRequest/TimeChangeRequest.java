package com.deeppatel.rotamanager.admin.TimeChangeRequest;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deeppatel.rotamanager.R;
import com.deeppatel.rotamanager.helpers.dataLoad;
import com.deeppatel.rotamanager.models.TimeChangeRequestChildModel;
import com.deeppatel.rotamanager.models.TimeChangeRequestParentModel;
import com.deeppatel.rotamanager.helpers.adapters.TimeChangeRequestsAdapters.TimeChangeRequestParentAdapter;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

public class TimeChangeRequest extends AppCompatActivity {
    private ImageView back;
    List<TimeChangeRequestChildModel> timeChangeRequestChildModelList;
    RecyclerView ParentRecyclerViewItem;
    TimeChangeRequestParentAdapter timeChangeRequestParentAdapter;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_change_request);
        back = findViewById(R.id.iv_back_button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ParentRecyclerViewItem = findViewById(R.id.parent_recyclerview);

        LinearLayoutManager layoutManager = new LinearLayoutManager(TimeChangeRequest.this);
        timeChangeRequestParentAdapter = new TimeChangeRequestParentAdapter(ParentItemList(), TimeChangeRequest.this);
        ParentRecyclerViewItem.setAdapter(timeChangeRequestParentAdapter);
        ParentRecyclerViewItem.setLayoutManager(layoutManager);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private List<TimeChangeRequestParentModel> ParentItemList() {
        Dictionary TimeChangeRequestParentModelDict = new Hashtable();

        for (TimeChangeRequestChildModel x : ChildItemList()) {
            String key = x.getDate() + "-" + x.getMonth().toUpperCase();

            List<TimeChangeRequestChildModel> TimeChangeRequestParentModelDictValue = new ArrayList<>();
            if (TimeChangeRequestParentModelDict.get(key) == null) {
                TimeChangeRequestParentModelDictValue.add(x);
                TimeChangeRequestParentModelDict.put(key, TimeChangeRequestParentModelDictValue);
            } else {
                TimeChangeRequestParentModelDictValue = (List<TimeChangeRequestChildModel>) TimeChangeRequestParentModelDict.get(key);
                TimeChangeRequestParentModelDictValue.add(x);
                TimeChangeRequestParentModelDict.put(key, TimeChangeRequestParentModelDictValue);
            }
        }

        List<TimeChangeRequestParentModel> itemList = new ArrayList<>();

        Enumeration<String> e = TimeChangeRequestParentModelDict.keys();
        while (e.hasMoreElements()) {
            String k = e.nextElement();
            TimeChangeRequestParentModel item = new TimeChangeRequestParentModel(k, (List<TimeChangeRequestChildModel>) TimeChangeRequestParentModelDict.get(k));
            itemList.add(item);
        }


        return itemList;
    }

    private List<TimeChangeRequestChildModel> ChildItemList() {
        timeChangeRequestChildModelList = dataLoad.timeChangeRequestChildModelList;
        return timeChangeRequestChildModelList;
    }
}