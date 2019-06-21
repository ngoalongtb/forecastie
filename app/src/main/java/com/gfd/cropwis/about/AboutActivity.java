package com.gfd.cropwis.about;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;

import com.gfd.cropwis.R;
import com.gfd.cropwis.activities.BaseActivity;
import com.gfd.cropwis.fragments.NavigationDrawerFragment;
import com.gfd.cropwis.utils.ActivityUtils;

public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.about_act);

        setupToolbar();

        setupDrawer();

        setupFragment();
    }

    private void setupToolbar() {
        // Load toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.about_preferences);
        setSupportActionBar(toolbar);
        if (darkTheme) {
            toolbar.setPopupTheme(R.style.AppTheme_PopupOverlay_Dark);
        } else if (blackTheme) {
            toolbar.setPopupTheme(R.style.AppTheme_PopupOverlay_Black);
        }
    }

    private void setupFragment() {
        AboutFragment aboutFragment =
                (AboutFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (aboutFragment == null) {
            // Create the fragment
            aboutFragment = AboutFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), aboutFragment, R.id.contentFrame);
        }
    }

    private void setupDrawer() {
        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerFragment.setUpDrawer(R.id.fragment_navigation_drawer, drawerLayout, (Toolbar) findViewById(R.id.toolbar));
    }
}
