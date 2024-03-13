package com.brandonoium.pyre.util.map.abstractmap;

import com.brandonoium.pyre.util.map.GameMap;
import com.brandonoium.pyre.util.map.abstractmap.mapfeature.MapRoom;
import com.brandonoium.pyre.util.map.abstractmap.mapfeature.RoomConnection;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LogicalMap {
    private List<MapRoom> rooms;
    private List<RoomConnection> connections;

    public LogicalMap() {
        rooms = new ArrayList<>();
        connections = new ArrayList<>();
    }


    public void addRoom(MapRoom newRoom) {
        if(!rooms.contains(newRoom)) {
            rooms.add(newRoom);
        }
    }

    public void addConnection(RoomConnection newConnection) {
        if(!connections.contains(newConnection)) {
            connections.add(newConnection);
        }
    }

    public List<MapRoom> getRooms() {
        return rooms;
    }

    public List<RoomConnection> getConnections() {
        return connections;
    }


    public void renderAllToMap(GameMap map, Random rng) {
        for(MapRoom room : rooms) {
            room.renderToMap(map, rng);
        }
        for(RoomConnection conn : connections) {
            conn.renderToMap(map, rng);
        }
    }
}
