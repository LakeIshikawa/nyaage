package com.lksoft.nyaage.editor.gametree;

import com.lksoft.nyaage.player.data.NyaGame;
import com.lksoft.nyaage.player.data.NyaView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

/**
 * Created by lake on 15/01/11.
 */
public class ViewsTreeItem extends TreeItem<String>  {
    // The game
    private NyaGame game;

    // Children created
    private boolean childrenCreated = false;


    public ViewsTreeItem(NyaGame game) {
        super("Views");
        this.game = game;
    }


    @Override
    public boolean isLeaf(){
        return game.getViews().isEmpty();
    }

    @Override
    public ObservableList<TreeItem<String>> getChildren() {
        if( !childrenCreated ) {
            childrenCreated = true;

            ObservableList<TreeItem<String>> list = FXCollections.observableArrayList();
            for( NyaView v : game.getViews() ){
                list.add(new ViewTreeItem(v));
            }

            getChildren().addAll(list);
            return list;
        }

        return super.getChildren();
    }
}
