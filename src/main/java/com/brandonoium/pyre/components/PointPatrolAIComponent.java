package com.brandonoium.pyre.components;

import com.brandonoium.pyre.ecs.IComponent;
import com.brandonoium.pyre.util.Location;

public class PointPatrolAIComponent implements IComponent {
    private Location patrolCenter;

    public PointPatrolAIComponent(Location patrolCenter) {
        this.patrolCenter = patrolCenter;
    }

    public Location getPatrolCenter() {
        return patrolCenter;
    }

    public void setPatrolCenter(Location patrolCenter) {
        this.patrolCenter = patrolCenter;
    }
}
