package com.brandonoium.pyre.systems;

import com.brandonoium.pyre.components.BumpMovementComponent;
import com.brandonoium.pyre.components.JustMovedComponent;
import com.brandonoium.pyre.components.LocationComponent;
import com.brandonoium.pyre.ecs.EcsWorld;
import com.brandonoium.pyre.ecs.IComponent;
import com.brandonoium.pyre.ecs.EcsSystem;
import com.brandonoium.pyre.util.Location;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

public class BumpMovementSystem extends EcsSystem {

    static BumpMovementSystem singleton;

    public static BumpMovementSystem getSystem(EcsWorld world) {
        if(singleton == null) {
            singleton = new BumpMovementSystem(world);
        }
        return singleton;
    }

    public static BumpMovementSystem getSystemIfExists() {
        return singleton;
    }

    private BumpMovementSystem(EcsWorld world) {
        super(world);
    }

    @Override
    public void run() {
        Map<Long, IComponent> bumps = world.getComponentsByType(BumpMovementComponent.class);
        ArrayList<Long> idsToRemove = new ArrayList<>();

        // For now just do the movement as requested.
        for(Entry<Long, IComponent> b : bumps.entrySet()) {
            //System.out.println("Found bump: " + b);
            BumpMovementComponent bump = (BumpMovementComponent) b.getValue();
            LocationComponent l = (LocationComponent) world.getComponentsById(b.getKey()).get(LocationComponent.class);
            if(l == null) {
                System.out.println("BumpMovementComponent found on entity with no LocationComponent; entityId = " + b.getKey());

            } else {
                int newX = l.getLoc().getX() + bump.getxBump();
                int newY = l.getLoc().getY() + bump.getyBump();
                world.updateLocation(l, new Location(newX, newY), b.getKey());
                world.addComponent(b.getKey(), new JustMovedComponent());
            }

            idsToRemove.add(b.getKey());
        }

        for(long id : idsToRemove) {
            world.removeComponent(id, BumpMovementComponent.class);
        }
    }
}
