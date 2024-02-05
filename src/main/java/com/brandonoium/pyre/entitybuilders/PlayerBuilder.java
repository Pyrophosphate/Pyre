package com.brandonoium.pyre.entitybuilders;

import com.brandonoium.pyre.ecs.EcsWorld;
import com.brandonoium.pyre.components.*;

public class PlayerBuilder {

    public static void buildPlayer(EcsWorld world) {
        long playerId = world.newEntityId();
        world.addComponent(playerId, new LocationComponent(20, 15));
        world.addComponent(playerId, new TerminalRenderableComponent('@'));
        world.addComponent(playerId, new CameraTargetComponent(1));
        world.addComponent(playerId, new PlayerControlComponent());
    }
}
