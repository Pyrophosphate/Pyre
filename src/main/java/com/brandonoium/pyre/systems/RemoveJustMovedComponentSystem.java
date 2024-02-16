package com.brandonoium.pyre.systems;

import com.brandonoium.pyre.components.JustMovedComponent;
import com.brandonoium.pyre.ecs.EcsSystem;
import com.brandonoium.pyre.ecs.EcsWorld;
import com.brandonoium.pyre.ecs.IComponent;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

public class RemoveJustMovedComponentSystem extends EcsSystem {

    static RemoveJustMovedComponentSystem singleton;

    public static RemoveJustMovedComponentSystem getSystem(EcsWorld world) {
        if(singleton == null) {
            singleton = new RemoveJustMovedComponentSystem(world);
        }
        return singleton;
    }

    public static RemoveJustMovedComponentSystem getSystemIfExists() {
        return singleton;
    }


    private RemoveJustMovedComponentSystem(EcsWorld world) {
        super(world);
    }


    @Override
    public void run() {
        Map<Long, IComponent> movers = world.getComponentsByType(JustMovedComponent.class);
        ArrayList<Long> idsToRemove = new ArrayList<>();

        for(Entry<Long, IComponent> m : movers.entrySet()) {
            idsToRemove.add(m.getKey());
        }

        for(long id : idsToRemove) {
            world.removeComponent(id, JustMovedComponent.class);
        }
    }
}
