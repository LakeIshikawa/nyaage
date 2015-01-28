package com.lksoft.nyaage.player.gamestate;

/**
 * Created by lake on 14/12/27.
 */
public class DebugState {

    // Draw debug gizmos
    private boolean drawGizmos = false;


    public boolean isDrawGizmos() {
        return drawGizmos;
    }

    public void setDrawGizmos(boolean drawGizmos) {
        this.drawGizmos = drawGizmos;
    }
}
