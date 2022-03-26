package com.deeppatel.rotamanager.helpers;

public class TimeChangeRequestChildModel {
    private String name,time,day,date,month;

    public TimeChangeRequestChildModel(String day, String date, String month, String name, String time) {
        this.name = name;
        this.time = time;
        this.day = day;
        this.date = date;
        this.month = month;
    }

    public String getName() { return name; }
    public String getTime() { return time; }
    public String getDay() { return day; }
    public String getDate() { return date; }
    public String getMonth() { return month; }

}
