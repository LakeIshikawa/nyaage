package com.lksoft.nyaage.editor.gametree;

import com.lksoft.nyaage.player.data.NyaCharacter;

import javafx.scene.control.TreeItem;

/**
 * Created by lake on 15/01/11.
 */
public class CharacterTreeItem extends TreeItem<String> {

    public CharacterTreeItem(NyaCharacter c) {
        super(c.getDisplayName());
    }

    @Override
    public boolean isLeaf(){
        return true;
    }
}
