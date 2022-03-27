package com.deeppatel.rotamanager.models;

import android.os.Parcel;
import android.os.Parcelable;

public class TimeEntry extends Model implements Parcelable {
    String uid;
    String date;
    String day;
    String name;
    String time;
    String month;

    public TimeEntry(String uid, String day, String date, String name, String time,String month){
        this.uid = uid;
        this.date = date;
        this.day = day;
        this.name = name;
        this.time = time;
        this.month = month;
    }

    protected TimeEntry(Parcel in) {
        uid = in.readString();
        date = in.readString();
        day = in.readString();
        name = in.readString();
        time = in.readString();
        month = in.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(uid);
        parcel.writeString(date);
        parcel.writeString(day);
        parcel.writeString(name);
        parcel.writeString(time);
        parcel.writeString(month);
    }

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

    public String getUid() {
        return uid;
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

    @Override
    public void setId(String id) {
        uid = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
