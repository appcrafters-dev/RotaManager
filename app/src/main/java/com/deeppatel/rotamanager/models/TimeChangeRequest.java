package com.deeppatel.rotamanager.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.Timestamp;

import java.util.HashMap;
import java.util.Map;


public class TimeChangeRequest {

    public String getUidUser() {
        return uidUser;
    }

    public void setUidUser(String uidUser) {
        this.uidUser = uidUser;
    }

    public String getUidSchedule() {
        return uidSchedule;
    }

    public void setUidSchedule(String uidSchedule) {
        this.uidSchedule = uidSchedule;
    }

    public Timestamp getFrom() {
        return from;
    }

    public void setFrom(Timestamp from) {
        this.from = from;
    }

    public Timestamp getTo() {
        return to;
    }

    public void setTo(Timestamp to) {
        this.to = to;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public HashMap<String, Object> toHashMap(){
        HashMap <String, Object> hashMap = new HashMap<>();
        hashMap.put("uidUser", uidUser);
        hashMap.put("uidSchedule", uidSchedule);
        hashMap.put("uidName", name);
        hashMap.put("from", from);
        hashMap.put("to", to);
        hashMap.put("reason", reason);
        hashMap.put("status", status);
        return hashMap;
    }

    private String uidUser;

    public String getUidName() {
        return name;
    }

    public void setUidName(String uidName) {
        this.name = name;
    }

    private String name;
    private String uidSchedule;
    private Timestamp from;
    private Timestamp to;
    private String reason;
    private String status;

    public TimeChangeRequest(String uidUser, String name , String uidSchedule, Timestamp from, Timestamp to, String status, String reason){
        this.uidUser = uidUser;
        this.name = name;
        this.uidSchedule = uidSchedule;
        this.from = from;
        this.to = to;
        this.reason = reason;
        this.status = status;
    }
}