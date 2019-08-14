
package com.gfd.cropwis.hotspotsarea;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gfd.cropwis.R;
import com.unnamed.b.atv.model.TreeNode;

public class HotspotsAreaLevel1Holder extends TreeNode.BaseNodeViewHolder<HotspotArea> {
    private TextView tvName1;

    public HotspotsAreaLevel1Holder(Context context) {
        super(context);
    }

    @Override
    public View createNodeView(TreeNode treeNode, HotspotArea hotspotArea) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.hotspots_area_item1, null, false);

        ViewGroup.LayoutParams rootParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(rootParams);  ///////*********set layout params to your view

        mapping(view);

        tvName1.setText(hotspotArea.getLevel1());
        return view;
    }

    private void mapping(View view) {
        tvName1 = view.findViewById(R.id.tvName1);
    }
}
