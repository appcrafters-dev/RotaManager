package com.deeppatel.rotamanager.helpers;

public class TimeChangeRequestChildModel {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

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

    public TimeChangeRequestChildModel(String uid,String name,String day,String from,String to, String date, String month) {
        this.name = name;
        this.uid = uid;
        this.from = from;
        this.to = to;
        this.day = day;
        this.date = date;
        this.month = month;
    }
}
