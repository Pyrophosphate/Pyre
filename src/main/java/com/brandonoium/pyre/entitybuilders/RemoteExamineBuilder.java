package com.brandonoium.pyre.entitybuilders;

import com.brandonoium.pyre.components.*;
import com.brandonoium.pyre.ecs.EcsWorld;
import com.brandonoium.pyre.util.Location;

public class RemoteExamineBuilder {

    public static long buildRemoteExamine(EcsWorld world, long playerId) {
        long newId = world.newEntityId();

        Location playerLocation = ((LocationComponent) world.getComponent(playerId, LocationComponent.class)).getLoc();

        world.addComponent(newId, new LocationComponent(playerLocation.copy()));
        world.addComponent(newId, new TerminalRenderableComponent('X'));
        world.addComponent(newId, new PlayerControlComponent(0));
        world.addComponent(newId, new CameraTargetComponent(0));
        world.addComponent(newId, new IsRemoteExamineComponent());

        return newId;
    }
}
