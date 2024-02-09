package com.brandonoium.pyre.systems;

import com.brandonoium.pyre.components.*;
import com.brandonoium.pyre.ecs.EcsWorld;
import com.brandonoium.pyre.ecs.IComponent;
import com.brandonoium.pyre.ecs.ISystem;
import com.brandonoium.pyre.util.Location;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

public class FollowSimpleTargetSystem extends ISystem {

    //private EcsWorld world;


    public FollowSimpleTargetSystem(EcsWorld world) {
        super(world);
    }


    @Override
    public void run() {
        Map<Long, IComponent> movers = world.getComponentsByType(SimpleMovementTargetComponent.class);
        ArrayList<Long> idsToRemove = new ArrayList<>();

        for(Entry<Long, IComponent> a : movers.entrySet()) {
            SimpleMovementTargetComponent ai = (SimpleMovementTargetComponent) a.getValue();
            Location current = ((LocationComponent) world.getComponentsById(a.getKey()).get(LocationComponent.class)).getLoc();
            Location target = ai.getTarget();


            // Step toward the goal in x and y.
            int dx = 0, dy = 0;

            if(target.getX() < current.getX()) {
                dx = -1;
            } else if (target.getX() > current.getX()) {
                dx = 1;
            }
            if(target.getY() < current.getY()) {
                dy = -1;
            } else if (target.getY() > current.getY()) {
                dy = 1;
            }

            if(dx == 0 && dy == 0) {
                idsToRemove.add(a.getKey());
            } else {
                world.addComponent(a.getKey(), new BumpMovementComponent(dx, dy));
            }
        }

        for(long id : idsToRemove) {
            world.removeComponent(id, SimpleMovementTargetComponent.class);
            world.addComponent(id, new MessageLogOutputComponent("Finished moving."));
        }
    }
}
