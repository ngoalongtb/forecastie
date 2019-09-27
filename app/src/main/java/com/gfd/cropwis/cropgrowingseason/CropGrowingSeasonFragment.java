package com.gfd.cropwis.cropgrowingseason;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.gfd.cropwis.Constants;
import com.gfd.cropwis.R;
import com.gfd.cropwis.adapters.RecyclerViewSearchAddressAdapter;
import com.gfd.cropwis.base.BaseFragment;
import com.gfd.cropwis.configs.AppConfig;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.maps.model.TileProvider;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @author luantm
 */
public class CropGrowingSeasonFragment extends BaseFragment implements View.OnClickListener, OnMapReadyCallback {
    private List<CropGrowingSeasonType> types;
    private Spinner mSpinner;
    private Button mBtnGetInfo;
    private ProgressDialog progressDialog;
    private CardView mcardViewInfo;
    private TextView mtvLocation, mtvStartDateSeason1, mtvEndDateSeason1, mtvLengthSeason1, mtvStartDateSeason2, mtvEndDateSeason2, mtvLengthSeason2;
    private GoogleMap mMap;
    private SupportMapFragment mMapFragment;
    private ImageView mivSearch;
    private EditText metSearch;
    private CardView mcvSearchResult;
    private RecyclerView recyclerViewSearchAddress;
    private CropGrowingSeason cropGrowingSeason;

    private ImageView mivOpenChooseLayerPopup;

    private LinearLayout mllChooseLayerPopup;
    private LinearLayout mllChooseNoLayer;
    private LinearLayout mllChooseLayerSeason1;
    private LinearLayout mllChooseLayerSeason2;

    private TileOverlay mtileOverlaySeason1;
    private TileOverlay mtileOverlaySeason2;

    private TextView mtvLabelNoLayer;
    private TextView mtvLabelLayer1;
    private TextView mtvLabelLayer2;

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

    static CropGrowingSeasonFragment newInstance() {
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
//        mSpinner.setAdapter(new SeasonSpinnerAdapter(getActivity(), types));
        recyclerViewSearchAddress.setLayoutManager(new LinearLayoutManager(getActivity()));
        metSearch.setOnEditorActionListener(new EditText.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    searchLocation();
                    return true;
                }
                return false;
            }
        });

        metSearch.setOnClickListener(new EditText.OnClickListener() {
            @Override
            public void onClick(View view) {
                mcvSearchResult.setVisibility(View.GONE);
            }
        });

        // Default all popup is gone
        mllChooseLayerPopup.setVisibility(View.GONE);


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
//        mSpinner = view.findViewById(R.id.spinner);
        mivSearch = view.findViewById(R.id.ivSearch);
        mivSearch.setOnClickListener(this);

        metSearch = view.findViewById(R.id.etSearch);
        mcvSearchResult = view.findViewById(R.id.cvSearchResult);

        mBtnGetInfo = view.findViewById(R.id.btnGetInfo);
        mBtnGetInfo.setOnClickListener(this);

        mtvLocation= view.findViewById(R.id.tvLocation);
        mtvStartDateSeason1 = view.findViewById(R.id.tvStartDateSeason1);
        mtvEndDateSeason1 = view.findViewById(R.id.tvEndDateSeason1);
//        mtvLengthSeason1 = view.findViewById(R.id.tvLengthSeason1);
        mtvStartDateSeason2 = view.findViewById(R.id.tvStartDateSeason2);
        mtvEndDateSeason2 = view.findViewById(R.id.tvEndDateSeason2);
//        mtvLengthSeason2 = view.findViewById(R.id.tvLengthSeason2);

        mcardViewInfo = view.findViewById(R.id.cardViewInfo);
        mcardViewInfo.setVisibility(View.GONE);

        mivOpenChooseLayerPopup = view.findViewById(R.id.ivOpenChooseLayerPopup);
        mivOpenChooseLayerPopup.setOnClickListener(this);

        recyclerViewSearchAddress = view.findViewById(R.id.recyclerViewSearchAddress);

        mllChooseNoLayer = view.findViewById(R.id.llChooseNoLayer);
        mllChooseNoLayer.setOnClickListener(this);

        mllChooseLayerSeason1 = view.findViewById(R.id.llChooseLayerSeason1);
        mllChooseLayerSeason1.setOnClickListener(this);

        mllChooseLayerSeason2 = view.findViewById(R.id.llChooseLayerSeason2);
        mllChooseLayerSeason2.setOnClickListener(this);

        mllChooseLayerPopup = view.findViewById(R.id.llChooseLayerPopup);

        mtvLabelNoLayer = view.findViewById(R.id.tvLabelNoLayer);
        mtvLabelLayer1 = view.findViewById(R.id.tvLabelLayer1);
        mtvLabelLayer2 = view.findViewById(R.id.tvLabelLayer2);
    }

    @Override
    public void onClick(View view) {
        if (view == mBtnGetInfo) {
            getInfo();
        } else if (view == mivSearch) {
            searchLocation();
        } else if (view == mllChooseNoLayer) {
            displayNoLayer();
        } else if (view == mllChooseLayerSeason1) {
            displayLayerSeason1();
        } else if (view == mllChooseLayerSeason2) {
            displayLayerseason2();
        } else if (view == mivOpenChooseLayerPopup) {
            displayChooseLayerPopup();
        }
    }

    private void displayChooseLayerPopup() {
        mllChooseLayerPopup.setVisibility(View.VISIBLE);
    }

    private void removeAllTileLayerOnMap() {
        if (mtileOverlaySeason1 != null) {
            mtileOverlaySeason1.setVisible(false);
        }
        if (mtileOverlaySeason2 != null) {
            mtileOverlaySeason2.setVisible(false);
        }
        mtvLabelNoLayer.setTextColor(getResources().getColor(R.color.text_dark));
        mtvLabelLayer1.setTextColor(getResources().getColor(R.color.text_dark));
        mtvLabelLayer2.setTextColor(getResources().getColor(R.color.text_dark));
    }

    private void displayNoLayer() {
        removeAllTileLayerOnMap();
        mtvLabelNoLayer.setTextColor(getResources().getColor(R.color.material_blue_500));
    }
    // TODO: fix hardcode here
    private void displayLayerSeason1() {
        removeAllTileLayerOnMap();
        mtvLabelLayer1.setTextColor(getResources().getColor(R.color.material_blue_500));

        if (mtileOverlaySeason1 == null) {
            final String wmsUrl =
                    "http://cropwis.org:8080/geoserver/ug/wms" +
                            "?service=WMS" +
                            "&version=1.1.0" +
                            "&request=GetMap" +
                            "&layers=ug:sos1_old" +
                            "&bbox=%f,%f,%f,%f" +
                            "&width=256" +
                            "&height=256" +
                            "&srs=EPSG:900913" +  // NB This is important, other SRS's won't work.
                            "&format=image/png" +
                            "&transparent=true";
            TileProvider wmsTileProvider = TileProviderFactory.getOsgeoWmsTileProvider(wmsUrl);
            mtileOverlaySeason1 = mMap.addTileOverlay(new TileOverlayOptions().tileProvider(wmsTileProvider));
        }

        mtileOverlaySeason1.setVisible(true);
    }

    // TODO: fix hardcode here
    private void displayLayerseason2() {
        removeAllTileLayerOnMap();
        mtvLabelLayer2.setTextColor(getResources().getColor(R.color.material_blue_500));
        if (mtileOverlaySeason2 == null) {
            final String wmsUrl =
                    "http://cropwis.org:8080/geoserver/ug/wms" +
                            "?service=WMS" +
                            "&version=1.1.0" +
                            "&request=GetMap" +
                            "&layers=ug:sos2_old" +
                            "&bbox=%f,%f,%f,%f" +
                            "&width=256" +
                            "&height=256" +
                            "&srs=EPSG:900913" +  // NB This is important, other SRS's won't work.
                            "&format=image/png" +
                            "&transparent=true";
            TileProvider wmsTileProvider = TileProviderFactory.getOsgeoWmsTileProvider(wmsUrl);
            mtileOverlaySeason2 = mMap.addTileOverlay(new TileOverlayOptions().tileProvider(wmsTileProvider));
        }

        mtileOverlaySeason2.setVisible(true);
    }

    private void searchLocation() {
        String location = metSearch.getText().toString() + " Uganda";
        if (location.isEmpty()) {
            Toast.makeText(getActivityNonNull(), getText(R.string.enter_search_location_pls), Toast.LENGTH_LONG).show();
            return;
        }

        mcvSearchResult.setVisibility(View.VISIBLE);

        Geocoder geocoder = new Geocoder(this.getContext());
        try {
            List<Address> addresses = geocoder.getFromLocationName(location, 5);
            recyclerViewSearchAddress.setAdapter(new RecyclerViewSearchAddressAdapter(addresses, getContext(), CropGrowingSeasonFragment.this));
        } catch (IOException e) {
            Toast.makeText(getContext(), getText(R.string.an_error_occurred) + e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e(AppConfig.LOG_KEY, getText(R.string.an_error_occurred) + e.getMessage());
        }
        hideKeyboard(getActivityNonNull());
    }

    public void moveCameraToLatLng(LatLng latLng) {
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        mcvSearchResult.setVisibility(View.GONE);
    }

    private void getInfo() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle(getText(R.string.sending_request));
        progressDialog.setMessage(getText(R.string.wait_please));
        progressDialog.show();
        progressDialog.setCancelable(false);

        double lat = mMap.getCameraPosition().target.latitude;
        double lng = mMap.getCameraPosition().target.longitude;

        cropGrowingSeason = new CropGrowingSeason();

        obtainData(lat,lng,"sos1m");
        obtainData(lat,lng,"eos1m");
//        obtainData(lat,lng,"los1m");
        obtainData(lat,lng,"sos2m");
        obtainData(lat,lng,"eos2m");
//        obtainData(lat,lng,"los2m");
    }

    private void obtainData(double lat, double lng, final String type) {
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

                                JSONObject jsonObject = response.getJSONObject(0);
                                if (jsonObject.has(CropGrowingSeason.PARISH)) {
                                    cropGrowingSeason.setParish(jsonObject.optString(CropGrowingSeason.PARISH));
                                }

                                if (jsonObject.has(CropGrowingSeason.SUB_COUNTRY)) {
                                    cropGrowingSeason.setSubCountry(jsonObject.optString(CropGrowingSeason.SUB_COUNTRY));
                                }

                                if (jsonObject.has(CropGrowingSeason.DISTRICT)) {
                                    cropGrowingSeason.setDistrict(jsonObject.optString(CropGrowingSeason.DISTRICT));
                                }

                                if (jsonObject.has(CropGrowingSeason.VILLAGE)) {
                                    cropGrowingSeason.setVillage(jsonObject.optString(CropGrowingSeason.VILLAGE));
                                }

                                if (jsonObject.has(type)) {

                                    String value = jsonObject.optString(type);
                                    Calendar cal = Calendar.getInstance();
                                    int currentYear = cal.get(Calendar.YEAR);

                                    SimpleDateFormat parser = new SimpleDateFormat("dd MMM yyyy", new Locale("en,EN"));
                                    switch (type) {
                                        case "sos1m":
                                            cropGrowingSeason.setStartSeason1(value);
                                            mtvStartDateSeason1.setText(value);
                                            break;
                                        case "eos1m":
                                            cropGrowingSeason.setEndSeason1(value);
                                            mtvEndDateSeason1.setText(value);
                                            break;
//                                        case "los1m":
//                                            String oldValue = value;
//                                            value += " " + currentYear;
//                                            Date lengthSeason1AsDate = parser.parse(value);
//                                            cal.setTime(lengthSeason1AsDate);
//                                            int lengthSeason1 = cal.get(Calendar.DAY_OF_YEAR);
//                                            cropGrowingSeason.setLengthSeason1(lengthSeason1);
//                                            mtvLengthSeason1.setText(String.valueOf(lengthSeason1 + " " + oldValue));
//                                            break;
                                        case "sos2m":
                                            cropGrowingSeason.setStartSeason2(value);
                                            mtvStartDateSeason2.setText(value);
                                            break;
                                        case "eos2m":
                                            cropGrowingSeason.setEndSeason2(value);
                                            mtvEndDateSeason2.setText(value);
                                            break;
//                                        case "los2m":
//                                            oldValue = value;
//                                            value += " " + currentYear;
//                                            Date lengthSeason2AsDate = parser.parse(value);
//                                            cal.setTime(lengthSeason2AsDate);
//                                            int lengthSeason2 = cal.get(Calendar.DAY_OF_YEAR);
//                                            cropGrowingSeason.setLengthSeason2(lengthSeason2);
//                                            mtvLengthSeason2.setText(String.valueOf(lengthSeason2 + " " + oldValue));
//                                            break;
                                    }
                                }

                                String location = String.format("%s, %s, %s, %s", cropGrowingSeason.getParish(), cropGrowingSeason.getVillage(), cropGrowingSeason.getDistrict(), cropGrowingSeason.getSubCountry());
                                mtvLocation.setText(location);
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
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                hideKeyboard(getActivityNonNull());
                mllChooseLayerPopup.setVisibility(View.GONE);
                mcvSearchResult.setVisibility(View.GONE);
            }
        });
        if (ActivityCompat.checkSelfPermission(getActivityNonNull(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivityNonNull(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            mMap.getUiSettings().setCompassEnabled(true);
            mMap.getUiSettings().setIndoorLevelPickerEnabled(true);
            mMap.getUiSettings().setMapToolbarEnabled(true);
            mMap.getUiSettings().setZoomControlsEnabled(true);

            if (mMapFragment.getView() != null) {
                View locationButton = ((View) mMapFragment.getView().findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
                RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
                rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
                rlp.setMargins(0, 160, 30, 0);
            }


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
            if (ActivityCompat.checkSelfPermission(getActivityNonNull(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(getActivityNonNull(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                mMap.getUiSettings().setMyLocationButtonEnabled(true);

                if (mMapFragment.getView() != null) {
                    View locationButton = ((View) mMapFragment.getView().findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
                    RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
                    rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
                    rlp.setMargins(0, 160, 30, 0);
                    mMap.getUiSettings().setMapToolbarEnabled(true);
                    mMap.setMyLocationEnabled(true);
                }
            } else {
                Toast.makeText(getActivity(), "error", Toast.LENGTH_LONG).show();
            }
        }
    }

    //removed class seasonSpinnerAdapter
}
