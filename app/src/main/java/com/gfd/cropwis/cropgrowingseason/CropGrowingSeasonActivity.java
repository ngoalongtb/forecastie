package com.gfd.cropwis.cropgrowingseason;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.gfd.cropwis.R;
import com.gfd.cropwis.activities.BaseActivity;
import com.gfd.cropwis.fragments.NavigationDrawerFragment;
import com.gfd.cropwis.utils.ActivityUtils;

public class CropGrowingSeasonActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crop_growing_season_act);

        setupToolbar();

        setupDrawer();

        setupFragment();
    }

    private void setupToolbar() {
        // Load toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.crop_growing_sesson);
        setSupportActionBar(toolbar);
        if (darkTheme) {
            toolbar.setPopupTheme(R.style.AppTheme_PopupOverlay_Dark);
        } else if (blackTheme) {
            toolbar.setPopupTheme(R.style.AppTheme_PopupOverlay_Black);
        }
    }

    private void setupFragment() {
        CropGrowingSeasonFragment fragment =
                (CropGrowingSeasonFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (fragment == null) {
            // Create the fragment
            fragment = CropGrowingSeasonFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), fragment, R.id.contentFrame);
        }
    }

    private void setupDrawer() {
        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerFragment.setUpDrawer(R.id.fragment_navigation_drawer, drawerLayout, (Toolbar) findViewById(R.id.toolbar));
    }
}
