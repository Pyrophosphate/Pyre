package com.brandonoium.pyre.entitybuilders;

import com.brandonoium.pyre.components.*;
import com.brandonoium.pyre.ecs.EcsWorld;
import com.brandonoium.pyre.util.Location;

public class RemoteExamineBuilder {

    /**
     * Builds a "remote examine" entity. That is, if you press 'x' to look around you, this is the actual entity that does the looking around.
     */
    public static long buildRemoteExamine(EcsWorld world, long playerId) {
        long newId = world.newEntityId();

        Location playerLocation = ((LocationComponent) world.getComponent(playerId, LocationComponent.class)).getLoc();

        world.addComponent(newId, new LocationComponent(playerLocation.copy()));
        world.addComponent(newId, new TerminalRenderableComponent('X'));
        world.addComponent(newId, new PlayerControlComponent(10));
        world.addComponent(newId, new CameraTargetComponent(10));
        world.addComponent(newId, new IsRemoteExamineComponent());
        world.addComponent(newId, new MoveableComponent(false, false, true));

        return newId;
    }
}
