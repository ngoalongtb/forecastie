package com.gfd.cropwis.hotspotsarea;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gfd.cropwis.R;
import com.unnamed.b.atv.model.TreeNode;

public class HotspotsAreaLevel2Holder extends TreeNode.BaseNodeViewHolder<HotspotArea> {
    private TextView tvName2;

    public HotspotsAreaLevel2Holder(Context context) {
        super(context);
    }

    @Override
    public View createNodeView(TreeNode treeNode, HotspotArea hotspotArea) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.hotspots_area_item2, null, false);

        ViewGroup.LayoutParams rootParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(rootParams);  ///////*********set layout params to your view

        mapping(view);

        tvName2.setText(hotspotArea.getLevel2());
        return view;
    }

    private void mapping(View view) {
        tvName2 = view.findViewById(R.id.tvName1);
    }
}
