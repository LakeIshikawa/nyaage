package com.lksoft.nyaage.editor.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Created by Stallman on 31/01/2015.
 */
public class RectanglePaintTool implements PaintTool {
    private GraphicsContext gc;

    public RectanglePaintTool(GraphicsContext graphicsContext2D) {
        this.gc = graphicsContext2D;
    }

    @Override
    public void onMouseClicked(Color color, int x, int y) {

    }

    @Override
    public void onMouseReleased(Color color, int x, int y) {

    }

    @Override
    public void onMouseDragged(Color color, int x, int y) {

    }
}
