package com.lksoft.nyaage.player.gamestate;

import com.badlogic.gdx.Gdx;
import com.lksoft.nyaage.player.Nya;

/**
 * Created by lake on 15/01/04.
 */
public class RoomChange {
    // Fade time
    private final static float FADE_TIME = 1.5f;

    // Room to load
    private int nextRoom;
    // Timer
    private float timer;
    // Load flag
    private boolean loaded;

    /**
     * Make a new room change
     * @param nextRoom Next room to be loaded
     */
    public RoomChange(int nextRoom){
        this.nextRoom = nextRoom;
    }

    /**
     * Frame update
     * @return completion
     */
    public boolean update(){
        timer += Gdx.graphics.getDeltaTime();

        // Fade out current room
        if( timer < FADE_TIME ){
            float fade = 1.0f-timer/FADE_TIME;
            Nya.get().getSpriteBatch().setColor(fade, fade, fade, 1.0f);
        }

        // Fade in next room
        else if( timer < FADE_TIME*2 ) {
            float fade = (timer-FADE_TIME)/FADE_TIME;
            Nya.get().getSpriteBatch().setColor(fade, fade, fade, 1.0f);

            // Load the room
            if( !loaded ){
                Nya.get().getGameState().loadRoom(nextRoom);
                loaded = true;
            }
        }

        // Finished
        else {
            return true;
        }

        return false;
    }
}
