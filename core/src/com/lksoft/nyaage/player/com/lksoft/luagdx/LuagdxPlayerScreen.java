package com.lksoft.nyaage.player.com.lksoft.luagdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

public class LuagdxPlayerScreen implements Screen {

    Luagdx luagdx = new Luagdx();

    @Override
    public void show() {
        luagdx.initialize();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        luagdx.getBatch().setProjectionMatrix(luagdx.getViewport().getCamera().combined);

        luagdx.getBatch().begin();
        luagdx.render();
        luagdx.getBatch().end();
    }

    @Override
    public void resize(int width, int height) {
        luagdx.getViewport().update(width, height, true);
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
