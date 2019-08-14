package com.gfd.cropwis.hotspotsarea;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gfd.cropwis.R;
import com.unnamed.b.atv.model.TreeNode;

public class HotspotsAreaLevel3Holder extends TreeNode.BaseNodeViewHolder<HotspotArea> {
    private TextView tvHotspotValue;
    private TextView tvHotspotName3;

    public HotspotsAreaLevel3Holder(Context context) {
        super(context);
    }

    @Override
    public View createNodeView(TreeNode treeNode, HotspotArea hotspotArea) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.hotspots_area_item3, null, false);

        ViewGroup.LayoutParams rootParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(rootParams);  ///////*********set layout params to your view

        mapping(view);

        tvHotspotName3.setText(hotspotArea.getLevel3());
        tvHotspotValue.setText(String.valueOf(hotspotArea.getValue()));

        return view;
    }

    private void mapping(View view) {
        tvHotspotValue = view.findViewById(R.id.tvHotspotValue);
        tvHotspotName3 = view.findViewById(R.id.tvHotspotName3);
    }
}
