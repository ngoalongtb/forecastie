package com.gfd.cropwis.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.gfd.cropwis.R;
import com.gfd.cropwis.activities.HotspotsAreaActivity;
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
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ActionBarDrawerToggle   mDrawerToggle;
    private DrawerLayout            mDrawerLayout;

    private LinearLayout mMenuWeatherForecast;
    private LinearLayout mMenuHotspotArea;
    private LinearLayout mMenuCropGrowingSesson;
    private LinearLayout mMenuSettings;
    private LinearLayout mMenuAbout;

    private OnFragmentInteractionListener mListener;

    public NavigationDrawerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NavigationDrawerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NavigationDrawerFragment newInstance(String param1, String param2) {
        NavigationDrawerFragment fragment = new NavigationDrawerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.menuWeatherForecast:
                break;
            case R.id.menuHotspotArea:
                intent = new Intent(this.getActivity(), HotspotsAreaActivity.class);
                startActivity(intent);
                break;
            case R.id.menuCropGrowingSesson:
                break;
            case R.id.menuSettings:
                intent = new Intent(this.getActivity(), SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.menuAbout:
                aboutDialog();
                break;
        }
    }
    private void aboutDialog() {
        new AboutDialogFragment().show(getActivity().getSupportFragmentManager(), null);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
