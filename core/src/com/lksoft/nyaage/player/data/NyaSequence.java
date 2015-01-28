package com.lksoft.nyaage.player.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lake on 14/12/25.
 */
public class NyaSequence {
    // A list of frames
    private List<NyaFrame> frames = new ArrayList<>();
    // Frame time
    private float frameTime;


    public List<NyaFrame> getFrames() {
        return frames;
    }

    public float getFrameTime() {
        return frameTime;
    }

    public void setFrameTime(float frameTime) {
        this.frameTime = frameTime;
    }
}
