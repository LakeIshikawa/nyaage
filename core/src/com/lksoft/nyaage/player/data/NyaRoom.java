package com.lksoft.nyaage.player.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lake on 14/12/21.
 */
public class NyaRoom {
    // Room name
    private String name;
    // Background atlas region names (one per frame)
    private List<String> backgrounds = new ArrayList<>();
    // WalkMap
    private String walkMap;
    // Overlays
    private List<NyaOverlay> overlays = new ArrayList<>();
    // Hotspots
    private NyaHotspots hotspots;
    // Objects
    private List<NyaObject> objects = new ArrayList<>();
    // Script
    private String script;
    // Frame time
    private float bgFrameTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getBackgrounds() {
        return backgrounds;
    }

    public void setBackgrounds(List<String> backgrounds) {
        this.backgrounds = backgrounds;
    }

    public float getBgFrameTime() {
        return bgFrameTime;
    }

    public void setBgFrameTime(float bgFrameTime) {
        this.bgFrameTime = bgFrameTime;
    }

    public String getWalkMap() {
        return walkMap;
    }

    public void setWalkMap(String walkMap) {
        this.walkMap = walkMap;
    }

    public List<NyaOverlay> getOverlays() {
        return overlays;
    }

    public void setOverlays(List<NyaOverlay> overlays) {
        this.overlays = overlays;
    }

    public NyaHotspots getHotspots() {
        return hotspots;
    }

    public void setHotspots(NyaHotspots hotspots) {
        this.hotspots = hotspots;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public List<NyaObject> getObjects() {
        return objects;
    }

    public void setObjects(List<NyaObject> objects) {
        this.objects = objects;
    }
}
