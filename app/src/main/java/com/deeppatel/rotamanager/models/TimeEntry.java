package com.deeppatel.rotamanager.models;

import android.os.Parcel;

import com.deeppatel.rotamanager.helpers.Utils;

import org.joda.time.DateTime;

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
    ChildUser user;
    DateTime date;
    DateTime from;
    DateTime to;

    protected TimeEntry(Parcel in) {
        id = in.readString();
        user = in.readParcelable(ChildUser.class.getClassLoader());
        date = DateTime.parse(in.readString());
        from = DateTime.parse(in.readString());
        to = DateTime.parse(in.readString());
    }

    public TimeEntry(String id, ChildUser user, DateTime date, DateTime from, DateTime to) {
        this.id = id;
        this.user = user;
        this.date = date;
        this.from = from;
        this.to = to;
    }

    public TimeEntry() {
    }

    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public ChildUser getUser() {
        return user;
    }

    public void setUser(ChildUser user) {
        this.user = user;
    }

    public DateTime getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = DateTime.parse(from);
    }

    public String getFromString() {
        return Utils.dateTimeToTimeString(from);
    }

    public String getToString() {
        return Utils.dateTimeToTimeString(to);
    }

    public DateTime getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = DateTime.parse(to);
    }

    @Override
    public HashMap<String, Object> toHashMap() {
        return new HashMap<String, Object>(){{
            put("user", user.toHashMap());
            put("date", date.toString());
            put("from", from.toString());
            put("to", to.toString());
        }};
    }

    public HashMap<String, Object> toHashMapWithId() {
        return new HashMap<String, Object>(toHashMap()){{
            put("id", id);
        }};
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeParcelable(user, i);
        parcel.writeString(date.toString());
        parcel.writeString(from.toString());
        parcel.writeString(to.toString());
    }

    public DateTime getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = DateTime.parse(date);
    }

    public String getDateString() {
        return Utils.dateTimeToDateString(date);
    }

    @Override
    public String toString() {
        return "TimeEntry{" +
                "id='" + id + '\'' +
                ", user=" + user.toString() +
                ", date=" + date +
                ", from=" + from +
                ", to=" + to +
                '}';
    }
}
