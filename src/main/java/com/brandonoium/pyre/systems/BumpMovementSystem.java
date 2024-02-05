package com.brandonoium.pyre.systems;

import com.brandonoium.pyre.components.BumpMovementComponent;
import com.brandonoium.pyre.components.LocationComponent;
import com.brandonoium.pyre.components.TerminalRenderableComponent;
import com.brandonoium.pyre.ecs.EcsWorld;
import com.brandonoium.pyre.ecs.IComponent;
import com.brandonoium.pyre.ecs.ISystem;
import com.brandonoium.pyre.util.Location;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

public class BumpMovementSystem implements ISystem {

    private EcsWorld world;


    @Override
    public void init(EcsWorld world) {
        this.world = world;
    }

    @Override
    public void run() {
        Map<Long, IComponent> bumps = world.getComponentsByType(BumpMovementSystem.class);
        ArrayList<Long> idsToRemove = new ArrayList<>();

        // For now just do the movement as requested.
        for(Entry<Long, IComponent> b : bumps.entrySet()) {
            BumpMovementComponent bump = (BumpMovementComponent) b.getValue();
            LocationComponent l = (LocationComponent) world.getComponentsById(b.getKey()).get(LocationComponent.class);
            int newX = l.getLoc().getX() + bump.getxBump();
            int newY = l.getLoc().getY() + bump.getyBump();
            l.setLoc(new Location(newX, newY));

            idsToRemove.add(b.getKey());
        }

        for(long id : idsToRemove) {
            world.removeComponent(id, BumpMovementComponent.class);
        }
    }
}