package com.lksoft.nyaage.editor.gametree;

import com.lksoft.nyaage.player.data.NyaCharacter;
import com.lksoft.nyaage.player.data.NyaGame;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

/**
 * Created by lake on 15/01/11.
 */
public class CharactersTreeItem extends TreeItem<String> {
    // Game
    private NyaGame game;

    // Children created
    private boolean childrenCreated = false;

    public CharactersTreeItem(NyaGame game) {
        super("Characters");
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
            for( NyaCharacter c : game.getCharacters() ){
                list.add(new CharacterTreeItem(c));
            }

            getChildren().addAll(list);
            return list;
        }

        return super.getChildren();
    }
}
