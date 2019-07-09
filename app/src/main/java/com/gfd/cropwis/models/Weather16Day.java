package com.gfd.cropwis.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Weather16Day {
    private String city;
    private String country;
    private Date date;
    private String temperatureDay;
    private String temperatureMin;
    private String temperatureMax;
    private String temperatureNight;
    private String temperatureEve;
    private String temperatureMorn;
    private String pressure;
    private String humidity;


    private String rain;
    private String id;
    private String icon;
    private String description;


    public Date getDate(){
        return this.date;
    }

    public void setDate(String dateString) {
        try {
            setDate(new Date(Long.parseLong(dateString) * 1000));
        }
        catch (Exception e) {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            try {
                setDate(inputFormat.parse(dateString));
            }
            catch (ParseException e2) {
                setDate(new Date()); // make the error somewhat obvious
                e2.printStackTrace();
            }
        }
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
