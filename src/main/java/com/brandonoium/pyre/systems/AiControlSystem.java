package com.brandonoium.pyre.systems;

import com.brandonoium.pyre.components.AIControlComponent;
import com.brandonoium.pyre.components.BumpMovementComponent;
import com.brandonoium.pyre.components.LocationComponent;
import com.brandonoium.pyre.ecs.EcsWorld;
import com.brandonoium.pyre.ecs.IComponent;
import com.brandonoium.pyre.ecs.ISystem;
import com.brandonoium.pyre.util.Location;

import java.util.ArrayList;
import java.util.Map;

public class AiControlSystem extends ISystem {

    //private EcsWorld world;

    public AiControlSystem(EcsWorld world) {
        super(world);
    }

    @Override
    public void run() {
        Map<Long, IComponent> ais = world.getComponentsByType(AIControlComponent.class);

        // Just do random walking for now.
        for(Map.Entry<Long, IComponent> a : ais.entrySet()) {
            AIControlComponent ai = (AIControlComponent) a.getValue();
            LocationComponent l = (LocationComponent) world.getComponentsById(a.getKey()).get(LocationComponent.class);

            world.addComponent(a.getKey(), new BumpMovementComponent(1, 0));
        }
    }
}
