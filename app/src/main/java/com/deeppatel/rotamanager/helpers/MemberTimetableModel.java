package com.deeppatel.rotamanager.helpers;

public class MemberTimetableModel {
    public String uid;
    public String date;
    public String day;
    public String from;
    public String to;

    public MemberTimetableModel(String uid, String day, String date, String from, String to){
        this.uid = uid;
        this.date = date;
        this.day = day;
        this.to = to;
        this.from = from;
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
}

