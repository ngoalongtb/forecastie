package com.gfd.cropwis.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import java.util.ArrayList;

import com.gfd.cropwis.R;
import com.gfd.cropwis.models.Weather5Day;

public class LocationsRecyclerAdapter extends RecyclerView.Adapter<LocationsRecyclerAdapter.LocationsViewHolder> {
    private LayoutInflater inflater;
    private ItemClickListener itemClickListener;
    private Context context;
    private ArrayList<Weather5Day> weather5DayArrayList;
    private boolean darkTheme;
    private boolean blackTheme;

    public LocationsRecyclerAdapter(Context context, ArrayList<Weather5Day> weather5DayArrayList, boolean darkTheme, boolean blackTheme) {
        this.context = context;
        this.weather5DayArrayList = weather5DayArrayList;
        this.darkTheme = darkTheme;
        this.blackTheme = blackTheme;

        inflater = LayoutInflater.from(context);
    }

    @Override
    public LocationsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LocationsViewHolder(inflater.inflate(R.layout.list_location_row, parent, false));
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onBindViewHolder(LocationsViewHolder holder, int position) {
        Typeface weatherFont = Typeface.createFromAsset(context.getAssets(), "fonts/weather.ttf");
        Weather5Day weather5Day = weather5DayArrayList.get(position);

        holder.cityTextView.setText(String.format("%s, %s", weather5Day.getCity(), weather5Day.getCountry()));
        holder.temperatureTextView.setText(weather5Day.getTemperature());
        holder.descriptionTextView.setText(weather5Day.getDescription());
        holder.iconTextView.setText(weather5Day.getIcon());
        holder.iconTextView.setTypeface(weatherFont);

        holder.webView.getSettings().setJavaScriptEnabled(true);
        holder.webView.loadUrl("file:///android_asset/map.html?lat=" + weather5Day.getLat()+ "&lon=" + weather5Day.getLon() + "&appid=" + "notneeded&displayPin=true");

        if (darkTheme || blackTheme) {
            holder.cityTextView.setTextColor(Color.WHITE);
            holder.temperatureTextView.setTextColor(Color.WHITE);
            holder.descriptionTextView.setTextColor(Color.WHITE);
            holder.iconTextView.setTextColor(Color.WHITE);
        }

        if (darkTheme) {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#2e3c43"));
        }

        if (blackTheme) {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#2f2f2f"));
        }
    }

    @Override
    public int getItemCount() {
        return weather5DayArrayList.size();
    }

    class LocationsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView cityTextView;
        private TextView temperatureTextView;
        private TextView descriptionTextView;
        private TextView iconTextView;
        private WebView webView;
        private CardView cardView;

        LocationsViewHolder(View itemView) {
            super(itemView);

            cityTextView = itemView.findViewById(R.id.rowCityTextView);
            temperatureTextView = itemView.findViewById(R.id.rowTemperatureTextView);
            descriptionTextView = itemView.findViewById(R.id.rowDescriptionTextView);
            iconTextView = itemView.findViewById(R.id.rowIconTextView);
            webView = itemView.findViewById(R.id.webView2);
            cardView = itemView.findViewById(R.id.rowCardView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (itemClickListener != null) {
                itemClickListener.onItemClickListener(view, getAdapterPosition());
            }
        }
    }

    public Weather5Day getItem(int position) {
        return weather5DayArrayList.get(position);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClickListener(View view, int position);
    }

}
