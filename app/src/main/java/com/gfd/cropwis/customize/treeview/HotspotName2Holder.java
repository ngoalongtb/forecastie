package com.gfd.cropwis.customize.treeview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gfd.cropwis.R;
import com.gfd.cropwis.models.Hotspot;
import com.unnamed.b.atv.model.TreeNode;

public class HotspotName2Holder extends TreeNode.BaseNodeViewHolder<String> {
    private TextView tvName2;

    public HotspotName2Holder(Context context) {
        super(context);
    }

    @Override
    public View createNodeView(TreeNode treeNode, String value) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.item_hotspot_name2, null, false);

        ViewGroup.LayoutParams rootParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(rootParams);  ///////*********set layout params to your view

        mapping(view);

        tvName2.setText(value);
        return view;
    }

    private void mapping(View view) {
        tvName2 = view.findViewById(R.id.tvName2);
    }
}
