package com.lksoft.nyaage.player.gamestate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.lksoft.astar.pathfind.SquareBoard;
import com.lksoft.nyaage.player.Nya;
import com.lksoft.nyaage.player.common.Direction;
import com.lksoft.nyaage.player.data.NyaCharacter;
import com.lksoft.nyaage.player.gamestate.commands.Say;
import com.lksoft.nyaage.player.gamestate.commands.WalkTo;

/**
 * Created by lake on 14/12/21.
 */
public class CharacterState implements DrawableState {
    // Basic data
    private NyaCharacter character;
    // Current room
    private int room;

    // view
    private ViewState view;

    // Walking status
    private WalkTo walking;
    // Talking status
    private Say talking;

    /**
     * Make new character state from spec
     * @param character spec
     */
    public CharacterState(NyaCharacter character){
        this.character = character;

        // Set current room
        room = character.getStartRoom();

        // Load View
        view = new ViewState(character);
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public NyaCharacter getCharacter() {
        return character;
    }

    /**
     * Face to character
     * @param character The character to face to
     */
    public void faceCharacter(CharacterState character){
        int dx = (int)(view.getPosition().x - view.getPosition().x);
        int dy = (int)(view.getPosition().y - view.getPosition().y);

        if( Math.abs(dx) > Math.abs(dy) ){
            if( dx > 0 ) view.faceDirection(Direction.LEFT);
            else view.faceDirection(Direction.RIGHT);
        } else {
            if( dy > 0 ) view.faceDirection(Direction.DOWN);
            else view.faceDirection(Direction.UP);
        }
    }

    /**
     * Change room and position
     * @param room
     * @param x
     * @param y
     */
    public void changeRoom(int room, int x, int y){
        setRoom(room);
        view.setPosition(x, y);
    }

    /**
     * Blocking walkTo
     * @param x
     * @param y
     */
    public void walkTo(int x, int y){
        walkTo(x, y, true);
    }
    /**
     * Set a walk to action
     * @param x Destination
     * @param y Destination
     */
    public void walkTo(int x, int y, boolean blocking){
        Gdx.app.log("NYA", "Walk to: "+x+", "+y);
        SquareBoard sqb = Nya.get().getGameState().getCurrentRoom().getSquareboard();
        boolean[] walkAreaStatus = Nya.get().getGameState().getCurrentRoom().getWalkAreaStatus();

        // Interrupt previous command
        if( walking != null ) {
            Nya.get().getGameState().getCommandRunner().interrupt(walking);
        }

        // Make new command
        walking = new WalkTo(sqb, walkAreaStatus, new Vector2(x, y), this);
        Nya.get().getGameState().getCommandRunner().start(walking, blocking);
    }

    /**
     * End walking
     */
    public void endWalking() {
        walking = null;
    }

    /**
     * Blocking say
     * @param text
     */
    public void say(String text){
        say(text, true);
    }
    /**
     * Say something
     * @param text Words
     */
    public void say(String text, boolean blocking){
        Gdx.app.log("NYA", character.getDisplayName()+" : "+text);

        // Interrupt previous command
        if( talking != null ) {
            Nya.get().getGameState().getCommandRunner().interrupt(talking);
        }

        // Make new command
        talking = new Say(this, text, Nya.get().getGameState().getGame().getDefaultFont());
        Nya.get().getGameState().getCommandRunner().start(talking, blocking);
    }

    /**
     * End the say command
     */
    public void endSay() {
        talking = null;
    }


    /**
     * Frame update
     */
    public void update() {
        view.update();
    }

    @Override
    public void draw() {
        view.draw();
    }

    @Override
    public int getBaseline() {
        return view.getBaseline();
    }
}
