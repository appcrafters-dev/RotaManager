package com.deeppatel.rotamanager.helpers;

import android.graphics.drawable.Drawable;
import android.net.Uri;

import com.pchmn.materialchips.model.ChipInterface;

public class ContactChip implements ChipInterface {

    private String id;
//    private Uri avatarUri;
    private String name;
    private String email;

    public ContactChip(String id, String name, String email) {
        this.id = id;
//        this.avatarUri = avatarUri;
        this.name = name;
        this.email = email;
    }
    @Override
    public Object getId() {
        return id;
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
}