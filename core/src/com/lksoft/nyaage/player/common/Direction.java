package com.lksoft.nyaage.player.common;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by lake on 14/12/26.
 */
public enum Direction {
    RIGHT("down"),
    DOWN("down"),
    LEFT("down"),
    UP("down");

    private String name;
    Direction(String name){
        this.name = name;
    }

    public static Direction fromSpeed(Vector2 speed) {
        if( Math.abs(speed.x) > Math.abs(speed.y) ){
            return speed.x>0? RIGHT : LEFT;
        } else {
            return speed.y>0? DOWN : UP;
        }
    }

    @Override
    public String toString(){
        return name;
    }
}
