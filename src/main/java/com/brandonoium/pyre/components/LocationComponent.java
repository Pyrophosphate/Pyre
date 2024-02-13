package com.brandonoium.pyre.components;

import com.brandonoium.pyre.ecs.IComponent;
import com.brandonoium.pyre.util.Location;

public class LocationComponent implements IComponent {
    private Location loc;

    public LocationComponent(int x, int y) {
        loc = new Location(x, y);
    }

    public LocationComponent(Location loc) {
        this.loc = loc;
    }

    public Location getLoc() {
        return loc;
    }

    private void setLoc(Location loc) {
        this.loc = loc;
    }
}
