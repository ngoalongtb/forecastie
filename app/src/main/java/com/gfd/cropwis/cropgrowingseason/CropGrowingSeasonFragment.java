package com.gfd.cropwis.cropgrowingseason;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.gfd.cropwis.Constants;
import com.gfd.cropwis.R;
import com.gfd.cropwis.configs.AppConfig;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * @author luantm
 */
public class CropGrowingSeasonFragment extends Fragment implements View.OnClickListener, OnMapReadyCallback {
    private List<CropGrowingSeasonType> types;
    private Spinner mSpinner;
    private Button mBtnGetInfo;
    private ProgressDialog progressDialog;
    private CardView mcardViewInfo;
    private TextView mtvDistrict;
    private TextView mtvParish;
    private TextView mtvDate;
    private TextView mtvSubCountry;
    private TextView mtvSeason;
    private TextView mtvVillage;
    private TextView mtvSeasonLabel;
    private TextView mtvSeasonDate;
    private GoogleMap mMap;
    private SupportMapFragment mMapFragment;
    private static final int REQUEST_FINE_LOCATION = 11;

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
        return new CropGrowingSeasonFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
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
        mMapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        if (mMapFragment == null) {
            Toast.makeText(getContext(), getString(R.string.cant_init_map), Toast.LENGTH_LONG).show();
            Log.e(AppConfig.LOG_KEY, getString(R.string.cant_init_map));
            return;
        }

        mMapFragment.getMapAsync(this);
    }

    private void mapping(View view) {
        mSpinner = view.findViewById(R.id.spinner);

        mBtnGetInfo = view.findViewById(R.id.btnGetInfo);
        mBtnGetInfo.setOnClickListener(this);

        mtvDistrict = view.findViewById(R.id.tvDistrict);
        mtvParish = view.findViewById(R.id.tvParish);
        mtvDate = view.findViewById(R.id.tvDate);
        mtvSubCountry = view.findViewById(R.id.tvSubCountry);
        mtvSeason = view.findViewById(R.id.tvSeason);
        mtvVillage = view.findViewById(R.id.tvVillage);
        mtvSeasonDate = view.findViewById(R.id.tvSeasonDate);
        mtvSeasonLabel = view.findViewById(R.id.tvSeasonLabel);

        mcardViewInfo = view.findViewById(R.id.cardViewInfo);
        mcardViewInfo.setVisibility(view.GONE);
    }

    @Override
    public void onClick(View view) {
        if (view == mBtnGetInfo) {
            getInfo();
        }
    }

    private void getInfo() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle(getText(R.string.sending_request));
        progressDialog.setMessage(getText(R.string.wait_please));
        progressDialog.show();
        progressDialog.setCancelable(false);

        double lat = mMap.getCameraPosition().target.latitude;
        double lng = mMap.getCameraPosition().target.longitude;
        final String type = ((CropGrowingSeasonType) mSpinner.getSelectedItem()).getValue();
        String url = String.format(Constants.CROP_GROWING_SEASON_API, lat, lng, type);
        AndroidNetworking.get(url)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        progressDialog.dismiss();
                        try {
                            if (response.length() > 0) {
                                CropGrowingSeason cropGrowingSeason = new CropGrowingSeason(response.getJSONObject(0), type);

                                mtvDistrict.setText(cropGrowingSeason.getDistrict());
                                mtvParish.setText(cropGrowingSeason.getParish());
                                mtvDate.setText(cropGrowingSeason.getValue());
                                mtvSubCountry.setText(cropGrowingSeason.getSubCountry());
                                mtvSeason.setText(cropGrowingSeason.getText());
                                mtvVillage.setText(cropGrowingSeason.getVillage());
                                mtvSeasonDate.setText(cropGrowingSeason.getValue());
                                mtvSeasonLabel.setText(cropGrowingSeason.getText());

                                mcardViewInfo.setVisibility(View.VISIBLE);
                            } else {
                                Toast.makeText(getContext(), getString(R.string.no_info), Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            Log.e(AppConfig.LOG_KEY, e.getMessage());
                            Toast.makeText(getActivity(), getText(R.string.an_error_occurred) + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                        Log.i(AppConfig.LOG_KEY, response.toString());
                    }

                    @Override
                    public void onError(ANError anError) {
                        progressDialog.dismiss();
                        Log.e(AppConfig.LOG_KEY, anError.getErrorBody());
                        Toast.makeText(getActivity(), getText(R.string.an_error_occurred) + anError.getErrorBody(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Constants.DEFAULT_LAT, Constants.DEFAULT_LON), Constants.DEFAULT_ZOOM_LEVEL));

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            View locationButton = ((View) mMapFragment.getView().findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
            rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
            rlp.setMargins(0, 160, 30, 0);

            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMapToolbarEnabled(true);
        } else {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 101) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                mMap.getUiSettings().setMyLocationButtonEnabled(true);
                View locationButton = ((View) mMapFragment.getView().findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
                RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
                rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
                rlp.setMargins(0, 200, 30, 0);
                mMap.getUiSettings().setMapToolbarEnabled(true);
                mMap.setMyLocationEnabled(true);
            } else {
                Toast.makeText(getActivity(), "error", Toast.LENGTH_LONG);
            }
        }
    }

    private class SeasonSpinnerAdapter extends ArrayAdapter<CropGrowingSeasonType> {

        public SeasonSpinnerAdapter(Context context, List<CropGrowingSeasonType> types) {
            super(context, 0, types);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
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
        public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
            return initView(position, convertView, parent);
        }
    }
}
