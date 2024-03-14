package com.brandonoium.pyre.entitybuilders;

import com.brandonoium.pyre.components.*;
import com.brandonoium.pyre.ecs.EcsWorld;

public class StairsDownBuilder {

    /**
     * Create an EntityID for the "player" entity and assign a standard collection of components.
     *
     * @param world The EcsWorld that the player belongs to.
     * @return The EntityID of the created player.
     */
    public static void buildStairsDown(EcsWorld world, int x, int y) {
        long newId = world.newEntityId();
        world.addComponent(newId, new LocationComponent(x, y));
        world.addComponent(newId, new TerminalRenderableComponent('>'));
        world.addComponent(newId, new NameComponent("Stairs"));
        world.addComponent(newId, new DescriptionComponent("These go to the next level."));
        world.addComponent(newId, new StairsDownComponent());
    }
}
