package com.deeppatel.rotamanager.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;

public class User extends Model {
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel user) {
            return new User(user);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };

    private String uid;
    private String name;
    private String email;
    private String designation;
    private String phone;
    private String gender;
    private String role;
    private String inviteCode;

    public User() {
    }

    public User(Parcel user) {
        this.uid = user.readString();
        this.name = user.readString();
        this.email = user.readString();
        this.phone = user.readString();
        this.designation = user.readString();
        this.gender = user.readString();
        this.role = user.readString();
        this.inviteCode = user.readString();
    }

    @Override
    public String toString() {
        return "User{" +
                "uid='" + uid + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", designation='" + designation + '\'' +
                ", phone='" + phone + '\'' +
                ", gender='" + gender + '\'' +
                ", role='" + role + '\'' +
                ", inviteCode='" + inviteCode + '\'' +
                '}';
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(uid);
        parcel.writeString(name);
        parcel.writeString(email);
        parcel.writeString(phone);
        parcel.writeString(designation);
        parcel.writeString(gender);
        parcel.writeString(role);
        parcel.writeString(inviteCode);
    }

    @Override
    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("name", name);
        hashMap.put("email", email);
        hashMap.put("phone", phone);
        hashMap.put("designation", designation);
        hashMap.put("gender", gender);
        hashMap.put("role", role);
        hashMap.put("inviteCode", inviteCode);
        return hashMap;
    }

    @Override
    public int describeContents() {
        return 0;
    }


    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getUid() {
        return uid;
    }

    public void setId(String id) {
        this.uid = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
