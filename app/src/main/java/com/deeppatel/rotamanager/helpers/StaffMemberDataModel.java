package com.deeppatel.rotamanager.helpers;

public class StaffMemberDataModel{
    public String uid;
    public String name;
    public String email;
    public String photoURI;

    public String getDesignation() {
        return designation;
    }

    public String getGender() {
        return gender;
    }

    public String designation;
    public String phone;
    public String gender;

    public StaffMemberDataModel(String uid, String name, String email, String photoURI, String phone, String designation, String gender){
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.photoURI = photoURI;
        this.phone = phone;
        this.designation = designation;
        this.gender = gender;
    }

    public String getPhotoURI() {
        return photoURI;
    }

    public String getName() {
        return name;
    }

    public String getUid() {
        return uid;
    }

    public String getEmail() {
        return email;
    }
    public String getPhone() { return phone; }
}