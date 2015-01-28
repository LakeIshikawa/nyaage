package com.lksoft.nyaage.editor.gametree;

import com.lksoft.nyaage.player.data.NyaView;

import javafx.scene.control.TreeItem;

/**
 * Created by lake on 15/01/11.
 */
public class ViewTreeItem extends TreeItem<String> {

    public ViewTreeItem(NyaView v) {
        super(v.getName());
    }

    @Override
    public boolean isLeaf(){
        return true;
    }
}
