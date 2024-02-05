package com.brandonoium.bithorse;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class BitHorseTerminal extends BitHorseApp {

    private BitDisplay display;

    // Terminal attributes
    private int rows, columns;
    private int charSize = 8;
    private BitHorseTerminalBuffer buffer;
    private BitTermCharSet charSet;


    public BitHorseTerminal(String title, int columns, int rows) throws InvalidCharSetException, IOException {
        super(title, columns * 8, rows * 8);
        this.rows = rows;
        this.columns = columns;

        buffer = new BitHorseTerminalBuffer(columns, rows);

        // Load default character set.
        loadCharSetFile("./CGA8x8thick.png");
        init();
    }

    public void loadCharSetFile(String file) throws InvalidCharSetException, IOException {
        BufferedImage img;
        try {
            img = ImageIO.read(new File(file));
        } catch (IOException e) {
            throw e;
        }

        try {
            charSet = new BitTermCharSet(img, 8, 8, Color.WHITE);
        } catch (InvalidCharSetException e) {
            throw e;
        }
    }

    @Override
    public void init() {
        display = super.getDisplay();

        this.setupDisplay();

        //this.addComponentListener(new CompListener());
        this.setResizable(false);
    }

    @Override
    public void run() {
        this.draw();
    }


    // Terminal methods.
    public void draw() {
        for(int y = 0; y < rows; y++) {
            for(int x = 0; x < columns; x++) {
                drawGlyph(x, y, buffer.getFromPosition(x, y));
            }
        }
        display.paintScaled();

        display.repaint();
    }

    private void drawGlyph(int xPos, int yPos, int c) {
        int startX = xPos * charSize;
        int startY = yPos * charSize;

        boolean[][] glyph = charSet.getGlyphAtIndex(c);

        for(int y = 0; y < charSize; y++) {
            for(int x = 0; x < charSize; x++) {
                display.setPixel(x + startX, y + startY, glyph[y][x]);
            }
        }
    }

    public void setFgBgColors(Color fg, Color bg) {
        display.settColor(fg.getRGB());
        display.setfColor(bg.getRGB());
    }

    public BitHorseTerminalBuffer getBuffer() {
        return buffer;
    }

    public void drawBufferAt(BitHorseTerminalBuffer buff, int x, int y) {
        buffer.copyFromBuffer(buff, x, y);
    }
}
