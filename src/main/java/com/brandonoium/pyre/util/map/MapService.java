package com.brandonoium.pyre.util.map;

import com.brandonoium.pyre.util.map.generator.MapGenerator;

public class MapService {
    private GameMap map;


    public GameMap getMap() {
        return map;
    }

    public void setMap(GameMap map) {
        this.map = map;
    }


    public boolean canWalkAt(int x, int y) {
        if(map.isLocationValid(x, y))
            return map.getTileAt(x, y).canWalk();
        else
            return false;
    }

    public boolean canFlyAt(int x, int y) {
        if(map.isLocationValid(x, y))
            return map.getTileAt(x, y).canFly();
        else
            return false;
    }

    public boolean isOpaque(int x, int y) {
        if(map.isLocationValid(x, y))
            return map.getTileAt(x, y).isOpaque();
        else
            return false;
    }

    public int getGlyphAt(int x, int y) {
        if(map.isLocationValid(x, y))
            return map.getTileAt(x, y).getGlyph();
        else
            return 0;
    }

    public boolean isValidLocation(int x, int y) {
        return map.isLocationValid(x, y);
    }


    public void generateMap(MapGenerator generator) {
        map = generator.createMap();
    }
}
