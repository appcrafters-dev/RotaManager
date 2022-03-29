package com.deeppatel.rotamanager.models;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.pchmn.materialchips.model.ChipInterface;

import java.util.HashMap;

public class TimeEntryUser extends Model implements ChipInterface {
    public static final Parcelable.Creator<TimeEntryUser> CREATOR = new Parcelable.Creator<TimeEntryUser>() {
        @Override
        public TimeEntryUser createFromParcel(Parcel in) {
            return new TimeEntryUser(in);
        }

        @Override
        public TimeEntryUser[] newArray(int size) {
            return new TimeEntryUser[size];
        }
    };
    String uid;
    String name;
    String email;

    public TimeEntryUser() {}

    public TimeEntryUser(String uid, String name, String email) {
        this.uid = uid;
        this.name = name;
        this.email = email;
    }

    protected TimeEntryUser(Parcel in) {
        uid = in.readString();
        name = in.readString();
        email = in.readString();
    }

    public static TimeEntryUser fromUser(User user) {
        return new TimeEntryUser(user.getUid(), user.getName(), user.getEmail());
    }

    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("uid", uid);
        hashMap.put("name", name);
        hashMap.put("email", email);
        return hashMap;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    @Override
    public Object getId() {
        return uid;
    }

    @Override
    public void setId(String id) {
        this.uid = id;
    }

    @Override
    public Uri getAvatarUri() {
        return null;
    }

    @Override
    public Drawable getAvatarDrawable() {
        return null;
    }

    @Override
    public String getLabel() {
        return name;
    }

    @Override
    public String getInfo() {
        return email;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(uid);
        parcel.writeString(name);
        parcel.writeString(email);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return "TimeEntryUser{" +
                "uid='" + uid + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}