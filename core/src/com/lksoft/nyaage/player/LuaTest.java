package com.lksoft.nyaage.player;

import com.badlogic.gdx.Gdx;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.JsePlatform;
import org.luaj.vm2.luajc.LuaJC;

/**
 * Created by lake on 14/12/24.
 */
public class LuaTest {
    public static void luaTest() {

        Globals g = JsePlatform.standardGlobals();
        LuaJC.install(g);

        g.set("character", CoerceJavaToLua.coerce(new SomeShit(g)));
        g.load(Gdx.files.internal("test.lua").reader(), "test.lua").call();
        g.load("co = coroutine.wrap(onLookAt)").call();
        g.get("co").call();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        g.get("co").call();

    }

    public static class SomeShit{

        Globals g;

        public SomeShit(Globals g){
            this.g = g;
        }

        public void walkTo(int x, int y) {
            System.out.println("Started walking.");
            g.yield(LuaValue.NONE);
        }
    }


    public static class SomeUserClass {
        public String toString() {
            return "user-class-instance-"+this.hashCode();
        }
        public SomeShit itWorks = new SomeShit(null);
    }
}
