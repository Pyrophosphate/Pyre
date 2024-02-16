package com.brandonoium.pyre.systems;

import com.brandonoium.pyre.components.ExamineComponent;
import com.brandonoium.pyre.components.IsRemoteExamineComponent;
import com.brandonoium.pyre.components.JustMovedComponent;
import com.brandonoium.pyre.ecs.EcsSystem;
import com.brandonoium.pyre.ecs.EcsWorld;
import com.brandonoium.pyre.ecs.IComponent;
import java.util.Map;

public class RemoteExamineSystem extends EcsSystem {

    static RemoteExamineSystem singleton;

    public static RemoteExamineSystem getSystem(EcsWorld world) {
        if(singleton == null) {
            singleton = new RemoteExamineSystem(world);
        }
        return singleton;
    }

    public static RemoteExamineSystem getSystemIfExists() {
        return singleton;
    }


    private RemoteExamineSystem(EcsWorld world) {
        super(world);
    }


    @Override
    public void run() {
        Map<Long, IComponent> remoteExamineComps = world.getComponentsByType(IsRemoteExamineComponent.class);

        for(Map.Entry<Long, IComponent> x : remoteExamineComps.entrySet()) {
            if(world.getComponent(x.getKey(), JustMovedComponent.class) != null) {
                world.addComponent(x.getKey(), new ExamineComponent());
            }
        }
    }
}
