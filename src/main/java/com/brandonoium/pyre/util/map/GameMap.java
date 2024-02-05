package com.brandonoium.pyre.util.map;

public class GameMap {
    private MapTileType[][] tiles;
    private int w, h;


    public GameMap(int width, int height) {
        w = width;
        h = height;
        tiles = new MapTileType[h][w];
    }


    public boolean isLocationValid(int x, int y) {
        return (x >= 0 && x < w && y >= 0 && y < h);
    }

    public void setTileAt(int x, int y, MapTileType type) {
        if(isLocationValid(x, y)) {
            tiles[y][x] = type;
        }
    }

    public MapTileType getTileAt(int x, int y) {
        if(isLocationValid(x, y))
            return tiles[y][x];
        else
            return null;
    }

    public void resize(int width, int height) {
        tiles = new MapTileType[height][width];
        w = width;
        h = height;
    }

    public void fill(MapTileType type) {
        for(int y = 0; y < h; y++) {
            for(int x = 0; x< w; x++) {
                tiles[y][x] = type;
            }
        }
    }

    public void print() {
        for(int y = 0; y < h; y++) {
            for(int x = 0; x< w; x++) {
                System.out.print(tiles[y][x]);
            }
            System.out.println();
        }
    }
}
