package com.lksoft.nyaage.player.com.lksoft.luagdx;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Comparator;

/**
 * Created by lake on 14/12/29.
 */
public class ZSprite extends Sprite {
    // Comparator
    public static Comparator<? super ZSprite> comparator = new Comparator<ZSprite>() {
        @Override
        public int compare(ZSprite o1, ZSprite o2) {
            if (o1.getZ() > o2.getZ() ) return -1;
            if (o1.getZ() < o2.getZ() ) return 1;
            return 0;
        }
    };

    // The priority z
    private int z;

    // Constructors
    public ZSprite(Texture texture){
        super(texture);
    }
    public ZSprite(Texture texture, int w, int h){
        super(texture, w, h);
    }
    public ZSprite(Texture texture, int x, int y, int w, int h){
        super(texture, x, y, w, h);
    }
    public ZSprite(Sprite s){
        super(s);
    }
    public ZSprite(TextureRegion region){
        super(region);
    }
    public ZSprite(TextureRegion region, int x, int y, int w, int h){
        super(region, x, y, w, h);
    }

    public int getZ() {
        return z;
    }
    public void setZ(int z) {
        this.z = z;
    }
}
