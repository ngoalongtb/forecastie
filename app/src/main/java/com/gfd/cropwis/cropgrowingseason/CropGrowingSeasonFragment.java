package com.gfd.cropwis.cropgrowingseason;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.gfd.cropwis.Constants;
import com.gfd.cropwis.R;
import com.gfd.cropwis.configs.AppConfig;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * @author luantm
 */
public class CropGrowingSeasonFragment extends Fragment implements View.OnClickListener, OnMapReadyCallback {
    private List<CropGrowingSeasonType> types;
    private GoogleMap mMap;
    private Spinner mSpinner;

    public CropGrowingSeasonFragment() {
        // Required empty public constructor
        types = initTypes();
    }

    private List<CropGrowingSeasonType> initTypes() {
        types = new ArrayList<>();

        types.add(new CropGrowingSeasonType("sos1", "Start of Season 1"));
        types.add(new CropGrowingSeasonType("sos2", "Start of Season 2"));
        types.add(new CropGrowingSeasonType("sos1m", "Mean of starting season 1"));
        types.add(new CropGrowingSeasonType("sos2m", "Mean of starting season 2"));
        types.add(new CropGrowingSeasonType("esos1", "End of season 1"));
        types.add(new CropGrowingSeasonType("esos2", "End of season 2"));
        types.add(new CropGrowingSeasonType("esos1m", "Mean of End of season 1"));
        types.add(new CropGrowingSeasonType("esos2m", "Mean of End of season 2"));
        types.add(new CropGrowingSeasonType("los1", "Length of season 1"));
        types.add(new CropGrowingSeasonType("los2", "Length of season 2"));
        types.add(new CropGrowingSeasonType("los1m", "Mean of Length of season 1"));
        types.add(new CropGrowingSeasonType("los2m", "Mean of Length of season 2"));

        return types;
    }

    public static CropGrowingSeasonFragment newInstance() {
        CropGrowingSeasonFragment fragment = new CropGrowingSeasonFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.crop_growing_season_frag, container, false);

        mapping(view);

        initDisplay();

        initMap();

        return view;
    }

    private void initDisplay() {
        mSpinner.setAdapter(new SeasonSpinnerAdapter(getActivity(), types));
    }

    private void initMap() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment == null) {
            Toast.makeText(getContext(), getString(R.string.cant_init_map), Toast.LENGTH_LONG).show();
            Log.e(AppConfig.LOG_KEY, getString(R.string.cant_init_map));
            return;
        }

        mapFragment.getMapAsync(this);
    }

    private void mapping(View view) {
        mSpinner = view.findViewById(R.id.spinner);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Constants.DEFAULT_LAT, Constants.DEFAULT_LON), Constants.DEFAULT_ZOOM_LEVEL));
    }

    private class SeasonSpinnerAdapter extends ArrayAdapter<CropGrowingSeasonType> {

        public SeasonSpinnerAdapter(Context context, List<CropGrowingSeasonType> types) {
            super(context, 0, types);
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return initView(position, convertView, parent);
        }

        private View initView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.crop_growing_season_type_item, parent, false);
            }

            TextView textView = convertView.findViewById(R.id.tvSeason);

            CropGrowingSeasonType type = getItem(position);

            if (type != null) {
                textView.setText(type.getLabel());
            }
            return convertView;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return initView(position, convertView, parent);
        }
    }
}
