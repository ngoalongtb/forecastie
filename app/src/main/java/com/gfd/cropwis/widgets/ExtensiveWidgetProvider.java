package com.gfd.cropwis.widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;

import java.text.DateFormat;

import com.gfd.cropwis.AlarmReceiver;
import com.gfd.cropwis.activities.MainActivity;
import com.gfd.cropwis.R;
import com.gfd.cropwis.models.Weather5Day;

public class ExtensiveWidgetProvider extends AbstractWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int widgetId : appWidgetIds) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.extensive_widget);

            setTheme(context, remoteViews);

            Intent intent = new Intent(context, AlarmReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.widgetButtonRefresh, pendingIntent);

            Intent intent2 = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent2 = PendingIntent.getActivity(context, 0, intent2, 0);
            remoteViews.setOnClickPendingIntent(R.id.widgetRoot, pendingIntent2);

            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
            Weather5Day widgetWeather5Day = new Weather5Day();
            if(!sp.getString("lastToday", "").equals("")) {
                widgetWeather5Day = parseWidgetJson(sp.getString("lastToday", ""), context);
            }
            else {
                try {
                    pendingIntent2.send();
                } catch (PendingIntent.CanceledException e) {
                    e.printStackTrace();
                }
                return;
            }

            DateFormat timeFormat = android.text.format.DateFormat.getTimeFormat(context);

            remoteViews.setTextViewText(R.id.widgetCity, widgetWeather5Day.getCity() + ", " + widgetWeather5Day.getCountry());
            remoteViews.setTextViewText(R.id.widgetTemperature, widgetWeather5Day.getTemperature());
            remoteViews.setTextViewText(R.id.widgetDescription, widgetWeather5Day.getDescription());
            remoteViews.setTextViewText(R.id.widgetWind, widgetWeather5Day.getWind());
            remoteViews.setTextViewText(R.id.widgetPressure, widgetWeather5Day.getPressure());
            remoteViews.setTextViewText(R.id.widgetHumidity, context.getString(R.string.humidity) + ": " + widgetWeather5Day.getHumidity() + " %");
            remoteViews.setTextViewText(R.id.widgetSunrise, context.getString(R.string.sunrise) + ": " + timeFormat.format(widgetWeather5Day.getSunrise())); //
            remoteViews.setTextViewText(R.id.widgetSunset, context.getString(R.string.sunset) + ": " + timeFormat.format(widgetWeather5Day.getSunset()));
            remoteViews.setTextViewText(R.id.widgetLastUpdate, widgetWeather5Day.getLastUpdated());
            remoteViews.setImageViewBitmap(R.id.widgetIcon, getWeatherIcon(widgetWeather5Day.getIcon(), context));

            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
        scheduleNextUpdate(context);
    }

}
