package com.lksoft.astar.bytemap;

/**
 * Created by lake on 14/12/26.
 *
 * An accessible byte map loaded from file
 */
public class ByteMap {
    // Data
    private byte[] pixels;
    // Pixel data start
    private int dataStart;
    // Width
    private int width;
    // Height
    private int height;
    // Stride
    private int stride;

    /**
     * Load from file
     * @param bytes The byte-data of a 8bit bitmap
     */
    public ByteMap(byte[] bytes){
        this.pixels = bytes;
        dataStart = getInt(pixels, 10);
        width = getInt(pixels, 18);
        height = getInt(pixels, 22);

        // Round stride to 4 multiples
        stride = getWidth();
        if( stride%4 != 0 ) {
            stride = stride + (4 - stride % 4);
        }
    }

    /**
     * Get a pixel byte
     * @param x
     * @param y
     * @return
     */
    public int getPixel(int x, int y){
        return pixels[dataStart + y*stride + x];
    }

    /**
     * Get a 4 bytes int from buffer
     * @param data buffer
     * @param pos starting pos
     * @return
     */
    private static int getInt(byte[] data, int pos){
        return (int)((data[pos+3] << 24)&0xFF000000)
                + (int)((data[pos+2] << 16)&0xFF0000)
                + (int)((data[pos+1] << 8) & 0xFF00)
                + (int)(data[pos]&0xFF);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
