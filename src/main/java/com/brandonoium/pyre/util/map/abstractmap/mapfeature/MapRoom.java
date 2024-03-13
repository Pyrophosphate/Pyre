package com.brandonoium.pyre.util.map.abstractmap.mapfeature;

import com.brandonoium.pyre.util.map.GameMap;
import com.brandonoium.pyre.util.map.MapTileType;
import com.brandonoium.pyre.util.map.abstractmap.MapFeature;

import java.util.Random;

public class MapRoom extends MapFeature {
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


    @Override
    public void renderToMap(GameMap map, Random rng) {
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                map.setTileAt(x + this.x, y + this.y, MapTileType.FLOOR);
            }
        }
    }
}
