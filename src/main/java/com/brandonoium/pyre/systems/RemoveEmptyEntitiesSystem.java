package com.brandonoium.pyre.systems;

import com.brandonoium.pyre.ecs.EcsSystem;
import com.brandonoium.pyre.ecs.EcsWorld;

public class RemoveEmptyEntitiesSystem extends EcsSystem {

    static RemoveEmptyEntitiesSystem singleton;

    private RemoveEmptyEntitiesSystem(EcsWorld world) {
        super(world);
    }

    public static RemoveEmptyEntitiesSystem getSystem(EcsWorld world) {
        if(singleton == null) {
            singleton = new RemoveEmptyEntitiesSystem(world);
        }
        return singleton;
    }

    public static RemoveEmptyEntitiesSystem getSystemIfExists() {
        return singleton;
    }


    @Override
    public void run() {
        world.removeEmptyEntities();
    }
}
