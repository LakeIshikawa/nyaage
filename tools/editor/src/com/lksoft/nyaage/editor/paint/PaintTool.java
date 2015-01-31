package com.lksoft.nyaage.editor.paint;

import javafx.scene.paint.Color;

/**
 * Created by Stallman on 31/01/2015.
 * Abstract canvas paint tool
 *
 */
public interface PaintTool {
    public void onMouseClicked(Color color, int x, int y);
    public void onMouseReleased(Color color, int x, int y);
    public void onMouseDragged(Color color, int x, int y);
}
