package com.lksoft.nyaage.player.gamestate;

import com.badlogic.gdx.math.Vector2;
import com.lksoft.nyaage.player.common.Direction;
import com.lksoft.nyaage.player.data.NyaCharacter;


/**
 * Created by lake on 14/12/24.
 * View instance
 */
public class ViewState {

    // Facing
    private Direction facing = Direction.DOWN;


    /**
     * Make a view state
     */
    public ViewState(NyaCharacter character){

    }

    /**
     * Frame update
     */
    public void update() {

    }

    /**
     * Draw
     */
    public void draw() {

    }

    /**
     * @return This view's baseline
     */
    public int getBaseline() {
        return 0;
    }

    /**
     * @return current position
     */
    public Vector2 getPosition() {
        return null;
    }

    /**
     * Sets the position
     * @param x
     * @param y
     */
    public void setPosition(int x, int y) {
        // TODO
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
}
