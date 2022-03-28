package com.deeppatel.rotamanager.helpers;

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.deeppatel.rotamanager.models.MemberTimeChangeRequestModel;
import com.deeppatel.rotamanager.models.NewTimeChangeRequest;
import com.deeppatel.rotamanager.models.TimeChangeRequestChildModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class dataLoad {
    public dataLoad(){};

    public static List<TimeChangeRequestChildModel> timeChangeRequestChildModelList = new ArrayList<>();

    public static List<MemberTimeChangeRequestModel> memberTimeChangeRequestModel = new ArrayList<>();

    public static void makeRequestMember(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String user = FirebaseAuth.getInstance().getCurrentUser().getUid();
        db.collection("TimeRequest").whereEqualTo("uidUser", user).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            Log.i("are we here?", "value");
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

                                Log.e("AAAAAA",uid + " " + name + " "+day + " "+calenderFrom + " "+calenderTo + " "+ calenderDate + " "+calenderMonth + " " + doc.get("uidSchedule").toString());

                                memberTimeChangeRequestModel.add(new MemberTimeChangeRequestModel(uid, doc.get("status").toString(), name,day,calenderFrom,calenderTo, calenderDate,calenderMonth));
                            }
                            Log.i("Request Member", memberTimeChangeRequestModel.get(0).getUid());
                        }
                    }
                });
    }

    public static void makeRequest(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("TimeRequest").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
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

                                Log.e("AAAAAA",uid + " " + name + " "+day + " "+calenderFrom + " "+calenderTo + " "+ calenderDate + " "+calenderMonth + " " + doc.get("uidSchedule").toString());

                                timeChangeRequestChildModelList.add(new TimeChangeRequestChildModel(uid, name,day,calenderFrom,calenderTo, calenderDate,calenderMonth, doc.get("uidSchedule").toString()));
                            }
                            Log.i("make request", timeChangeRequestChildModelList.get(2).getUid());
                        }
                    }
                });
    }
}
