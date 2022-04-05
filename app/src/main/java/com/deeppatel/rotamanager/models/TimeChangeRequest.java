package com.deeppatel.rotamanager.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;

import com.deeppatel.rotamanager.helpers.Utils;
import com.deeppatel.rotamanager.helpers.enums.TimeChangeRequestStatus;

import org.joda.time.DateTime;

import java.util.HashMap;


public class TimeChangeRequest extends Model implements Parcelable {
    public static final Creator<TimeChangeRequest> CREATOR = new Creator<TimeChangeRequest>() {
        @Override
        public TimeChangeRequest createFromParcel(Parcel in) {
            return new TimeChangeRequest(in);
        }

        @Override
        public TimeChangeRequest[] newArray(int size) {
            return new TimeChangeRequest[size];
        }
    };
    private String id;
    private TimeChangeRequestStatus status;
    private DateTime from;
    private DateTime to;
    private String reason;
    private TimeEntry timeEntry;

    public TimeChangeRequest(String id, TimeChangeRequestStatus status, DateTime from, DateTime to, String reason,
                             TimeEntry timeEntry) {
        this.id = id;
        this.status = status;
        this.from = from;
        this.to = to;
        this.reason = reason;
        this.timeEntry = timeEntry;
    }

    public TimeChangeRequest() {
    }

    protected TimeChangeRequest(Parcel in) {
        id = in.readString();
        status = TimeChangeRequestStatus.get(in.readString());
        reason = in.readString();
        from = DateTime.parse(in.readString());
        to = DateTime.parse(in.readString());
        timeEntry = in.readParcelable(TimeEntry.class.getClassLoader());
    }

    @Override
    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("status", status.getStatus());
        hashMap.put("reason", reason);
        hashMap.put("from", from.toString());
        hashMap.put("to", to.toString());
        hashMap.put("timeEntry", timeEntry.toHashMapWithId());
        return hashMap;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TimeChangeRequestStatus getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = TimeChangeRequestStatus.get(status);
    }

    public DateTime getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = DateTime.parse(from);
    }

    public DateTime getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = DateTime.parse(to);
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public TimeEntry getTimeEntry() {
        return timeEntry;
    }

    public void setTimeEntry(TimeEntry timeEntry) {
        this.timeEntry = timeEntry;
    }

    public String getFromString() {
        return Utils.dateTimeToTimeString(from);
    }

    public String getToString() {
        return Utils.dateTimeToTimeString(to);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(status.getStatus());
        parcel.writeString(reason);
        parcel.writeString(from.toString());
        parcel.writeString(to.toString());
        parcel.writeParcelable(timeEntry, i);
    }


    @NonNull
    @Override
    public String toString() {
        return "TimeChangeRequest{" +
                "id='" + id + '\'' +
                ", status='" + status + '\'' +
                ", from=" + from +
                ", to=" + to +
                ", reason='" + reason + '\'' +
                ", timeEntry=" + timeEntry +
                '}';
    }
}
