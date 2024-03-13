package com.brandonoium.pyre.util.map.generator;

import com.brandonoium.pyre.ecs.EcsWorld;
import com.brandonoium.pyre.util.map.GameMap;
import com.brandonoium.pyre.util.map.MapTileType;
import com.brandonoium.pyre.util.map.abstractmap.LogicalMap;
import com.brandonoium.pyre.util.map.abstractmap.mapfeature.MapRoom;
import com.brandonoium.pyre.util.map.abstractmap.mapfeature.SimpleCorridor;

import java.util.ArrayList;
import java.util.Random;

public class RoomGridMapGenerator implements MapGenerator {

    private int width;
    private int height;

    private int roomGridWidth = 3;
    private int roomGridHeight = 3;

    private int maxRoomDimension = 50;
    private int minRoomDimension = 3;
    private int minRoomDistFromGrid = 1;
    private int maxAllowedEmptyGrids = 1;

    private int corridorWidth = 2;

    private Random rng;

    public RoomGridMapGenerator(EcsWorld world, int width, int height, long seed) {
        this.width = width;
        this.height = height;
        rng = new Random(seed);
    }

    public RoomGridMapGenerator(EcsWorld world, int width, int height) {
        this(world, width, height, 0);
        rng = new Random();
    }

    @Override
    public GameMap createMap() throws MapGenException {
        GameMap map = new GameMap(width, height);
        LogicalMap lmap = new LogicalMap();
        map.fill(MapTileType.VOID);
        ArrayList<MapRoom> rooms = new ArrayList<>(roomGridWidth * roomGridHeight);

        int cellWidth = width / roomGridWidth;
        int cellHeight = height / roomGridHeight;

        int usableWidth = cellWidth - (minRoomDistFromGrid * 2);
        int usableHeight = cellHeight - (minRoomDistFromGrid * 2);
        int calcMaxRoomWidth = Math.min(usableWidth, maxRoomDimension);
        int calcMaxRoomHeight = Math.min(usableHeight, maxRoomDimension);

        for(int y = 0; y < roomGridHeight; y++) {
            for(int x = 0; x < roomGridWidth; x++) {
                int width = rng.nextInt(calcMaxRoomWidth - minRoomDimension) + minRoomDimension;
                int height = rng.nextInt(calcMaxRoomHeight - minRoomDimension) + minRoomDimension;
                int xPos = rng.nextInt(usableWidth - width) + minRoomDistFromGrid;
                int yPos = rng.nextInt(usableHeight - height) + minRoomDistFromGrid;

                rooms.add(roomPositionIndex(x, y), new MapRoom(xPos + (x * cellWidth), yPos + (y * cellHeight), width, height));
            }
        }

        for(MapRoom room : rooms) {
            lmap.addRoom(room);
        }

        ArrayList<SimpleCorridor> connections = connectAllAdjacentRooms(rooms);

        for(SimpleCorridor connection : connections) {
            lmap.addConnection(connection);
        }

        lmap.renderAllToMap(map, rng);

        surroundWithWalls(map);

        return map;
    }


    private int roomPositionIndex(int x, int y) {
        return (y * roomGridWidth) + x;
    }

    private ArrayList<SimpleCorridor> connectAllAdjacentRooms(ArrayList<MapRoom> rooms) {
        ArrayList<SimpleCorridor> corridors = new ArrayList<>();

        for(int y = 0; y < roomGridHeight - 1; y++) {
            for(int x = 0; x < roomGridWidth - 1; x++) {
                corridors.add(new SimpleCorridor(rooms.get(roomPositionIndex(x, y)), rooms.get(roomPositionIndex(x + 1, y)), corridorWidth));
                corridors.add(new SimpleCorridor(rooms.get(roomPositionIndex(x, y)), rooms.get(roomPositionIndex(x, y + 1)), corridorWidth));
            }
            corridors.add(new SimpleCorridor(rooms.get(roomPositionIndex(roomGridWidth - 1, y)), rooms.get(roomPositionIndex(roomGridWidth - 1, y + 1)), corridorWidth));
        }
        for(int x = 0; x < roomGridWidth - 1; x++) {
            corridors.add(new SimpleCorridor(rooms.get(roomPositionIndex(x, roomGridHeight - 1)), rooms.get(roomPositionIndex(x + 1, roomGridHeight - 1)), corridorWidth));
        }

        return corridors;
    }

    private void surroundWithWalls(GameMap map) {
        for(int y = 0; y < map.getH(); y++) {
            for(int x = 0; x < map.getW(); x++) {
                if(map.getTileAt(x, y) != MapTileType.FLOOR) {
                    if(map.getTileAt(x-1, y-1) == MapTileType.FLOOR) {
                        map.setTileAt(x, y, MapTileType.WALL);
                    }
                    if(map.getTileAt(x, y-1) == MapTileType.FLOOR) {
                        map.setTileAt(x, y, MapTileType.WALL);
                    }
                    if(map.getTileAt(x+1, y-1) == MapTileType.FLOOR) {
                        map.setTileAt(x, y, MapTileType.WALL);
                    }
                    if(map.getTileAt(x+1, y) == MapTileType.FLOOR) {
                        map.setTileAt(x, y, MapTileType.WALL);
                    }
                    if(map.getTileAt(x+1, y+1) == MapTileType.FLOOR) {
                        map.setTileAt(x, y, MapTileType.WALL);
                    }
                    if(map.getTileAt(x, y+1) == MapTileType.FLOOR) {
                        map.setTileAt(x, y, MapTileType.WALL);
                    }
                    if(map.getTileAt(x-1, y+1) == MapTileType.FLOOR) {
                        map.setTileAt(x, y, MapTileType.WALL);
                    }
                    if(map.getTileAt(x-1, y) == MapTileType.FLOOR) {
                        map.setTileAt(x, y, MapTileType.WALL);
                    }
                }
            }
        }
    }


    public void setRoomGridWidth(int roomGridWidth) {
        this.roomGridWidth = roomGridWidth;
    }

    public void setRoomGridHeight(int roomGridHeight) {
        this.roomGridHeight = roomGridHeight;
    }

    public void setMaxRoomDimension(int maxRoomDimension) {
        this.maxRoomDimension = maxRoomDimension;
    }

    public void setMinRoomDimension(int minRoomDimension) {
        this.minRoomDimension = minRoomDimension;
    }

    public void setMinRoomDistFromGrid(int minRoomDistFromGrid) {
        this.minRoomDistFromGrid = minRoomDistFromGrid;
    }

    public void setMaxAllowedEmptyGrids(int maxAllowedEmptyGrids) {
        this.maxAllowedEmptyGrids = maxAllowedEmptyGrids;
    }
}
