package com.deeppatel.rotamanager.helpers;

public class StaffMemberDataModel {
    public String uid;
    public String name;
    public String email;
    public String photoURI;

    public StaffMemberDataModel(String uid, String name, String email, String photoURI){
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.photoURI = photoURI;
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
}