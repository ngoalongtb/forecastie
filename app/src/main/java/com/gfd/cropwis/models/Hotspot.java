package com.gfd.cropwis.models;

import com.gfd.cropwis.configs.AppConstant;

import org.json.JSONObject;

import java.io.Serializable;

public class Hotspot implements Serializable {
    public static String NAME_2 = "name_2";
    public static String NAME_3 = "name_3";
    public static String VALUE = "value";
    public static String ID_3 =  "id_3";

    private String name2;
    private String name3;
    private int value;
    private String id3;

    public Hotspot() {

    }

    public Hotspot(JSONObject jsonObject) {
        if (jsonObject == null) return;

        if (jsonObject.has(NAME_2)) {
            setName2(jsonObject.optString(NAME_2, AppConstant.DEFAULT_EMPTY_STRING));
        }

        if (jsonObject.has(NAME_3)) {
            setName3(jsonObject.optString(NAME_3, AppConstant.DEFAULT_EMPTY_STRING));
        }

        if (jsonObject.has(ID_3)) {
            setId3(jsonObject.optString(ID_3, AppConstant.DEFAULT_EMPTY_STRING));
        }

        if (jsonObject.has(VALUE)) {
            setValue(jsonObject.optInt(VALUE));
        }
    }


    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getName3() {
        return name3;
    }

    public void setName3(String name3) {
        this.name3 = name3;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getId3() {
        return id3;
    }

    public void setId3(String id3) {
        this.id3 = id3;
    }
}
