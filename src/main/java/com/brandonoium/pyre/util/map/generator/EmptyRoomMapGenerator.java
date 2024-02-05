package com.brandonoium.pyre.util.map.generator;

import com.brandonoium.pyre.util.map.GameMap;
import com.brandonoium.pyre.util.map.MapTileType;

public class EmptyRoomMapGenerator implements MapGenerator {
    private int width;
    private int height;

    public EmptyRoomMapGenerator(int width, int height) {
        this.width = width;
        this.height = height;
    }


    @Override
    public GameMap createMap() {
        GameMap map = new GameMap(width, height);

        // Do top row
        for(int x = 0; x < width; x++) {
            map.setTileAt(x, 0, MapTileType.WALL);
        }

        // Do middle section
        for(int y = 1; y < height - 1; y++) {
            map.setTileAt(0, y, MapTileType.WALL);
            for(int x = 1; x < width - 1; x++) {
                map.setTileAt(x, y, MapTileType.FLOOR);
            }
            map.setTileAt(width - 1, y, MapTileType.WALL);
        }

        // Do bottom row
        for(int x = 0; x < width; x++) {
            map.setTileAt(x, height - 1, MapTileType.WALL);
        }

        return map;
    }
}
