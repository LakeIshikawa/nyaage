package com.lksoft.nyaage.player.gamestate.commands;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.lksoft.nyaage.player.Nya;

/**
 * Created by lake on 15/01/04.
 */
public class DisplayText extends ScriptCommand {
    // State
    private String text;
    private int x,y;
    private BitmapFont font;

    // Timer
    private float displayTime;

    /**
     * Make a new text display
     * @param text Text
     * @param x Position
     * @param y Position
     */
    public DisplayText(String text, int x, int y, String font) {
        this.x = x;
        this.y = y;
        this.text = text;
        this.font = Nya.get().getAssetManager().get(font);

        displayTime = Math.max(1.0f, text.length() * Say.CPS);
    }

    @Override
    public boolean doUpdate() {
        displayTime -= Gdx.graphics.getDeltaTime();
        return displayTime<0;
    }

    @Override
    public void draw() {
        font.setColor(1, 1, 1, 1);
        font.drawMultiLine(
                Nya.get().getSpriteBatch(),
                text,
                x, y
        );
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
