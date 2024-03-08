package com.brandonoium.pyre.util.map.generator;

import com.brandonoium.pyre.util.map.GameMap;
import com.brandonoium.pyre.util.map.MapTileType;
import com.brandonoium.pyre.util.map.abstractmap.util.MapRoom;
import com.brandonoium.pyre.util.map.abstractmap.util.RoomConnection;

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

    private Random rng;

    public RoomGridMapGenerator(int width, int height, long seed) {
        this.width = width;
        this.height = height;
        rng = new Random(seed);
    }

    public RoomGridMapGenerator(int width, int height) {
        this(width, height, 0);
        rng = new Random();
    }

    @Override
    public GameMap createMap() throws MapGenException {
        GameMap map = new GameMap(width, height);
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
            room.renderToMap(map);
        }

        ArrayList<RoomConnection> connections = connectAllAdjacentRooms(rooms);

        for(RoomConnection connection : connections) {
            carveCorridor(connection, map);
        }

        surroundWithWalls(map);

        return map;
    }


    private int roomPositionIndex(int x, int y) {
        return (y * roomGridWidth) + x;
    }

    private ArrayList<RoomConnection> connectAllAdjacentRooms(ArrayList<MapRoom> rooms) {
        ArrayList<RoomConnection> corridors = new ArrayList<>();

        for(int y = 0; y < roomGridHeight - 1; y++) {
            for(int x = 0; x < roomGridWidth - 1; x++) {
                corridors.add(new RoomConnection(rooms.get(roomPositionIndex(x, y)), rooms.get(roomPositionIndex(x + 1, y))));
                corridors.add(new RoomConnection(rooms.get(roomPositionIndex(x, y)), rooms.get(roomPositionIndex(x, y + 1))));
            }
            corridors.add(new RoomConnection(rooms.get(roomPositionIndex(roomGridWidth - 1, y)), rooms.get(roomPositionIndex(roomGridWidth - 1, y + 1))));
        }
        for(int x = 0; x < roomGridWidth - 1; x++) {
            corridors.add(new RoomConnection(rooms.get(roomPositionIndex(x, roomGridHeight - 1)), rooms.get(roomPositionIndex(x + 1, roomGridHeight - 1))));
        }

        return corridors;
    }

    private void carveCorridor(RoomConnection connection, GameMap map) {
        carveCorridor(connection.getRoom1(), connection.getRoom2(), map);
    }

    private void carveCorridor(MapRoom room1, MapRoom room2, GameMap map) {
        int startX = rng.nextInt(room1.getX(), room1.getX() + room1.getWidth());
        int startY = rng.nextInt(room1.getY(), room1.getY() + room1.getHeight());
        int endX = rng.nextInt(room2.getX(), room2.getX() + room2.getWidth());
        int endY = rng.nextInt(room2.getY(), room2.getY() + room2.getHeight());

        if(rng.nextBoolean()) {
            carveEWLine(startY, startX, endX, map);
            carveNSLine(endX, startY, endY, map);
        } else {
            carveNSLine(startX, startY, endY, map);
            carveEWLine(endY, startX, endX, map);
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
