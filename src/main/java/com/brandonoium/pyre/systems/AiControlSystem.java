package com.brandonoium.pyre.systems;

import com.brandonoium.pyre.components.AIControlComponent;
import com.brandonoium.pyre.components.BumpMovementComponent;
import com.brandonoium.pyre.components.LocationComponent;
import com.brandonoium.pyre.ecs.EcsWorld;
import com.brandonoium.pyre.ecs.IComponent;
import com.brandonoium.pyre.ecs.EcsSystem;

import java.util.Map;

public class AiControlSystem extends EcsSystem {

    static AiControlSystem singleton;

    public static AiControlSystem getSystem(EcsWorld world) {
        if(singleton == null) {
            singleton = new AiControlSystem(world);
        }
        return singleton;
    }

    public static AiControlSystem getSystemIfExists() {
        return singleton;
    }

    private AiControlSystem(EcsWorld world) {
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
