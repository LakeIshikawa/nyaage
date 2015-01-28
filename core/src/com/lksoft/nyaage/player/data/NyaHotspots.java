package com.lksoft.nyaage.player.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lake on 14/12/29.
 */
public class NyaHotspots {
    // Hotspots map
    private String hotspotMap;
    // Hotspot display names
    private List<NyaHotspot> hotspots = new ArrayList<>();

    public String getHotspotMap() {
        return hotspotMap;
    }

    public void setHotspotMap(String hotspotsMap) {
        this.hotspotMap = hotspotsMap;
    }

    public List<NyaHotspot> getHotspots() {
        return hotspots;
    }

    public void setHotspots(List<NyaHotspot> hotspots) {
        this.hotspots = hotspots;
    }
}
