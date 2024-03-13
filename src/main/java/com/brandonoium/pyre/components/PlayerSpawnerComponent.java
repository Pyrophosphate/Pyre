package com.brandonoium.pyre.components;

import com.brandonoium.pyre.ecs.IComponent;
import com.brandonoium.pyre.util.Location;

public class PlayerSpawnerComponent implements IComponent {
    private Location playerLocation;

    public PlayerSpawnerComponent(int x, int y) {
        playerLocation = new Location(x, y);
    }

    public Location getPlayerLocation() {
        return playerLocation;
    }
}
