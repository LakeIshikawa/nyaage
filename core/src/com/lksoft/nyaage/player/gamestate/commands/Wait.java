package com.lksoft.nyaage.player.gamestate.commands;

import com.badlogic.gdx.Gdx;

/**
 * Created by lake on 15/01/04.
 */
public class Wait extends ScriptCommand {

    // Timer
    private float time;

    /**
     * New wait command
     * @param time
     */
    public Wait(float time){
        this.time = time;
    }

    @Override
    public boolean doUpdate() {
        time -= Gdx.graphics.getDeltaTime();
        return time<0;
    }

    @Override
    public void draw() {
        // Nothing
    }

    @Override
    public void doSkip() {
        // Nothing to do
    }

    @Override
    public void onFinish() {
        // Nothing
    }
}
