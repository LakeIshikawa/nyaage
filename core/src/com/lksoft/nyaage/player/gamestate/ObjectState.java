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
    // Position
    private Vector2 position = new Vector2();

    // Default image
    TextureRegion defaultImage;
    // Locked view
    private ViewState lockedView;

    // Visibility
    private boolean visible = true;

    // Anime timer
    float timer = 0.0f;

    /**
     * Make new object
     * @param object
     */
    public ObjectState(NyaObject object){
        this.object = object;
        this.position.set(object.getStartX(), object.getStartY());

        TextureAtlas atlas = Nya.get().getAssetManager().get("image/images.atlas", TextureAtlas.class);
        this.defaultImage = atlas.findRegion(object.getDefaultImage());
    }

    /**
     * @return Current anime frame of current view
     */
    public TextureRegion getCurrentFrame(){
        if( lockedView != null ) {
            Animation anime = lockedView.getAnimations().get(0);
            return anime.getKeyFrame(timer);
        } else
            return defaultImage;
    }

    /**
     * Frame update
     */
    public void update() {
        // Animate timer
        timer += Gdx.graphics.getDeltaTime();
    }


    @Override
    public void draw() {
        Nya.get().getSpriteBatch().draw(
                getCurrentFrame(),
                position.x,
                position.y);
    }

    @Override
    public int getBaseline() {
        return (int)position.y;
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
