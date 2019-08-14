package com.gfd.cropwis.hotspotsarea;

import org.json.JSONObject;

public class HotspotArea {
    public static String RS = "rs";
    public static String MS = "ms";

    public static String LEVEL_1 = "name_1";
    public static String LEVEL_2 = "name_2";
    public static String LEVEL_3 = "name_3";
    public static String VALUE = "value";

    private String level1;
    private String level2;
    private String level3;
    private int value;

    public HotspotArea(JSONObject jsonObject) {
        if (jsonObject == null) return;

        if (jsonObject.has(LEVEL_1)) {
            setLevel1(jsonObject.optString(LEVEL_1, ""));
        }
        if (jsonObject.has(LEVEL_2)) {
            setLevel2(jsonObject.optString(LEVEL_2, ""));
        }
        if (jsonObject.has(LEVEL_3)) {
            setLevel3(jsonObject.optString(LEVEL_3, ""));
        }
        if (jsonObject.has(VALUE)) {
            setValue(jsonObject.optInt(VALUE));
        }
    }

    public String getLevel1() {
        return level1;
    }

    public void setLevel1(String level1) {
        this.level1 = level1;
    }

    public String getLevel2() {
        return level2;
    }

    public void setLevel2(String level2) {
        this.level2 = level2;
    }

    public String getLevel3() {
        return level3;
    }

    public void setLevel3(String level3) {
        this.level3 = level3;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
