package com.lksoft.nyaage.player.gamestate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.lksoft.astar.bytemap.ByteMap;
import com.lksoft.astar.pathfind.SquareBoard;
import com.lksoft.astar.pathfind.SquareBoardIO;
import com.lksoft.nyaage.player.Nya;
import com.lksoft.nyaage.player.data.NyaHotspot;
import com.lksoft.nyaage.player.data.NyaObject;
import com.lksoft.nyaage.player.data.NyaOverlay;
import com.lksoft.nyaage.player.data.NyaRoom;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lake on 14/12/24.
 */
public class RoomState {
    // Backgrounds
    private Animation background;
    // Squareboards
    private SquareBoard squareboard;
    // Walkable area status
    private boolean[] walkAreaStatus = new boolean[255];
    // Overlay state
    private List<OverlayState> overlays = new ArrayList<>();
    // Objects state
    private List<ObjectState> objects = new ArrayList<>();
    // Hotspot map
    private ByteMap hotspotMap;
    // Hotspots
    private List<HotspotState> hotspots = new ArrayList<>();
    // Room script
    private ScriptState script;
    // Room id
    private int roomId;

    /**
     * Cache resources
     * @param newRoom
     * @param room
     * @param globals
     */
    public RoomState(int newRoom, NyaRoom room, Globals globals){
        this.roomId = newRoom;

        // Create bg animation
        if( room.getBackgrounds() != null ) {
            List<TextureRegion> backgrounds = new ArrayList<>();
            for (String bg : room.getBackgrounds()) {
                TextureAtlas atlas = Nya.get().getAssetManager().get("image/images.atlas", TextureAtlas.class);
                backgrounds.add(atlas.findRegion(bg));
            }

            if (!backgrounds.isEmpty()) {
                background =
                        new Animation(
                                room.getBgFrameTime(),
                                backgrounds.toArray(new TextureRegion[backgrounds.size()]));
                background.setPlayMode(Animation.PlayMode.LOOP);
            }
        }

        // Load squareboard
        if( room.getWalkMap() != null ) {
            squareboard = SquareBoardIO.loadSQB(Gdx.files.internal(room.getWalkMap()).read());
        }

        // Initialize walk areas
        for( int i=0; i<walkAreaStatus.length; i++ ){
            walkAreaStatus[i] = true;
        }

        // Load overlays
        if( room.getOverlays() != null ) {
            for (NyaOverlay o : room.getOverlays()) {
                overlays.add(new OverlayState(this, o));
            }
        }

        // Load objects
        if( room.getObjects() != null ){
            for(NyaObject o : room.getObjects()){
                objects.add(new ObjectState(o));
            }
        }

        // Load hotspot map
        if( room.getHotspots() != null ) {
            hotspotMap = new ByteMap(Gdx.files.internal(room.getHotspots().getHotspotMap()).readBytes());
            // Load hotspot states
            for (NyaHotspot h : room.getHotspots().getHotspots()) {
                getHotspots().add(new HotspotState(h, this));
            }
        }

        // Unbind all room-events
        globals.set("afterLoad", LuaValue.NIL);
        globals.set("afterFade", LuaValue.NIL);

        // Bind objects
        for( ObjectState o : objects ){
            globals.set(o.getObject().getScriptId(), CoerceJavaToLua.coerce(o));
        }

        // Load room script
        if( room.getScript() != null ){
            script = new ScriptState(globals, Gdx.files.internal(room.getScript()));
        }
    }

    /**
     * Trigger an event
     * @param functionName Function name to be called
     */
    public void triggerEvent(String functionName) {
        script.callFunction(functionName, null);
    }

    /**
     * Draw the room
     */
    public void draw() {
        if( background != null ) {
            TextureRegion frame = getBackground().getKeyFrame(Gdx.graphics.getDeltaTime());
            Nya.get().getSpriteBatch().draw(frame, 0, 0);
        }
    }

    /**
     * @return Room id
     */
    public int getRoomId() {
        return roomId;
    }

    /**
     * @return The squareboard
     */
    public SquareBoard getSquareboard(){
        return squareboard;
    }

    /**
     * @return Walk area status array
     */
    public boolean[] getWalkAreaStatus() {
        return walkAreaStatus;
    }

    /**
     * @return Overlay objects
     */
    public List<OverlayState> getOverlays() {
        return overlays;
    }

    /**
     * @return The bg animation
     */
    public Animation getBackground() {
        return background;
    }

    /**
     * @return Hotspots
     */
    public ByteMap getHotspotMap() {
        return hotspotMap;
    }

    public List<HotspotState> getHotspots() {
        return hotspots;
    }

    public List<ObjectState> getObjects() {
        return objects;
    }
}
