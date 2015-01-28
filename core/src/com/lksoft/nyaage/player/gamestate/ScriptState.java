package com.lksoft.nyaage.player.gamestate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

/**
 * Created by lake on 14/12/25.
 */
public class ScriptState {

    // Reference to globals
    Globals globals;

    // For coroutine callbacks
    public static ScriptState lastCalledScript;
    public static String lastCalledFunction;

    /**
     * Compile script
     * @param scriptFile Script filename
     */
    public ScriptState(Globals globals, FileHandle scriptFile){
        this.globals = globals;

        // Compile and execute script
        globals.load(scriptFile.reader(), scriptFile.name()).call();
    }

    /**
     * Call a script function
     * @param function Function name as declared in script
     */
    public void callFunction(String function, Object[] args){
        // If function does not exist, terminate
        if( globals.get(function) == LuaValue.NIL ) return;

        Gdx.app.log("nya", "Calling: "+function);

        // Wrap with coroutine
        String coroutine = function+"_co";
        globals.load(coroutine+" = coroutine.wrap("+function+")").call();

        // Save caller state
        lastCalledScript = this;
        lastCalledFunction = coroutine;

        // No args
        if( args == null ) {
            globals.get(coroutine).call();
            return;
        }

        // Args
        LuaValue[] largs = new LuaValue[args.length];
        for( int i=0; i<args.length; i++ ){
            largs[i] = CoerceJavaToLua.coerce(args[i]);
        }
        globals.get(coroutine).invoke(largs);
    }

    /**
     * Resume a yielded corouting
     * @param function The function name
     */
    public void resumeFunction(String function) {
        globals.get(function).call();
    }
}
