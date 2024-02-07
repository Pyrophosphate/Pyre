package com.brandonoium.pyre.components;

import com.brandonoium.pyre.ecs.IComponent;
import com.brandonoium.pyre.util.Location;

public class SimpleMovementTargetComponent implements IComponent {
    private Location target;

    public SimpleMovementTargetComponent(Location target) {
        this.target = target;
    }


    public Location getTarget() {
        return target;
    }

    public void setTarget(Location target) {
        this.target = target;
    }
}
