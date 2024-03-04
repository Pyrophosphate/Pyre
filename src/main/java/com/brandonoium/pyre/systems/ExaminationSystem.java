package com.brandonoium.pyre.systems;

import com.brandonoium.pyre.components.DescriptionComponent;
import com.brandonoium.pyre.components.ExamineComponent;
import com.brandonoium.pyre.components.LocationComponent;
import com.brandonoium.pyre.components.MessageLogOutputComponent;
import com.brandonoium.pyre.ecs.EcsSystem;
import com.brandonoium.pyre.ecs.EcsWorld;
import com.brandonoium.pyre.ecs.IComponent;
import com.brandonoium.pyre.util.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExaminationSystem extends EcsSystem {

    static ExaminationSystem singleton;

    public static ExaminationSystem getSystem(EcsWorld world) {
        if(singleton == null) {
            singleton = new ExaminationSystem(world);
        }
        return singleton;
    }

    public static ExaminationSystem getSystemIfExists() {
        return singleton;
    }

    private ExaminationSystem(EcsWorld world) {
        super(world);
    }

    @Override
    public void run() {
        Map<Long, IComponent> examinations = world.getComponentsByType(ExamineComponent.class);
        ArrayList<Long> idsToRemove = new ArrayList<>();

        for(Map.Entry<Long, IComponent> x : examinations.entrySet()) {
            Location location = ((LocationComponent) world.getComponent(x.getKey(), LocationComponent.class)).getLoc();

            List<Long> collisions = world.getLocationIndex().getEntitiesAt(location.getX(), location.getY());

            for(long entity : collisions) {
                if(entity != x.getKey()) {
                    DescriptionComponent desc = (DescriptionComponent) world.getComponent(entity, DescriptionComponent.class);
                    if(desc != null)
                        MessageLogOutputComponent.addMessage(world, entity, desc.getDesc());
                }
            }

            idsToRemove.add(x.getKey());
        }

        for(long id : idsToRemove) {
            world.removeComponent(id, ExamineComponent.class);
        }
    }
}
