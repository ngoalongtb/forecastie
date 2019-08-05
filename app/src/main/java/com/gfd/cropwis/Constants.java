package com.gfd.cropwis;

public class Constants {
    public static final String  DEFAULT_CITY = "Kampala";
    public static final double  DEFAULT_LAT = 0.347596;
    public static final double  DEFAULT_LON = 32.582520;
    public static final String DEFAULT_CITY_ID = "2643743";
    public static final int DEFAULT_ZOOM_LEVEL = 7;

    public static final String HOTSPOT_API = "http://cropinformationsystem.net/ug/service/hotspot?year=%d&week=%d";
    public static final String NEW_HOTSPOT = "http://cropwis.org/ug/service/newhotspot";
    public static final String FORECAST_16_DAYS = "http://cropwis.org/ug/service/openweather?lat=%s&lng=%s";
    public static final String FORECAST_16_DAYS_KEY = "FORECAST_16_DAYS_KEY";
    public static final String CROP_GROWING_SEASON_API = "http://cropwis.org/ug/service/ssinfo?lat=%s&lng=%s&type=%s";
}
