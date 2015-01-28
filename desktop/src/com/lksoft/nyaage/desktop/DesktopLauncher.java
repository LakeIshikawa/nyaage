package com.lksoft.nyaage.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.lksoft.nyaage.player.NyaPlayer;

public class DesktopLauncher {
	public static void main (String[] arg) {
        try {
            NyaGamePackager.packageResources("../../resources/", "./");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        // Launch app
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 800;
        config.height = 600;
		new LwjglApplication(new NyaPlayer(), config);
	}
}
