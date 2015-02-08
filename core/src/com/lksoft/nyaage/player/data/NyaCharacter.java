package com.lksoft.nyaage.player.data;

/**
 * Created by lake on 14/12/21.
 */
public class NyaCharacter {


    // Display name
    private String displayName;
    // Script id
    private String scriptId;

    // View
    private String viewFront;
    private String viewSide;
    private String viewBack;

    // Base scale
    private float baseScale;

    // Starting room
    private int startRoom;

    // Walking speed
    private float walkingSpeedVer;
    private float walkingSpeedHor;

    // Starting position
    private float startX;
    private float startY;

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

    public int getStartRoom() {
        return startRoom;
    }

    public void setStartRoom(int startRoom) {
        this.startRoom = startRoom;
    }

    public float getBaseScale() {
        return baseScale;
    }

    public void setBaseScale(float baseScale) {
        this.baseScale = baseScale;
    }

    public String getViewFront() {
        return viewFront;
    }

    public void setViewFront(String viewFront) {
        this.viewFront = viewFront;
    }

    public String getViewSide() {
        return viewSide;
    }

    public void setViewSide(String viewSide) {
        this.viewSide = viewSide;
    }

    public String getViewBack() {
        return viewBack;
    }

    public void setViewBack(String viewBack) {
        this.viewBack = viewBack;
    }

    public float getWalkingSpeedVer() {
        return walkingSpeedVer;
    }

    public void setWalkingSpeedVer(float walkingSpeedVer) {
        this.walkingSpeedVer = walkingSpeedVer;
    }

    public float getWalkingSpeedHor() {
        return walkingSpeedHor;
    }

    public void setWalkingSpeedHor(float walkingSpeedHor) {
        this.walkingSpeedHor = walkingSpeedHor;
    }
}
