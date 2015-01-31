package com.lksoft.nyaage.editor.gametree;

import com.lksoft.nyaage.player.data.NyaGame;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

/**
 * Created by lake on 15/01/11.
 */
public class GameTreeItem extends TreeItem<String> {
    // The game
    NyaGame game;

    // Children created
    boolean childrenCreated = false;

    public GameTreeItem(NyaGame game){
        super("Game");
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
            ObservableList children = FXCollections.observableArrayList(
                    (TreeItem<String>)new RoomsTreeItem(game),
                    new CharactersTreeItem(game)
            );

            getChildren().addAll(children);
            return children;
        }

        return super.getChildren();
    }

    public NyaGame getGame() {
        return game;
    }
}
