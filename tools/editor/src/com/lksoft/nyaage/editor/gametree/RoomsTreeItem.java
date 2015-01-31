package com.lksoft.nyaage.editor.gametree;

import com.lksoft.nyaage.player.data.NyaGame;
import com.lksoft.nyaage.player.data.NyaRoom;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

/**
 * Created by lake on 15/01/11.
 */
public class RoomsTreeItem extends TreeItem<String> {
    // The game
    private NyaGame game;

    // Children created
    private boolean childrenCreated = false;

    public RoomsTreeItem(NyaGame game) {
        super("Rooms");
        this.game = game;
    }

    @Override
    public boolean isLeaf(){
        return false;
    }

    @Override
    public ObservableList<TreeItem<String>> getChildren() {
        if( !childrenCreated ) {
            childrenCreated = true;

            ObservableList<TreeItem<String>> list = FXCollections.observableArrayList();
            for( NyaRoom r : game.getRooms() ){
                list.add(new RoomTreeItem(r));
            }

            getChildren().addAll(list);
            return list;
        }

        return super.getChildren();
    }
}
