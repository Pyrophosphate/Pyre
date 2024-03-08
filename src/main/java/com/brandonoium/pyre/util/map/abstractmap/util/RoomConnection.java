package com.brandonoium.pyre.util.map.abstractmap.util;

public class RoomConnection {
    private MapRoom room1;
    private MapRoom room2;


    public RoomConnection(MapRoom room1, MapRoom room2) {
        this.room1 = room1;
        this.room2 = room2;
    }

    public MapRoom getRoom1() {
        return room1;
    }

    public void setRoom1(MapRoom room1) {
        this.room1 = room1;
    }

    public MapRoom getRoom2() {
        return room2;
    }

    public void setRoom2(MapRoom room2) {
        this.room2 = room2;
    }
}
