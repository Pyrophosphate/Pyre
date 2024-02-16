package com.brandonoium.pyre.entitybuilders;

import com.brandonoium.pyre.ecs.EcsWorld;
import com.brandonoium.pyre.components.*;

public class PlayerBuilder {

    /**
     * Create an EntityID for the "player" entity and assign a standard collection of components.
     *
     * @param world The EcsWorld that the player belongs to.
     * @return The EntityID of the created player.
     */
    public static long buildPlayer(EcsWorld world) {
        long playerId = world.newEntityId();
        world.setPlayerEntityId(playerId);
        world.addComponent(playerId, new LocationComponent(10, 5));
        world.addComponent(playerId, new TerminalRenderableComponent('@'));
        world.addComponent(playerId, new CameraTargetComponent(1));
        world.addComponent(playerId, new PlayerControlComponent(1));
        world.addComponent(playerId, new NameComponent("Player"));
        world.addComponent(playerId, new DescriptionComponent("This is you. The player. You're the player."));
        world.addComponent(playerId, new MoveableComponent(true, false, false));
        world.addComponent(playerId, new CanExamineComponent());
        world.addComponent(playerId, new HealthComponent(5));

        return playerId;
    }
}
