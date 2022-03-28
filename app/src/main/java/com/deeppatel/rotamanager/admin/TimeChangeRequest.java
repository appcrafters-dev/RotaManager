package com.deeppatel.rotamanager.admin;

import static com.pchmn.materialchips.R2.attr.layoutManager;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deeppatel.rotamanager.R;
import com.deeppatel.rotamanager.helpers.TimeChangeRequestChildModel;
import com.deeppatel.rotamanager.helpers.TimeChangeRequestParentModel;
import com.deeppatel.rotamanager.helpers.TimeChangeRequestParentAdapter;
import com.deeppatel.rotamanager.member.RequestTimeChangeRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

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
    RecyclerView ParentRecyclerViewItem;
    LinearLayoutManager layoutManager;
    TimeChangeRequestParentAdapter timeChangeRequestParentAdapter;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_change_request);
        back = findViewById(R.id.backButtonToolbar);
        ParentRecyclerViewItem = findViewById(R.id.parent_recyclerview);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        layoutManager = new LinearLayoutManager(TimeChangeRequest.this);
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
        List<TimeChangeRequestChildModel> timeChangeRequestChildModelList = new ArrayList<>();



////
//
//        timeChangeRequestChildModelList.add(new TimeChangeRequestChildModel("Tue", "John Doe","Tue", "9:30 AM ","1:00 PM", "19 ","march"));
//        timeChangeRequestChildModelList.add(new TimeChangeRequestChildModel("Tue", "John Doe","Tue", "9:30 AM ","1:00 PM", "21 ","march"));
//        timeChangeRequestChildModelList.add(new TimeChangeRequestChildModel("Tue", "John Doe","Tue", "9:30 AM ","1:00 PM", "21 ","march"));
        timeChangeRequestChildModelList.add(new TimeChangeRequestChildModel("Tue", "John Doe","Tue", "9:30 AM ","1:00 PM", "17 ","march"));
//        timeChangeRequestChildModelList.add(new TimeChangeRequestChildModel("Tue", "John Doe","Tue", "9:30 AM ","1:00 PM", "17 ","march"));
        print("lmao");
//        List<TimeChangeRequestChildModel> list = new list(timeChangeRequestChildModelList);
        Log.e("!!!!!!!!!!",timeChangeRequestChildModelList.toString());


        return timeChangeRequestChildModelList;
    }

    public void print(String st){
        Log.e("imp", st);
    }

    public List<TimeChangeRequestChildModel> list(List<TimeChangeRequestChildModel> timeChangeRequestChildModelList){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("TimeRequest").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for(QueryDocumentSnapshot doc : task.getResult()){
                    Timestamp from, to;
                    from = (Timestamp) doc.get("from");
                    to = (Timestamp) doc.get("to");
                    Date x = from.toDate();
                    Date y = to.toDate();
                    Calendar cal = Calendar.getInstance(Locale.US);
                    Calendar cal2 = Calendar.getInstance(Locale.US);
                    cal.setTime(x);
                    cal2.setTime(y);

                    String name = doc.getString("name");
                    String uid = doc.getString("uidUser");

                    String day = new SimpleDateFormat("EE").format(x);
                    String calenderDate = String.valueOf(cal.get(Calendar.DATE));
                    int calenderFromHour = cal.get(Calendar.HOUR_OF_DAY);
                    int calenderFromMinute = cal.get(Calendar.MINUTE);
                    int calenderToHour = cal2.get(Calendar.HOUR_OF_DAY);
                    int calenderToMinute = cal2.get(Calendar.MINUTE);
                    String calenderMonth = Month.of(cal2.MONTH + 1).name();

                    boolean isPM = (calenderFromHour >= 12);
                    String calenderFrom = String.format("%02d:%02d %s", (calenderFromHour == 12 || calenderFromHour == 0) ? 12 : calenderFromHour % 12, calenderFromMinute, isPM ? "PM" : "AM");
                    boolean isPM2 = (calenderToHour >= 12);
                    String calenderTo = String.format("%02d:%02d %s", (calenderToHour == 12 || calenderToHour == 0) ? 12 : calenderToHour % 12, calenderToMinute, isPM2 ? "PM" : "AM");

                    Log.e("AAAAAA",uid+ name+day+calenderFrom+calenderTo+ calenderDate+calenderMonth);

                    timeChangeRequestChildModelList.add(new TimeChangeRequestChildModel(uid, name,day,calenderFrom,calenderTo, calenderDate,calenderMonth));
                }
                print("lmao");
            }
        });

        return timeChangeRequestChildModelList;
    }
}