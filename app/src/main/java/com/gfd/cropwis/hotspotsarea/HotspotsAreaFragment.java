package com.gfd.cropwis.hotspotsarea;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.gfd.cropwis.Constants;
import com.gfd.cropwis.R;
import com.gfd.cropwis.configs.AppConfig;
import com.gfd.cropwis.models.Hotspot;
import com.gfd.cropwis.weekpicker.Week;
import com.gfd.cropwis.weekpicker.WeekPickerDialog;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * @author luantm
 */
public class HotspotsAreaFragment extends Fragment implements View.OnClickListener {
    private ScrollView svHotspotList;
    private LinearLayout llHotspotList;
    private TextView tvDate;
    private DatePickerDialog datePickerDialog;
    private ProgressDialog progressDialog;
    private int selectedYear = 2018;
    private int selectedWeek = 11;
    private List<HotspotArea> hotspotsArea;

    public HotspotsAreaFragment() {
        // Required empty public constructor
    }

    public static HotspotsAreaFragment newInstance() {
        HotspotsAreaFragment fragment = new HotspotsAreaFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.hotspots_area_frag, container, false);
        mapping(view);
        tvDate.setText(String.format("[Week %s, %s]", selectedWeek, selectedYear));

        getHotspotsArea();

        // Inflate the layout for this fragment
        return view;
    }

    private void mapping(View view) {
        svHotspotList = view.findViewById(R.id.svHotspotList);
        llHotspotList = view.findViewById(R.id.llHotspotList);

        tvDate = view.findViewById(R.id.tvDate);
        tvDate.setOnClickListener(this);
    }

    private void displayData() {

        String oldLevel2 = "";
        TreeNode root = TreeNode.root();
        TreeNode parent = null;
        for (HotspotArea hotspotArea:
             hotspotsArea) {
            if (hotspotArea.getLevel2().equals(oldLevel2)) {
                TreeNode child = new TreeNode(hotspotArea).setViewHolder(new HotspotsAreaSubHolder(getActivity()));
                if (parent != null) {
                    parent.addChild(child);
                }
            } else {
                parent = new TreeNode(hotspotArea).setViewHolder(new HotspotsAreaHolder(getActivity()));
                TreeNode child = new TreeNode(hotspotArea).setViewHolder(new HotspotsAreaSubHolder(getActivity()));
                parent.addChild(child);
                root.addChild(parent);
                oldLevel2 = hotspotArea.getLevel2();
            }
        }

        AndroidTreeView treeView = new AndroidTreeView(getActivity(), root);
        llHotspotList.removeAllViews();
        llHotspotList.addView(treeView.getView());
    }

    @Override
    public void onClick(View v) {
        if (v == tvDate) {
            changeDate ();
        }
    }

    private void changeDate () {
        new WeekPickerDialog(getActivity(), Calendar.getInstance(), new WeekPickerDialog.OnWeekSelectListener() {
            @Override
            public void onWeekSelect(Week week) {
                if(week == null){
                    tvDate.setText("[No Selected]");
                }else {
                    tvDate.setText(String.format("[Week %s, %s]", week.getWeekNum(), week.getYear()));
                    selectedWeek = Integer.parseInt(week.getWeekNum());
                    selectedYear = week.getYear();
                    getHotspotsArea();
                }
            }
        }).show();
    }

    private void getHotspotsArea() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle(getText(R.string.sending_request));
        progressDialog.setMessage(getText(R.string.wait_please));
        progressDialog.show();

        String url = String.format(Constants.HOTSPOT_API, selectedYear, selectedWeek);
        AndroidNetworking.get(url)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        try {
                            hotspotsArea = new ArrayList<>();
                            JSONObject jsonObject = response.getJSONObject("ms");

                            for (Iterator<String> it = jsonObject.keys(); it.hasNext(); ) {
                                String key = it.next();
                                hotspotsArea.add(new HotspotArea(jsonObject.getJSONObject(key)));
                            }

                            Collections.sort(hotspotsArea, new Comparator<HotspotArea>() {
                                @Override
                                public int compare(HotspotArea o1, HotspotArea o2) {
                                    if (o1.getLevel2().equals(o2.getLevel2())) {
                                        return o1.getLevel3().compareTo(o2.getLevel3());
                                    } else {
                                        return o1.getLevel2().compareTo(o2.getLevel2());
                                    }
                                }
                            });

                            displayData();

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
}
