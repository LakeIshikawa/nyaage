package com.lksoft.nyaage.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.utils.Json;
import com.lksoft.nyaage.player.data.NyaGame;
import com.lksoft.nyaage.player.gamestate.GameState;

/**
 * Created by lake on 14/12/21.
 */
public class PlayerScreen implements Screen {

    @Override
    public void show() {
        // Load game.json
        Json json = new Json();
        json.setIgnoreUnknownFields(true);
        NyaGame game = json.fromJson(NyaGame.class, Gdx.files.internal("game.json"));

        // Load resources
        // Images
        Nya.get().getAssetManager().load("image/images.atlas", TextureAtlas.class);
        // Fonts
        FileHandleResolver resolver = new InternalFileHandleResolver();
        Nya.get().getAssetManager().setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        Nya.get().getAssetManager().setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

        FreetypeFontLoader.FreeTypeFontLoaderParameter size1Params = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        size1Params.fontFileName = game.getDefaultFont();
        size1Params.fontParameters.size = 24;
        Nya.get().getAssetManager().load(game.getDefaultFont(), BitmapFont.class, size1Params);
        Nya.get().getAssetManager().finishLoading();

        // Create new game
        GameState state = GameState.create(game);
        Nya.get().setGameState(state);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Nya.get().getSpriteBatch().setProjectionMatrix(Nya.get().getViewport().getCamera().combined);

        Nya.get().getSpriteBatch().begin();
        Nya.get().getGameState().update();
        Nya.get().getGameState().draw();
        Nya.get().getSpriteBatch().end();
    }

    @Override
    public void resize(int width, int height) {
        Nya.get().getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
