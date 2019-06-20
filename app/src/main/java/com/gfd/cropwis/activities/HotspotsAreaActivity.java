package com.gfd.cropwis.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.gfd.cropwis.customize.treeview.HotspotHolder;
import com.gfd.cropwis.customize.treeview.HotspotName2Holder;
import com.gfd.cropwis.models.Hotspot;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import com.gfd.cropwis.R;

public class HotspotsAreaActivity extends AppCompatActivity {
    private ScrollView svHotspotList;
    private LinearLayout llHotspotList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotspots_area);

        mapping();
        
        displayData();
    }

    private void mapping() {
        svHotspotList = findViewById(R.id.svHotspotList);
        llHotspotList = findViewById(R.id.llHotspotList);
    }

    private void displayData() {
        Hotspot hotspot1 = new Hotspot();
        hotspot1.setValue(49);
        hotspot1.setName3("child 1");

        Hotspot hotspot2 = new Hotspot();
        hotspot2.setValue(48);
        hotspot2.setName3("child 2");

        TreeNode root = TreeNode.root();
        TreeNode parent = new TreeNode("MyParentNode").setViewHolder(new HotspotName2Holder(HotspotsAreaActivity.this));
        TreeNode child0 = new TreeNode(hotspot1).setViewHolder(new HotspotHolder(HotspotsAreaActivity.this));;
        TreeNode child1 = new TreeNode(hotspot2).setViewHolder(new HotspotHolder(HotspotsAreaActivity.this));;
        parent.addChildren(child0, child1);
        root.addChild(parent);

        AndroidTreeView treeView = new AndroidTreeView(HotspotsAreaActivity.this, root);
        llHotspotList.addView(treeView.getView());
    }
}
