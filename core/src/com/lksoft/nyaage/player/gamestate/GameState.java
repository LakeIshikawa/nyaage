package com.lksoft.nyaage.player.gamestate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector3;
import com.lksoft.nyaage.player.Nya;
import com.lksoft.nyaage.player.common.Direction;
import com.lksoft.nyaage.player.data.NyaCharacter;
import com.lksoft.nyaage.player.data.NyaGame;
import com.lksoft.nyaage.player.gamestate.commands.CommandRunner;
import com.lksoft.nyaage.player.gamestate.commands.DisplayText;
import com.lksoft.nyaage.player.gamestate.commands.Wait;

import org.luaj.vm2.Globals;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.JsePlatform;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by lake on 14/12/21.
 */
public class GameState {

    // Game data
    private NyaGame game;

    // Characters
    private List<CharacterState> characters = new ArrayList<>();

    // CommandRunner
    private CommandRunner commandRunner = new CommandRunner();

    // Script for input
    private ScriptState mainScript;

    // Global script state
    private Globals globals;

    // Room
    private RoomState currentRoom;
    private RoomChange roomChange;

    // Camera player
    private int cameraPlayer;


    // Temp list of things to draw
    private List<DrawableState> toDraw = new ArrayList<>();

    // Touch position
    private Vector3 touchPos = new Vector3();

    /**
     * Create new game state from data definition
     * @param game Game data
     * @return
     */
    public static GameState create(NyaGame game) {
        return new GameState(game);
    }

    /**
     * New game state from data
     * @param game
     */
    private GameState(NyaGame game) {
        this.game = game;

        // Initialize script engine
        initializeScript();

        // Load all characters
        for( NyaCharacter c : game.getCharacters() ){
            characters.add(new CharacterState(c));
        }

        // Initialize script engine
        bindStandardObjects();

        // Parse all scripts
        mainScript = new ScriptState(globals, Gdx.files.internal(game.getMainScript()));

        // Set the room and player
        changeRoom(game.getStartRoom());
        setCameraPlayer(game.getProtagonist());
    }

    /**
     * Initialize script and bindings
     */
    private void initializeScript() {
        globals = JsePlatform.standardGlobals();
        //LuaJC.install(globals);
    }

    /**
     * Bind some useful stuff
     */
    private void bindStandardObjects() {
        // Bind classes
        globals.set("Direction", CoerceJavaToLua.coerce(Direction.class));

        // Bind characters
        for( CharacterState c : characters ){
            globals.set(c.getCharacter().getScriptId(), CoerceJavaToLua.coerce(c));
        }

        // Bind protagonist
        globals.set("protagonist", CoerceJavaToLua.coerce(characters.get(game.getProtagonist())));

        // Bind game state
        globals.set("nya", CoerceJavaToLua.coerce(this));
    }

    /**
     * Frame update
     */
    public void update() {

        // Cutscene skipping
        if( Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) && getCommandRunner().isCutscene() ){
            getCommandRunner().cutCutscene();
            return;
        }

        // Room change (fade out, change, fade in, end)
        if( roomChange != null ){
            if( roomChange.update() ){
                roomChange = null;
                getCurrentRoom().triggerEvent("afterFade");
            }

            // No updating anything on room change
            return;
        }

        // Input handling
        if( Gdx.input.justTouched() ){
            // Cutscene - trigger touch to commands
            if( getCommandRunner().isCutscene() ){
                getCommandRunner().onTouch();
            }

            // Normal input handling
            else {
                touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
                Nya.get().getViewport().getCamera().unproject(touchPos);

                mainScript.callFunction(
                        "onTouchDown",
                        new Object[]{touchPos.x, touchPos.y}
                );
            }
        }

        // Debug
        DebugState state = Nya.get().getDebugState();
        if( Gdx.input.isKeyJustPressed(Input.Keys.G) ){
            state.setDrawGizmos(!state.isDrawGizmos());
        }

        // Update characters
        for (CharacterState cha : characters) {
            cha.update();
        }

        // Update commands
        getCommandRunner().update();
    }

    /**
     * Draw current game state
     */
    public void draw() {
        if( currentRoom == null ) return;

        // Draw room background
        currentRoom.draw();

        // Save list of object to draw, to sort later
        toDraw.clear();

        // Draw all characters in room
        for (CharacterState cha : characters) {
            if (cha.getRoom() == getCurrentRoom().getRoomId()) {
                toDraw.add(cha);
            }
        }

        // Draw all objects in room
        for( ObjectState o : getCurrentRoom().getObjects() ){
            if( o.isVisible() ) {
                toDraw.add(o);
            }
        }

        // Draw overlays
        for( OverlayState o : currentRoom.getOverlays() ){
            toDraw.add(o);
        }

        // Sort by baseline
        Collections.sort(toDraw, new Comparator<DrawableState>() {
            @Override
            public int compare(DrawableState o1, DrawableState o2) {
                if(o1.getBaseline() > o2.getBaseline()) return -1;
                if(o1.getBaseline() < o2.getBaseline()) return 1;
                return 0;
            }
        });

        // Draw everything
        for( DrawableState s : toDraw ){
            s.draw();
        }

        // Draw display-objects on top of everything
        getCommandRunner().draw();
    }


    /**
     * Get thing at x,y
     * @param x
     * @param y
     * @return
     */
    public Object getThingAt(int x, int y){
        // Search hotspots
        int hotspot = currentRoom.getHotspotMap().getPixel(x, y);
        if( hotspot != 0 ){
            return currentRoom.getHotspots().get(hotspot-1);
        }

        return null;
    }

    /**
     * Measures text width for default font
     * @param text Text
     * @return
     */
    public int textWidth(String text){
        return (int)Nya.get().getAssetManager().get(getGame().getDefaultFont(), BitmapFont.class).getBounds(text).width;
    }

    /**
     * Measures text height for default font
     * @param text Text
     * @return
     */
    public int textHeight(String text){
        return (int)Nya.get().getAssetManager().get(getGame().getDefaultFont(), BitmapFont.class).getBounds(text).height;
    }

    /**
     * Display arbitrary text
     * @param text The text
     * @param x Position
     * @param y Position
     */
    public void displayText(String text, int x, int y){
        displayText(text, x, y, true);
    }

    /**
     * Display arbitrary text
     * @param text The text
     * @param x Position
     * @param y Position
     * @param blocking Yields the script thread or not
     */
    public void displayText(String text, int x, int y, boolean blocking){
        getCommandRunner().start(new DisplayText(text, x, y, getGame().getDefaultFont()), blocking);
    }

    /**
     * Wait (yield) for some time
     * @param time
     */
    public void wait(float time){
        getCommandRunner().start(new Wait(time), true);
    }

    /**
     * @return World width
     */
    public int getScreenWidth(){
        return (int)Nya.get().getViewport().getWorldWidth();
    }

    /**
     * @return World height
     */
    public int getScreenHeight(){
        return (int)Nya.get().getViewport().getWorldHeight();
    }

    /**
     * Change current room
     * @param newRoom The new room to make current
     */
    public void changeRoom(int newRoom){
        roomChange = new RoomChange(newRoom);
    }

    /**
     * Begin a cutscene with current script
     */
    public void beginCutscene() {
        getCommandRunner().beginCutscene();
    }

    /**
     * End current cutscene
     */
    public void endCutscene() {
        getCommandRunner().endCutscene();
    }


    /**
     * Instantly load specified room
     * @param room
     */
    void loadRoom(int room){
        currentRoom = new RoomState(room, game.getRooms().get(room), globals);
        // Room enter event
        getCurrentRoom().triggerEvent("afterLoad");
    }

    public int getCameraPlayer() {
        return cameraPlayer;
    }

    public void setCameraPlayer(int cameraPlayer) {
        this.cameraPlayer = cameraPlayer;
    }

    /**
     * @return The current room
     */
    public RoomState getCurrentRoom() {
        return currentRoom;
    }

    /**
     * @return Script globals
     */
    public Globals getGlobals() {
        return globals;
    }

    /**
     * @return Game data
     */
    public NyaGame getGame() {
        return game;
    }

    /**
     * @return Script runner
     */
    public CommandRunner getCommandRunner() {
        return commandRunner;
    }
}
