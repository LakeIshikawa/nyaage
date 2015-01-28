package com.lksoft.nyaage.player.gamestate.commands;

import com.lksoft.nyaage.player.gamestate.ScriptState;

/**
 * Created by lake on 15/01/06.
 */
public abstract class ScriptCommand {

    // Caller state
    private ScriptState callerScript;
    private String callerFunction;

    // Skipped
    private boolean skipped;

    // Interface definition
    public abstract boolean doUpdate();
    public abstract void draw();
    public abstract void doSkip();
    public abstract void onFinish();

    /**
     * @return Whether the command should be skipped or not
     */
    public boolean onTouch(){
        return false;
    }

    /**
     * Update if not already skipped
     */
    public final boolean update(){
        if( !skipped ) return doUpdate();
        else return true;
    }

    /**
     * Skip
     */
    public final void skip(){
        skipped = true;
        doSkip();

        // Callback
        onFinish();
    }

    /**
     * Resume the caller script
     */
    public void resume(){
        if( getCallerScript() != null ){
            ScriptState.lastCalledScript = getCallerScript();
            ScriptState.lastCalledFunction = getCallerFunction();
            getCallerScript().resumeFunction(getCallerFunction());
        }
    }

    /**
     * @return Whether the command is blocking
     */
    public boolean isBlocking(){
        return callerScript != null;
    }

    public ScriptState getCallerScript() {
        return callerScript;
    }

    public void setCallerScript(ScriptState callerScript) {
        this.callerScript = callerScript;
    }

    public String getCallerFunction() {
        return callerFunction;
    }

    public void setCallerFunction(String callerFunction) {
        this.callerFunction = callerFunction;
    }
}
