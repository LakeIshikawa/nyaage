package com.lksoft.nyaage.player.com.lksoft.luagdx;

import com.badlogic.gdx.Game;

public class LuagdxPlayer extends Game {

	@Override
	public void create () {
        setScreen(new LuagdxPlayerScreen());
	}
}
