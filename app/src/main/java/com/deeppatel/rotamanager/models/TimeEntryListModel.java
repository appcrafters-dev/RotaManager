package com.deeppatel.rotamanager.models;

public class TimeEntryListModel {
    public String uid;
    public String date;
    public String day;
    public String name;
    public String time;
    public String month;

    public TimeEntryListModel(String uid, String day, String date, String name, String time,String month){
        this.uid = uid;
        this.date = date;
        this.day = day;
        this.name = name;
        this.time = time;
        this.month = month;
    }

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
}
