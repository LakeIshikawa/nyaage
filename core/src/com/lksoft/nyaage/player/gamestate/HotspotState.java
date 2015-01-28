package com.lksoft.nyaage.player.gamestate;

import com.lksoft.nyaage.player.data.NyaHotspot;

/**
 * Created by lake on 14/12/29.
 */
public class HotspotState implements  ScriptableState {

    // Data reference
    private NyaHotspot hotspot;
    // Parent room
    private RoomState parentRoom;

    public HotspotState(NyaHotspot hotspot, RoomState roomState) {
        this.hotspot = hotspot;
        this.parentRoom = roomState;
    }


    @Override
    public void interact(String type) {
        parentRoom.triggerEvent(hotspot.getScriptName()+"_"+type);
    }
}
