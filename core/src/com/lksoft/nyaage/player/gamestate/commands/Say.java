package com.lksoft.nyaage.player.gamestate.commands;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.lksoft.nyaage.player.Nya;
import com.lksoft.nyaage.player.gamestate.CharacterState;

/**
 * Created by lake on 15/01/02.
 */
public class Say extends ScriptCommand {
    // Average reading speed
    public static final float CPS = 60.0f/(140*5);
    // Max text width
    private static final int MAX_WIDTH = 400;
    // Additional height
    private static final int VER_ROOM = 50;

    // Font
    private BitmapFont font;
    // Text
    private String text;
    // Character
    private CharacterState character;

    // Talk time
    private float talkTime;


    /**
     * Display the message relative to the specified character
     * @param characterState
     * @param text
     */
    public Say(CharacterState characterState, String text, String font) {
        this.character = characterState;
        this.font = Nya.get().getAssetManager().get(font);
        this.text = text;

        // Calculate talk time
        talkTime = Math.max(1.5f, text.length() * CPS);
    }

    /**
     * Frame update
     * @return
     */
    public boolean doUpdate() {
        this.talkTime -= Gdx.graphics.getDeltaTime();
        return talkTime<0;
    }

    @Override
    public void draw() {
        font.setColor(1, 0, 0, 1);
        BitmapFont.TextBounds bounds = font.getWrappedBounds(text, MAX_WIDTH);

        int x = (int)(character.getPosition().x - bounds.width/2);
        int y = (int)(character.getPosition().y + character.getCurrentFrame().getRegionHeight() + VER_ROOM);

        // Do not overflow
        int maxH = (int)Nya.get().getViewport().getWorldWidth() - 10;
        int maxV = (int)Nya.get().getViewport().getWorldHeight() - 10;
        if( x<10 ) x=10;
        if( y<10 ) y=10;
        if( x+bounds.width > maxH ) {
            x = (int)(maxH - bounds.width);
        }
        if( y+bounds.height > maxV ) {
            y = (int)(maxV - bounds.height);
        }

        // Draw
        font.drawWrapped(
                Nya.get().getSpriteBatch(),
                text,
                x,
                y,
                bounds.width,
                BitmapFont.HAlignment.CENTER);
    }

    @Override
    public void doSkip() {
        // Nothing
    }

    @Override
    public void onFinish() {
        character.endSay();
    }

    @Override
    public boolean onTouch(){
        return true;
    }

}
