package com.lksoft.nyaage.player.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lake on 14/12/21.
 */
public class NyaGame {

    // Rooms
    private List<NyaRoom> rooms = new ArrayList<>();
    // Characters
    private List<NyaCharacter> characters = new ArrayList<>();

    // Default font
    private String defaultFont;

    // Main script
    private String mainScript;

    // Start room
    private int startRoom = 0;
    private int protagonist = 0;

    public List<NyaRoom> getRooms() {
        return rooms;
    }

    public void setRooms(List<NyaRoom> rooms) {
        this.rooms = rooms;
    }

    public List<NyaCharacter> getCharacters() {
        return characters;
    }

    public void setCharacters(List<NyaCharacter> characters) {
        this.characters = characters;
    }

    public int getStartRoom() {
        return startRoom;
    }

    public void setStartRoom(int startRoom) {
        this.startRoom = startRoom;
    }

    public int getProtagonist() {
        return protagonist;
    }

    public void setProtagonist(int protagonist) {
        this.protagonist = protagonist;
    }

    public String getMainScript() {
        return mainScript;
    }

    public void setMainScript(String mainScript) {
        this.mainScript = mainScript;
    }

    public String getDefaultFont() {
        return defaultFont;
    }

    public void setDefaultFont(String defaultFont) {
        this.defaultFont = defaultFont;
    }
}
