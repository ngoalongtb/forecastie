package com.gfd.cropwis.cropgrowingseason;

import org.json.JSONObject;

public class CropGrowingSeason {
    public static String PARISH = "parish";
    public static String SUB_COUNTRY = "subcounty";
    public static String DISTRICT = "district";
    public static String VILLAGE = "village";

    private String parish;
    private String subCountry;
    private String district;
    private String village;
    private String startSeason1;
    private String endSeason1;
    private int lengthSeason1;
    private String startSeason2;
    private String endSeason2;
    private int lengthSeason2;

    public CropGrowingSeason() {

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

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getStartSeason1() {
        return startSeason1;
    }

    public void setStartSeason1(String startSeason1) {
        this.startSeason1 = startSeason1;
    }

    public String getEndSeason1() {
        return endSeason1;
    }

    public void setEndSeason1(String endSeason1) {
        this.endSeason1 = endSeason1;
    }

    public String getStartSeason2() {
        return startSeason2;
    }

    public void setStartSeason2(String startSeason2) {
        this.startSeason2 = startSeason2;
    }

    public String getEndSeason2() {
        return endSeason2;
    }

    public void setEndSeason2(String endSeason2) {
        this.endSeason2 = endSeason2;
    }

    public int getLengthSeason1() {
        return lengthSeason1;
    }

    public void setLengthSeason1(int lengthSeason1) {
        this.lengthSeason1 = lengthSeason1;
    }

    public int getLengthSeason2() {
        return lengthSeason2;
    }

    public void setLengthSeason2(int lengthSeason2) {
        this.lengthSeason2 = lengthSeason2;
    }
}
