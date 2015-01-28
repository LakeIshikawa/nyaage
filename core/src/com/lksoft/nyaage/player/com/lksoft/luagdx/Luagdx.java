package com.lksoft.nyaage.player.com.lksoft.luagdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import org.luaj.vm2.Globals;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.JsePlatform;
import org.luaj.vm2.luajc.LuaJC;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lake on 14/12/29.
 */
public class Luagdx {

    // Sprite batch
    SpriteBatch batch = new SpriteBatch();
    // Viewport
    Viewport viewport = new FitViewport(800, 600);

    // Sprite list
    List<ZSprite> sprites = new ArrayList<>();

    // Lua globals
    Globals globals;

    /**
     * Startup and load main.lua
     */
    public void initialize(){
        // Initialize lua script engine
        globals = JsePlatform.standardGlobals();
        LuaJC.install(getGlobals());

        // Bind asset manager
        getGlobals().set("luagdx", CoerceJavaToLua.coerce(this));

        getGlobals().set("TextureAtlas", CoerceJavaToLua.coerce(TextureAtlas.class));
        getGlobals().set("ZSprite", CoerceJavaToLua.coerce(ZSprite.class));

        // Execute main file (does not block)
        getGlobals().load(Gdx.files.internal("scripts/main.lua").reader(), "main.lua").call();
    }

    /**
     * Render all sprites
     */
    public void render() {
        sprites.sort(ZSprite.comparator);
        for( Sprite s : sprites ){
            s.draw(batch);
        }
    }

    /**
     * Add sprite to render list
     * @param s
     */
    public void addSprite(ZSprite s){
        sprites.add(s);
    }

    /**
     * Remove from render list
     * @param s
     */
    public void removeSprite(Sprite s){
        sprites.remove(s);
    }

    // Accessors
    SpriteBatch getBatch() {
        return batch;
    }

    Viewport getViewport() {
        return viewport;
    }

    List<ZSprite> getSprites() {
        return sprites;
    }

    Globals getGlobals() {
        return globals;
    }
}
