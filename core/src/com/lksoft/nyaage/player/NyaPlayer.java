package com.lksoft.nyaage.player;

import com.badlogic.gdx.Game;

public class NyaPlayer extends Game {

	@Override
	public void create () {
        setScreen(new PlayerScreen());
	}
}
