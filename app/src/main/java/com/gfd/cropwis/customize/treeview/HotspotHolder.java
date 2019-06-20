package com.gfd.cropwis.customize.treeview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gfd.cropwis.R;
import com.gfd.cropwis.models.Hotspot;
import com.unnamed.b.atv.model.TreeNode;

public class HotspotHolder extends TreeNode.BaseNodeViewHolder<Hotspot> {
    private TextView tvHotspotValue;
    private TextView tvHotspotName3;

    public HotspotHolder(Context context) {
        super(context);
    }

    @Override
    public View createNodeView(TreeNode treeNode, Hotspot hotspot) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.item_hotspot, null, false);

        ViewGroup.LayoutParams rootParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(rootParams);  ///////*********set layout params to your view

        mapping(view);

        tvHotspotName3.setText(hotspot.getName3());
        tvHotspotValue.setText(String.valueOf(hotspot.getValue()));

        return view;
    }

    private void mapping(View view) {
        tvHotspotValue = view.findViewById(R.id.tvHotspotValue);
        tvHotspotName3 = view.findViewById(R.id.tvHotspotName3);
    }
}
