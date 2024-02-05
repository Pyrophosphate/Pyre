package com.brandonoium.pyre.util.map;

public class MapTile {
    private MapTileType type;

    public MapTile(MapTileType newType) {
        type = newType;
    }

    public MapTileType getType() {
        return type;
    }

    public void setType(MapTileType type) {
        this.type = type;
    }
}
