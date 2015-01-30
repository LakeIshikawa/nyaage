package com.lksoft.nyaage.player.gamestate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.lksoft.nyaage.player.Nya;
import com.lksoft.nyaage.player.data.NyaObject;

/**
 * Created by lake on 15/01/07.
 */
public class ObjectState implements DrawableState {
    // Basic data
    private NyaObject object;


    // Locked view
    private ViewState view;

    // Visibility
    private boolean visible = true;


    /**
     * Make new object
     * @param object
     */
    public ObjectState(NyaObject object){
        this.object = object;

        // TODO Load view
    }

    /**
     * Frame update
     */
    public void update() {
        //view.update();
    }


    @Override
    public void draw() {
        //view.draw();
    }

    @Override
    public int getBaseline() {
        return 0;//view.getBaseline();
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public NyaObject getObject() {
        return object;
    }
}
