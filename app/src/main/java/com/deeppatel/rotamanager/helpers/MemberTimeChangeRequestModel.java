package com.deeppatel.rotamanager.helpers;

public class MemberTimeChangeRequestModel {
    public String uid;
    public String date;
    public String day;
    public String status;
    public String time;

    public MemberTimeChangeRequestModel(String uid, String day, String status, String date, String time){
        this.uid = uid;
        this.date = date;
        this.day = day;
        this.status = status;
        this.time = time;
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

    public String getStatus() {
        return status;
    }

    public String getTime() {
        return time;
    }
}
