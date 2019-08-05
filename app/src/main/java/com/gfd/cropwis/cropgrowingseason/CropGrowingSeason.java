package com.gfd.cropwis.cropgrowingseason;

import org.json.JSONObject;

public class CropGrowingSeason {
    public static String PARISH = "parish";
    public static String SUB_COUNTRY = "subcounty";
    public static String DISTRICT = "district";
    public static String TEXT = "text";
    public static String VILLAGE = "village";

    private String parish;
    private String subCountry;
    private String district;
    private String text;
    private String village;
    private String value;

    public CropGrowingSeason() {

    }

    public CropGrowingSeason(JSONObject jsonObject, final String key) {
        if (jsonObject == null) return;

        if (jsonObject.has(PARISH)) {
            parish = jsonObject.optString(PARISH);
        }

        if (jsonObject.has(SUB_COUNTRY)) {
            subCountry = jsonObject.optString(SUB_COUNTRY);
        }

        if (jsonObject.has(DISTRICT)) {
            district = jsonObject.optString(DISTRICT);
        }

        if (jsonObject.has(TEXT)) {
            text = jsonObject.optString(TEXT);
        }

        if (jsonObject.has(VILLAGE)) {
            village = jsonObject.optString(VILLAGE);
        }

        if (jsonObject.has(key)) {
            value = jsonObject.optString(key);
        }
    }

    public String getParish() {
        return parish;
    }

    public void setParish(String parish) {
        this.parish = parish;
    }

    public String getSubCountry() {
        return subCountry;
    }

    public void setSubCountry(String subCountry) {
        this.subCountry = subCountry;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
