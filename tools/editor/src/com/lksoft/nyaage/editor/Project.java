package com.lksoft.nyaage.editor;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.lksoft.nyaage.player.Nya;
import com.lksoft.nyaage.player.data.NyaGame;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by lake on 15/01/12.
 */
public class Project {
    private static Project current = null;
    public static Project getCurrent(){ return current; }

    // Folders in project
    private static String[] projectFolders = {
            "fonts", "hotspots", "image", "overlays", "scripts", "spine", "walkmaps"
    };

    // State
    private NyaGame game;

    // Res path
    private String projectPath;

    /**
     * New project
     * @param gameJson
     */
    private Project(File gameJson) {
        // Save res path
        projectPath = gameJson.getPath().substring(0, gameJson.getPath().lastIndexOf(File.separator));

        // Load game json
        Json json = new Json();
        json.setIgnoreUnknownFields(true);
        game = json.fromJson(NyaGame.class, new FileHandle(gameJson));

        // Load room bgs

    }

    /**
     * Create new project
     * @param directory Project root directory
     */
    public static void newProject(File directory) throws IOException {
        File newGame = new File(directory, "game.json");
        newGame.createNewFile();

        // Write new game.json
        Json json = new Json();
        json.setIgnoreUnknownFields(true);
        json.toJson(new NyaGame(), new FileHandle(newGame));

        // Create project folders
        for( String dir : projectFolders ){
            File dirFile = new File(directory, dir);
            dirFile.mkdir();
        }

        current = new Project(newGame);
    }


    /**
     * Open a project
     * @param gameJson
     */
    public static void open(File gameJson){
        current = new Project(gameJson);
    }

    /**
     * @return Game data
     */
    public NyaGame getGame() {
        return game;
    }

    /**
     * Copy a file to resource folder
     * @param path
     * @param file
     */
    public void copyTo(String path, File file) throws IOException {
        // No copying for files already inside
        if(file.getAbsolutePath().contains(projectPath)) return;
        File newFile = new File(projectPath+path+file.getName());
        Files.copy(Paths.get(file.getAbsolutePath()), Paths.get(newFile.getAbsolutePath()));
    }

    /**
     * Get a resource file
     * @param relPath
     * @return
     */
    public File getResource(String relPath) {
        return new File(projectPath+relPath);
    }
}
