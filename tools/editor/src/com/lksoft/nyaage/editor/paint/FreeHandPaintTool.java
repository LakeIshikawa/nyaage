package com.lksoft.nyaage.editor.paint;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * Created by Stallman on 31/01/2015.
 */
public class FreeHandPaintTool implements PaintTool {
    private GraphicsContext gc;

    public FreeHandPaintTool(GraphicsContext gc){
        this.gc = gc;
    }


    @Override
    public void onMouseClicked(Color color, int x, int y) {

    }

    @Override
    public void onMouseReleased(Color color, int x, int y) {

    }

    @Override
    public void onMouseDragged(Color color, int x, int y) {
        gc.setFill(color);
        gc.fillOval(x, y, 5, 5);
    }
}
