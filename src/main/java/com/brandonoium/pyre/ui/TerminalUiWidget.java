package com.brandonoium.pyre.ui;

import com.brandonoium.bithorse.BitHorseTerminalBuffer;

import java.util.ArrayList;

public abstract class TerminalUiWidget {
    protected BitHorseTerminalBuffer buffer;
    private int width, height;
    private int x, y;

    private ArrayList<TerminalUiWidget> children;


    public TerminalUiWidget(int w, int h, int x, int y) {
        width = w;
        height = h;
        this.x = x;
        this.y = y;
        buffer = new BitHorseTerminalBuffer(w, h);

        children = new ArrayList<>();
    }

    public BitHorseTerminalBuffer getBuffer() {
        return buffer;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setSize(int w, int h) {
        width = w;
        height = h;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public BitHorseTerminalBuffer draw() {
        for(TerminalUiWidget child : children) {
            buffer.copyFromBuffer(child.draw(), child.getX(), child.getY());
        }
        return buffer;
    }

    public void addChild(TerminalUiWidget child) {
        children.add(child);
    }


    public int getMinWidth() {
        return 0;
    }

    public int getMaxWidth() {
        return Integer.MAX_VALUE;
    }

    public int getMinHeight() {
        return 0;
    }

    public int getMaxHeight() {
        return Integer.MAX_VALUE;
    }
}
