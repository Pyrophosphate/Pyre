package com.brandonoium.pyre.systems;

import com.brandonoium.pyre.components.LocationComponent;
import com.brandonoium.pyre.components.MessageLogOutputComponent;
import com.brandonoium.pyre.ecs.EcsSystem;
import com.brandonoium.pyre.ecs.EcsWorld;
import com.brandonoium.pyre.ecs.IComponent;
import com.brandonoium.pyre.util.Location;

import java.util.List;
import java.util.Map;

public class DebugCollisionSystem extends EcsSystem {

    static DebugCollisionSystem singleton;

    public static DebugCollisionSystem getSystem(EcsWorld world) {
        if(singleton == null) {
            singleton = new DebugCollisionSystem(world);
        }
        return singleton;
    }

    public static DebugCollisionSystem getSystemIfExists() {
        return singleton;
    }

    private DebugCollisionSystem(EcsWorld world) {
        super(world);
    }


    @Override
    public void run() {
        Map<Long, IComponent> locations = world.getComponentsByType(LocationComponent.class);

        for(Map.Entry<Long, IComponent> l : locations.entrySet()) {
            Location location = ((LocationComponent) l.getValue()).getLoc();

            List<Long> collisions = world.getLocationIndex().getEntitiesAt(location.getX(), location.getY());

            for(long entity : collisions) {
                if(entity != l.getKey()) {
                    world.addComponent(entity, new MessageLogOutputComponent("Collision found."));
                }
            }
        }
    }
}
