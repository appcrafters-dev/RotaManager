package com.deeppatel.rotamanager.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;

public class TimeEntry extends Model {
    public static final Creator<TimeEntry> CREATOR = new Creator<TimeEntry>() {
        @Override
        public TimeEntry createFromParcel(Parcel in) {
            return new TimeEntry(in);
        }

        @Override
        public TimeEntry[] newArray(int size) {
            return new TimeEntry[size];
        }
    };
    String id;
    String date;
    String day;
    String name;
    String time;
    String month;

    public TimeEntry(String id, String day, String date, String name, String time, String month) {
        this.id = id;
        this.date = date;
        this.day = day;
        this.name = name;
        this.time = time;
        this.month = month;
    }

    protected TimeEntry(Parcel in) {
        id = in.readString();
        date = in.readString();
        day = in.readString();
        name = in.readString();
        time = in.readString();
        month = in.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(date);
        parcel.writeString(day);
        parcel.writeString(name);
        parcel.writeString(time);
        parcel.writeString(month);
    }

    @Override
    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("date",date);
        hashMap.put("day",day);
        hashMap.put("name",name);
        hashMap.put("time",time);
        hashMap.put("month",month);
        return  hashMap;
    }

    public String getDate() {
        return date;
    }

    public String getDay() {
        return day;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public String getMonth() {
        return month;
    }

    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
