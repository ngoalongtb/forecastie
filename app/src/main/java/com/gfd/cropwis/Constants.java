package com.gfd.cropwis;

public class Constants {
    public static final String  DEFAULT_CITY = "Kampala";
    public static final double  DEFAULT_LAT = 0.347596;
    public static final double  DEFAULT_LON = 32.582520;
    public static final String DEFAULT_CITY_ID = "2643743";
    public static final int DEFAULT_ZOOM_LEVEL = 7;

    public static final String HOTSPOT_API = "http://cropinformationsystem.net/ug/service/hotspot?year=%d&week=%d";
    public static final String NEW_HOTSPOT = "https://cropwis.org/ug/service/newhotspot";
    public static final String FORECAST_16_DAYS = "https://api.openweathermap.org/data/2.5/forecast/daily?&cnt=16&appid=974a4736057da52c4c98d7ea2b736f60&units=metric&lat=%s&lon=%s";
    public static final String FORECAST_16_DAYS_KEY = "FORECAST_16_DAYS_KEY";
    public static final String CROP_GROWING_SEASON_API = "http://cropwis.org/ug/service/ssinfo?lat=%s&lng=%s&type=%s";
}
