package com.lksoft.nyaage.player.gamestate.commands;

import com.badlogic.gdx.Gdx;
import com.lksoft.nyaage.player.Nya;
import com.lksoft.nyaage.player.gamestate.ScriptState;

import org.luaj.vm2.LuaValue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by lake on 15/01/06.
 *
 * Utility to run script-asynch commands
 */
public class CommandRunner {
    // Current cutscene
    private Cutscene cutscene;

    // Temporary display objects
    private List<ScriptCommand> commands = new ArrayList<>();

    /**
     * Update all running commands
     */
    public void update(){
        ScriptCommand resume = null;
        Iterator it = commands.iterator();
        while( it.hasNext() ){
            ScriptCommand o = (ScriptCommand) it.next();
            if( o.update() ){
                it.remove();

                // Resume if blocking!
                if( o.isBlocking() ){
                    resume = o;
                }

                // Finish callback
                o.onFinish();
            }
        }

        // If blocking, resume script
        if( resume != null  ){
            Gdx.app.log("nya", resume.getClass().getSimpleName()
                    +" finished! resuming "+resume.getCallerFunction());
            resume.resume();
        }
    }

    /**
     * Draw commands where needed
     */
    public void draw(){
        for( ScriptCommand o : commands){
            o.draw();
        }
    }

    /**
     * Start a new command
     * @param command
     */
    public void start(ScriptCommand command, boolean blocking){
        // If skipping, just skip
        if( cutscene != null && cutscene.isSkipping() ){
            // Nothing to skip
            return;
        }

        if( blocking ){
            command.setCallerScript(ScriptState.lastCalledScript);
            command.setCallerFunction(ScriptState.lastCalledFunction);
            commands.add(command);
            Nya.get().getGameState().getGlobals().yield(LuaValue.NONE);
        }
        else {
            // Add the command to the list
            commands.add(command);
        }
    }


    /**
     * Begin a cutscene
     */
    public void beginCutscene() {
        cutscene = new Cutscene();
    }

    /**
     * End the cutscene
     */
    public void endCutscene() {
        cutscene = null;
    }

    /**
     * @return Whether a cutscene is going on
     */
    public boolean isCutscene() {
        return cutscene != null;
    }

    /**
     * Skip all the commands
     */
    public void cutCutscene() {
        // Set skip status
        cutscene.setSkipping(true);

        // Skip current commands
        ScriptCommand resume = null;
        for( ScriptCommand c : commands ){
            c.skip();

            // Resume if needed
            if( c.isBlocking() ){
                resume = c;
            }
        }

        // Empty command list
        commands.clear();

        // Resume scriptand skip all commands till nya:endCutscene
        if( resume != null ){
            resume.resume();
        }
    }

    /**
     * On touch
     */
    public void onTouch() {
        ScriptCommand resume = null;

        Iterator<ScriptCommand> it = commands.iterator();
        while(it.hasNext()){
            ScriptCommand c = it.next();
            if( c.onTouch() ){
                it.remove();

                // Callback
                c.onFinish();
                // Resume
                if( c.isBlocking() ) resume = c;
            }
        }

        if( resume != null ) {
            resume.resume();
        }
    }

    /**
     * Interrupt specified command
     * @param command
     */
    public void interrupt(ScriptCommand command) {
        commands.remove(command);
    }
}
