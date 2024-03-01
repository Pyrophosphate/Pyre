package com.brandonoium.pyre.util.map.generator.util;

public class MapRoom {
    private int x;
    private int y;

    private int width;
    private int height;


    public MapRoom(int xPosition, int yPosition, int width, int height) {
        this.x = xPosition;
        this.y = yPosition;
        this.width = width;
        this.height = height;
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
