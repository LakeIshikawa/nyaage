package com.lksoft.nyaage.player.data;

/**
 * Created by lake on 15/01/07.
 */
public class NyaObject {

    // Display name
    private String displayName;
    // Script id
    private String scriptId;

    // Default image (when no view is locked)
    private String defaultImage;

    // Start position
    private float startX;
    private float startY;


    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getScriptId() {
        return scriptId;
    }

    public void setScriptId(String scriptId) {
        this.scriptId = scriptId;
    }

    public String getDefaultImage() {
        return defaultImage;
    }

    public void setDefaultImage(String defaultImage) {
        this.defaultImage = defaultImage;
    }

    public float getStartX() {
        return startX;
    }

    public void setStartX(float startX) {
        this.startX = startX;
    }

    public float getStartY() {
        return startY;
    }

    public void setStartY(float startY) {
        this.startY = startY;
    }
}
