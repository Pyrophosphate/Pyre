package com.brandonoium.bithorse;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BitTermCharSet {
    protected boolean[][] buffer;
    private int bufferw, bufferh;

    private int charw, charh;


    public BitTermCharSet(int bufferWidth, int bufferHeight, int charWidth, int charHeight) throws InvalidCharSetException {
        if(bufferWidth < 0 || charWidth < 0) {
            throw new InvalidCharSetException("Invalid width of buffer or character.");
        }
        if(bufferHeight < 0 || charHeight < 0) {
            throw new InvalidCharSetException("Invalid height of buffer or character.");
        }

        if(bufferWidth % charWidth != 0) {
            throw new InvalidCharSetException("Buffer width must be an integer multiple of character width.");
        }
        if(bufferHeight % charHeight != 0) {
            throw new InvalidCharSetException("Buffer height must be an integer multiple of character height.");
        }

        bufferw = bufferWidth;
        bufferh = bufferHeight;
        charw = charWidth;
        charh = charHeight;

        buffer = new boolean[bufferh][bufferw];
    }

    public BitTermCharSet(BufferedImage source, int charWidth, int charHeight, Color maskColor) throws InvalidCharSetException {
        bufferw = source.getWidth();
        bufferh = source.getHeight();

        if(bufferw < 0 || charWidth < 0) {
            throw new InvalidCharSetException("Invalid width of buffer or character.");
        }
        if(bufferh < 0 || charHeight < 0) {
            throw new InvalidCharSetException("Invalid height of buffer or character.");
        }

        if(bufferw % charWidth != 0) {
            throw new InvalidCharSetException("Buffer width must be an integer multiple of character width.");
        }
        if(bufferh % charHeight != 0) {
            throw new InvalidCharSetException("Buffer height must be an integer multiple of character height.");
        }

        charw = charWidth;
        charh = charHeight;

        buffer = new boolean[bufferh][bufferw];

        int rgb = maskColor.getRGB();

        for(int y = 0; y < bufferh; y++) {
            for(int x = 0; x < bufferw; x++) {
                buffer[y][x] = (source.getRGB(x, y) == rgb);
            }
        }
    }


    public boolean[][] getGlyphAtLocation(int charX, int charY) {
        boolean[][] glyph = new boolean[charh][charw];

        int startX = charw * charX;
        int startY = charh * charY;

        for(int y = 0; y < charh; y++) {
            for(int x = 0; x < charw; x++) {
                glyph[y][x] = buffer[y + startY][x + startX];
            }
        }

        return glyph;
    }

    public boolean[][] getGlyphAtIndex(int index) {
        int w = bufferw / charw;
        int xi = index % w;
        int yi = index / w;

        return getGlyphAtLocation(xi, yi);
    }
}
