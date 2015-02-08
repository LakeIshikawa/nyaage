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
    private Skeleton[] skeleton = new Skeleton[4];
    private AnimationState[] animationState = new AnimationState[4];
    private SkeletonRenderer skeletonRenderer;

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
    public ViewState(NyaCharacter character){

        // Load atlas and json
        TextureAtlas atlas = Nya.get().getAssetManager().get("image/characters.atlas", TextureAtlas.class);
        SkeletonJson skeletonJson = new SkeletonJson(atlas);
        skeletonJson.setScale(character.getBaseScale());

        // Load skeletons
        loadSkeleton(skeletonJson, 0, character.getViewFront());
        loadSkeleton(skeletonJson, 1, character.getViewSide());
        loadSkeleton(skeletonJson, 2, character.getViewSide());
        loadSkeleton(skeletonJson, 3, character.getViewBack());

        // Flip left!
        if( skeleton[2] != null ) skeleton[2].setFlipX(true);

        // Set initial position
        for( int i=0; i<4; i++ ){
            if( skeleton[i] != null ) {
                skeleton[i].setPosition(character.getStartX(), character.getStartY());
            }
        }

        // Create renderer
        skeletonRenderer = new SkeletonRenderer();
        skeletonRenderer.setPremultipliedAlpha(true);

        // Set idle animation
        int index = getDirectionIndex();
        setAnimation(0, "idle", true);

        // Take default dimensions
        animationState[index].apply(skeleton[index]);
        skeleton[index].updateWorldTransform();
        skeleton[index].getBounds(aabbOffset, aabbSize);
    }

    /**
     * Load a skeleton and animation state
     * @param skeletonJson
     * @param pos
     * @param view
     */
    private void loadSkeleton(SkeletonJson skeletonJson, int pos, String view) {
        if( view == null ) return;

        SkeletonData skeletonData = skeletonJson.readSkeletonData(Gdx.files.internal(view));
        skeleton[pos] = new Skeleton(skeletonData);

        // Make animation state
        AnimationStateData animationStateData = new AnimationStateData(skeletonData);
        animationState[pos] = new AnimationState(animationStateData);

        // Set standard mix values
        animationStateData.setDefaultMix(0.2f);
    }

    /**
     * Frame update
     */
    public void update() {
        // Choose direction
        int index = getDirectionIndex();

        if( index != -1 ) {
            animationState[index].update(Gdx.graphics.getDeltaTime());
            animationState[index].apply(skeleton[index]);
            skeleton[index].updateWorldTransform();
        }
    }

    /**
     * Draw
     */
    public void draw() {
        // Choose direction
        int index = getDirectionIndex();

        // Draw
        skeleton[index].setColor(Nya.get().getSpriteBatch().getColor());
        skeletonRenderer.draw(Nya.get().getSpriteBatch(), skeleton[index]);
        Nya.get().getSpriteBatch().setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    }

    /**
     * @return This view's baseline
     */
    public int getBaseline() {
        return (int)skeleton[getDirectionIndex()].getY();
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
        int index = getDirectionIndex();
        tmpPosition.set(skeleton[index].getX(), skeleton[index].getY());
        return tmpPosition;
    }

    /**
     * Sets the position
     * @param x
     * @param y
     */
    public void setPosition(float x, float y) {
        for( int i=0; i<4; i++ ) {
            if( skeleton[i] != null ) {
                skeleton[i].setPosition(x, y);
            }
        }
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
        for( int i=0; i<4; i++ ) {
            if( animationState[i] != null ) {
                animationState[i].setAnimation(track, name, loop);
            }
        }
    }

    /**
     * @return The index of the skeleton/animationData of current
     * facing direction, or fallback to any other if not present.
     */
    private int getDirectionIndex(){
        if( skeleton[facing.ordinal()] != null ){
            return facing.ordinal();
        }
        else {
            // Fallback to any available skeleton
            for (int i = 0; i < 4; i++) {
                if (skeleton[i] != null) return i;
            }
        }

        return -1;
    }
}
