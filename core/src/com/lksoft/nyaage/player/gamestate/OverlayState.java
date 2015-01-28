package com.lksoft.nyaage.player.gamestate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.lksoft.nyaage.player.Nya;
import com.lksoft.nyaage.player.data.NyaOverlay;

/**
 * Created by lake on 14/12/27.
 */
public class OverlayState implements DrawableState {
    // Parent room
    RoomState room;
    // Overlay data
    NyaOverlay overlay;

    // Fbo
    FrameBuffer overlayFbo;
    // Region
    TextureRegion overlayRegion;
    // Draw pos
    int drawX;
    int drawY;

    public OverlayState(RoomState room, NyaOverlay overlay){
        this.overlay = overlay;
        this.room = room;

        // Load alpha mask
        Pixmap alphaPix = new Pixmap(Gdx.files.internal(overlay.getAlphaMask()));
        Texture alphaTex = new Texture(alphaPix);
        TextureRegion alphaMask = findSmallestBRRegion(alphaPix, alphaTex);
        drawX = alphaMask.getRegionX();
        drawY = alphaMask.getRegionY();

        TextureRegion bg = room.getBackground().getKeyFrame(0);
        overlayFbo = new FrameBuffer(Pixmap.Format.RGBA8888, alphaMask.getRegionWidth(), alphaMask.getRegionHeight(), false);
        overlayRegion = new TextureRegion(overlayFbo.getColorBufferTexture());
        overlayRegion.flip(false, true);

        overlayFbo.begin();
        SpriteBatch batch = new SpriteBatch();
        Viewport viewport = new FitViewport(alphaMask.getRegionWidth(), alphaMask.getRegionHeight());
        viewport.update(alphaMask.getRegionWidth(), alphaMask.getRegionHeight(), true);
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();

        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Draw the alpha mask
        Gdx.gl.glColorMask(false, false, false, true);
        //change the blending function for our alpha map
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ZERO);
        batch.draw(alphaTex, -alphaMask.getRegionX(), -alphaMask.getRegionY());
        batch.flush();

        // Draw the color
        Gdx.gl.glColorMask(true, true, true, true);
        batch.setBlendFunction(GL20.GL_DST_ALPHA, GL20.GL_ONE_MINUS_DST_ALPHA);
        batch.draw(bg, -alphaMask.getRegionX(), -alphaMask.getRegionY());

        batch.end();
        overlayFbo.end();

        // Alpha mask not needed anymore
        alphaPix.dispose();
        alphaTex.dispose();
    }

    /**
     * Find the smallest bounding rect of non-alpha pixels and create the
     * minimal texture region for that.
     * @param alphaPix
     * @param texture
     * @return
     */
    private TextureRegion findSmallestBRRegion(Pixmap alphaPix, Texture texture) {
        int maxx = 0;
        int minx = alphaPix.getWidth()-1;
        int maxy = 0;
        int miny = alphaPix.getHeight()-1;
        for( int x=0; x<alphaPix.getWidth(); x++ ){
            for( int y=0; y<alphaPix.getHeight(); y++ ){
                if( (alphaPix.getPixel(x, y) & 0xFF) != 0 ){
                    if( x<minx ) minx = x;
                    if( x>maxx ) maxx = x;
                    if( y<miny ) miny = y;
                    if( y>maxy ) maxy = y;
                }
            }
        }

        return new TextureRegion(texture, minx, alphaPix.getHeight()-maxy, 1+maxx-minx, 1+maxy-miny);
    }

    @Override
    public void draw() {
        Nya.get().getSpriteBatch().draw(overlayRegion, drawX, drawY);
    }

    @Override
    public int getBaseline() {
        return overlay.getBaseline();
    }
}
