package com.lksoft.nyaage.player.gamestate.commands;

/**
 * Created by lake on 15/01/05.
 */
public class Cutscene {

    // Skipping status
    private boolean isSkipping = false;

    /**
     * Cut the cutscene (lol)
     */
    public void cut() {
        setSkipping(true);
    }

    public boolean isSkipping() {
        return isSkipping;
    }

    public void setSkipping(boolean isSkipping) {
        this.isSkipping = isSkipping;
    }
}
