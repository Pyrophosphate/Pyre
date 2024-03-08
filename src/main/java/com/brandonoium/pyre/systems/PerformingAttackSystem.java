package com.brandonoium.pyre.systems;

import com.brandonoium.pyre.components.*;
import com.brandonoium.pyre.ecs.EcsSystem;
import com.brandonoium.pyre.ecs.EcsWorld;
import com.brandonoium.pyre.ecs.IComponent;

import java.util.ArrayList;
import java.util.Map;

public class PerformingAttackSystem  extends EcsSystem {

    static PerformingAttackSystem singleton;

    public static PerformingAttackSystem getSystem(EcsWorld world) {
        if(singleton == null) {
            singleton = new PerformingAttackSystem(world);
        }
        return singleton;
    }

    public static PerformingAttackSystem getSystemIfExists() {
        return singleton;
    }

    private PerformingAttackSystem(EcsWorld world) {
        super(world);
    }

    public void run() {
        Map<Long, IComponent> attacks = world.getComponentsByType(PerformingAttackComponent.class);
        ArrayList<Long> idsToRemove = new ArrayList<>();

        for(Map.Entry<Long, IComponent> a : attacks.entrySet()) {
            PerformingAttackComponent ai = (PerformingAttackComponent) a.getValue();



            idsToRemove.add(a.getKey());
            MessageLogOutputComponent.addMessage(world, ai.getAttackerId(), "Performed Attack.");
        }

        for(long id : idsToRemove) {
            world.removeComponent(id, PerformingAttackComponent.class);
        }
    }
}
