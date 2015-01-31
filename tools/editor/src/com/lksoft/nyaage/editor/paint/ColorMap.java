package com.lksoft.nyaage.editor.paint;


import javafx.scene.paint.Color;

/**
 * Created by Stallman on 31/01/2015.
 */
public class ColorMap {
    private static Color[] colorMap = {
            Color.color(0, 0, 0, 0),
            Color.GREEN,
            Color.CYAN,
            Color.MAGENTA,
            Color.MAROON,
            Color.PINK,
            Color.PURPLE,
            Color.YELLOW,
            Color.ORANGE,
            Color.NAVY,
            Color.TEAL,
            Color.WHITE,
            Color.DARKGOLDENROD
    };

    /**
     * Get the paletted color indexed by specified value
     * @param index
     * @return
     */
    public static Color getColor(int index){
        return colorMap[index];
    }
}
