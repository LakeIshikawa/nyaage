package com.lksoft.nyaage.player.gamestate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.esotericsoftware.spine.Skeleton;
import com.esotericsoftware.spine.SkeletonData;
import com.esotericsoftware.spine.SkeletonJson;
import com.esotericsoftware.spine.SkeletonRenderer;
import com.lksoft.nyaage.player.Nya;
import com.lksoft.nyaage.player.common.Direction;
import com.lksoft.nyaage.player.data.NyaCharacter;

import java.util.Vector;


/**
 * Created by lake on 14/12/24.
 * View instance
 */
public class ViewState {

    // Skeleton data
    private Skeleton skeleton;
    private SkeletonRenderer skeletonRenderer;
    private AnimationState animationState;

    // 4-directions or not
    private boolean fourDirections;

    // Facing
    private Direction facing = Direction.DOWN;

    // Position - temp
    private Vector2 tmpPosition = new Vector2();
    // bbox - temp
    private Vector2 aabbOffset = new Vector2();
    private Vector2 aabbSize = new Vector2();


    /**
     * Make a view state
     */
    public ViewState(NyaCharacter character, boolean fourDirections){
        this.fourDirections = fourDirections;

        // Load atlas and json
        TextureAtlas atlas = Nya.get().getAssetManager().get("image/characters.atlas", TextureAtlas.class);
        SkeletonJson skeletonJson = new SkeletonJson(atlas);
        skeletonJson.setScale(character.getBaseScale());
        SkeletonData skeletonData = skeletonJson.readSkeletonData(Gdx.files.internal(character.getView()));
        skeleton = new Skeleton(skeletonData);
        skeleton.setPosition(character.getStartX(), character.getStartY());

        // Make animation state
        AnimationStateData animationStateData = new AnimationStateData(skeletonData);
        animationState = new AnimationState(animationStateData);

        // Set standard mix values
        animationStateData.setDefaultMix(0.2f);

        // Create renderer
        skeletonRenderer = new SkeletonRenderer();
        skeletonRenderer.setPremultipliedAlpha(true);

        // Set idle animation
        setAnimation(0, "idle", true);

        // Take default dimensions
        animationState.apply(skeleton);
        skeleton.updateWorldTransform();
        skeleton.getBounds(aabbOffset, aabbSize);
    }

    /**
     * Frame update
     */
    public void update() {
        animationState.update(Gdx.graphics.getDeltaTime());
        animationState.apply(skeleton);
        skeleton.updateWorldTransform();
    }

    /**
     * Draw
     */
    public void draw() {
        skeletonRenderer.draw(Nya.get().getSpriteBatch(), skeleton);
        Nya.get().getSpriteBatch().setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    }

    /**
     * @return This view's baseline
     */
    public int getBaseline() {
        return (int)skeleton.getY();
    }

    /**
     * @return The height of the whole skeleton now
     */
    public int getHeight() {
        return (int)aabbSize.y;
    }

    /**
     * @return current position
     */
    public Vector2 getPosition() {
        tmpPosition.set(skeleton.getX(), skeleton.getY());
        return tmpPosition;
    }

    /**
     * Sets the position
     * @param x
     * @param y
     */
    public void setPosition(float x, float y) {
        skeleton.setPosition(x, y);
    }

    /**
     * Face a direction
     * @param facing
     */
    public void faceDirection(Direction facing) {
        this.facing = facing;
    }

    /**
     * @return The facing direction
     */
    public Direction getFacing() {
        return facing;
    }

    /**
     * Set the animation
     * @param name Animation name
     * @param loop Set to loop or not
     */
    public void setAnimation(int track, String name, boolean loop){
        if( fourDirections ){
            animationState.setAnimation(track, name+"-"+getFacing(), loop);
        } else {
            animationState.setAnimation(track, name, loop);
        }
    }
}
