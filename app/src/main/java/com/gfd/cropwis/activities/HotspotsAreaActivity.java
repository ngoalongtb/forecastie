package com.gfd.cropwis.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import com.gfd.cropwis.R;

public class HotspotsAreaActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotspots_area);

        mapping();
    }

    private void mapping() {

//        TreeNode root = TreeNode.root();
//        TreeNode parent = new TreeNode("MyParentNode");
//        TreeNode child0 = new TreeNode("ChildNode0");
//        TreeNode child1 = new TreeNode("ChildNode1");
//        parent.addChildren(child0, child1);
//        root.addChild(parent);
    }
}
