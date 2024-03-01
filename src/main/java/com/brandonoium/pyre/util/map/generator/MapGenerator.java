package com.brandonoium.pyre.util.map.generator;

import com.brandonoium.pyre.util.map.GameMap;

public interface MapGenerator {
    public GameMap createMap() throws MapGenException;
}
