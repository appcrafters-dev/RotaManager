package com.deeppatel.rotamanager.helpers;

public class MemberTimetableModel {
    public String uid;
    public String date;
    public String day;
    public String from;
    public String to;
    public String month;

    public String schedid;

    public String getSchedid() {
        return schedid;
    }

    public void setSchedid(String schedid) {
        this.schedid = schedid;
    }

    public MemberTimetableModel(String uid, String schedid,String day, String date, String from, String to,String month){
        this.uid = uid;
        this.schedid = schedid;
        this.date = date;
        this.day = day;
        this.to = to;
        this.from = from;
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

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getMonth() {
        return month;
    }
}

