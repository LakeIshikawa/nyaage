package com.lksoft.nyaage.desktop;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;

import java.nio.file.Paths;

/**
 * Created by lake on 14/12/27.
 */
public class NyaGamePackager {

    /**
     * Process all input resources and create all needed assets
     * @param resPath
     * @param assetsPath
     */
    public static void packageResources(String resPath, String assetsPath) throws Exception {
        // Pack textures
        TexturePacker.Settings settings = new TexturePacker.Settings();
        settings.maxWidth = 2048;
        settings.maxHeight = 2048;
        settings.useIndexes = false;
        TexturePacker.process(settings, resPath+"image/", assetsPath+"image", "images");

        // Process walkable maps
        WalkmapPacker.process(resPath+"walkmaps/", assetsPath+"walkmaps/");

        // Copy resources
        Copy.copyDir(Paths.get(resPath+"fonts"), Paths.get(assetsPath+"fonts"));
        Copy.copyDir(Paths.get(resPath+"hotspots"), Paths.get(assetsPath+"hotspots"));
        Copy.copyDir(Paths.get(resPath+"overlays"), Paths.get(assetsPath+"overlays"));
        Copy.copyDir(Paths.get(resPath+"scripts"), Paths.get(assetsPath+"scripts"));
        Copy.copyDir(Paths.get(resPath+"game.json"), Paths.get(assetsPath+"game.json"));
    }
}
