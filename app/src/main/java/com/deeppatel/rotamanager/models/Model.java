package com.deeppatel.rotamanager.models;

import android.os.Parcelable;

import java.util.HashMap;

public abstract class Model implements Parcelable {
    public abstract void setId(String id);
    public abstract HashMap<String, Object> toHashMap();
}
