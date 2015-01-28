package com.lksoft.nyaage.player.gamestate;

/**
 * Created by lake on 14/12/29.
 *
 * Game state capable of triggering script events
 */
public interface ScriptableState {

    /**
     * Basic interaction
     * @param type Interaction type
     */
    public void interact(String type);
}
