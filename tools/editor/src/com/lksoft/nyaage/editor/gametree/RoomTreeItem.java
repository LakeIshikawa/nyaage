package com.lksoft.nyaage.editor.gametree;

import com.lksoft.nyaage.player.data.NyaRoom;

import javafx.scene.control.TreeItem;

/**
 * Created by lake on 15/01/11.
 */
public class RoomTreeItem extends TreeItem<String> {

    private NyaRoom room;

    public RoomTreeItem(NyaRoom r) {
        super(r.getName());
        this.room = r;
    }

    @Override
    public boolean isLeaf(){
        return true;
    }

    public NyaRoom getRoom() {
        return room;
    }
}
