package com.brandonoium.pyre.util.map.abstractmap.mapfeature;

import com.brandonoium.pyre.ecs.EcsWorld;
import com.brandonoium.pyre.util.map.GameMap;
import com.brandonoium.pyre.util.map.MapTileType;

import java.util.Random;

public class SimpleCorridor extends RoomConnection {
    private int width;


    public SimpleCorridor(MapRoom room1, MapRoom room2, int width) {
        super(room1, room2);
        this.width = width;
    }

    public SimpleCorridor(MapRoom room1, MapRoom room2) {
        super(room1, room2);
        this.width = 1;
    }


    @Override
    public void renderToMap(GameMap map, Random rng, EcsWorld world) {
        int startX = rng.nextInt(room1.getX(), room1.getX() + room1.getWidth() - width);
        int startY = rng.nextInt(room1.getY(), room1.getY() + room1.getHeight() - width);
        int endX = rng.nextInt(room2.getX(), room2.getX() + room2.getWidth() - width);
        int endY = rng.nextInt(room2.getY(), room2.getY() + room2.getHeight() - width);

        if(rng.nextBoolean()) {
            for(int i = 0; i < width; i++) {
                carveEWLine(startY + i, startX, endX + (width - 1), map);
                carveNSLine(endX + i, startY, endY, map);
            }
        } else {
            for(int i = 0; i < width; i++) {
                carveEWLine(endY + i, startX, endX, map);
                carveNSLine(startX + i, startY, endY + (width - 1), map);
            }
        }
    }

    private void carveEWLine(int y, int x1, int x2, GameMap map) {
        int minX = Math.min(x1, x2);
        int maxX = Math.max(x1, x2);
        for(int x = minX; x <= maxX; x++) {
            map.setTileAt(x, y, MapTileType.FLOOR);
        }
    }

    private void carveNSLine(int x, int y1, int y2, GameMap map) {
        int minY = Math.min(y1, y2);
        int maxY = Math.max(y1, y2);
        for(int y = minY; y <= maxY; y++) {
            map.setTileAt(x, y, MapTileType.FLOOR);
        }
    }
}
