package com.gfd.cropwis.fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.gfd.cropwis.R;
import com.gfd.cropwis.about.AboutActivity;
import com.gfd.cropwis.cropgrowingseason.CropGrowingSeasonActivity;
import com.gfd.cropwis.hotspotsarea.HotspotsAreaActivity;
import com.gfd.cropwis.activities.MainActivity;
import com.gfd.cropwis.activities.SettingsActivity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NavigationDrawerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NavigationDrawerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NavigationDrawerFragment extends Fragment implements View.OnClickListener {
    private ActionBarDrawerToggle   mDrawerToggle;
    private DrawerLayout            mDrawerLayout;

    private LinearLayout mMenuWeatherForecast;
    private LinearLayout mMenuHotspotArea;
    private LinearLayout mMenuCropGrowingSesson;
    private LinearLayout mMenuSettings;
    private LinearLayout mMenuAbout;

    public NavigationDrawerFragment() {
        // Required empty public constructor
    }

    public static NavigationDrawerFragment newInstance() {
        NavigationDrawerFragment fragment = new NavigationDrawerFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        setUpRecyclerView(view);

        mapping(view);

        // Inflate the layout for this fragment
        return view;
    }

    private void mapping(View view) {
        mMenuWeatherForecast = view.findViewById(R.id.menuWeatherForecast);
        mMenuHotspotArea = view.findViewById(R.id.menuHotspotArea);
        mMenuCropGrowingSesson = view.findViewById(R.id.menuCropGrowingSesson);
        mMenuSettings = view.findViewById(R.id.menuSettings);
        mMenuAbout = view.findViewById(R.id.menuAbout);

        mMenuWeatherForecast.setOnClickListener(this);
        mMenuHotspotArea.setOnClickListener(this);
        mMenuCropGrowingSesson.setOnClickListener(this);
        mMenuSettings.setOnClickListener(this);
        mMenuAbout.setOnClickListener(this);
    }

    private void setUpRecyclerView(View view) {

//        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.drawerList);
//
//        List<NavigationDrawerItem> navigationDrawerItems = new ArrayList<NavigationDrawerItem>();
//        navigationDrawerItems.add(new NavigationDrawerItem("title 1", R.drawable.ic_cloud_black_18dp));
//        navigationDrawerItems.add(new NavigationDrawerItem("title 2", R.drawable.ic_cloud_black_18dp));
//        navigationDrawerItems.add(new NavigationDrawerItem("title 3", R.drawable.ic_cloud_black_18dp));
//        NavigationDrawerAdapter adapter = new NavigationDrawerAdapter(getActivity(), navigationDrawerItems);
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
    public void setUpDrawer(int fragmentId, DrawerLayout drawerLayout, Toolbar toolbar) {
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
//                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
//                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                // Do something of Slide of Drawer
            }
        };

        mDrawerLayout.addDrawerListener(mDrawerToggle);

        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.menuWeatherForecast:
                intent = new Intent(this.getActivity(), MainActivity.class);
                startActivity(intent);
                break;
            case R.id.menuHotspotArea:
                intent = new Intent(this.getActivity(), HotspotsAreaActivity.class);
                startActivity(intent);
                break;
            case R.id.menuCropGrowingSesson:
                intent = new Intent(this.getActivity(), CropGrowingSeasonActivity.class);
                startActivity(intent);
                break;
            case R.id.menuSettings:
                intent = new Intent(this.getActivity(), SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.menuAbout:
                intent = new Intent(this.getActivity(), AboutActivity.class);
                startActivity(intent);
                break;
        }
    }
    private void aboutDialog() {
        new AboutDialogFragment().show(getActivity().getSupportFragmentManager(), null);
    }
}
