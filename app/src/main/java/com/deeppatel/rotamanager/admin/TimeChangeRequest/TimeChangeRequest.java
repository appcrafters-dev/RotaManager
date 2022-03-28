package com.deeppatel.rotamanager.admin.TimeChangeRequest;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deeppatel.rotamanager.R;
import com.deeppatel.rotamanager.helpers.dataLoad;
import com.deeppatel.rotamanager.models.TimeChangeRequestChildModel;
import com.deeppatel.rotamanager.models.TimeChangeRequestParentModel;
import com.deeppatel.rotamanager.helpers.adapters.TimeChangeRequestsAdapters.TimeChangeRequestParentAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;

public class TimeChangeRequest extends AppCompatActivity {
    private ImageView back;
    private Activity currentActivity;
    List<TimeChangeRequestChildModel> timeChangeRequestChildModelList;
    RecyclerView ParentRecyclerViewItem;
    TimeChangeRequestParentAdapter timeChangeRequestParentAdapter;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_change_request);
        back = findViewById(R.id.backButtonToolbar);
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
        Dictionary idkidk = new Hashtable();

        for (TimeChangeRequestChildModel x : ChildItemList()) {
            String key = x.getDate() + "-" + x.getMonth().toUpperCase();

            List<TimeChangeRequestChildModel> lmao = new ArrayList<>();
            if (idkidk.get(key) == null) {
                lmao.add(x);
                idkidk.put(key, lmao);
            } else {
                lmao = (List<TimeChangeRequestChildModel>) idkidk.get(key);
                lmao.add(x);
                idkidk.put(key, lmao);
            }
        }

        List<TimeChangeRequestParentModel> itemList = new ArrayList<>();

        Enumeration<String> e = idkidk.keys();
        while (e.hasMoreElements()) {
            String k = e.nextElement();
            TimeChangeRequestParentModel item = new TimeChangeRequestParentModel(k, (List<TimeChangeRequestChildModel>) idkidk.get(k));
            itemList.add(item);
        }


        return itemList;
    }

    private List<TimeChangeRequestChildModel> ChildItemList() {
        timeChangeRequestChildModelList = dataLoad.timeChangeRequestChildModelList;
        return timeChangeRequestChildModelList;
    }
}