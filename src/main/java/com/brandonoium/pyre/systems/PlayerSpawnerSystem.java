package com.brandonoium.pyre.systems;

import com.brandonoium.pyre.components.*;
import com.brandonoium.pyre.ecs.EcsSystem;
import com.brandonoium.pyre.ecs.EcsWorld;
import com.brandonoium.pyre.ecs.IComponent;
import com.brandonoium.pyre.gamestates.EnemyTurnState;
import com.brandonoium.pyre.util.Location;

import java.util.ArrayList;
import java.util.Map;

public class PlayerSpawnerSystem extends EcsSystem {
    static PlayerSpawnerSystem singleton;
    private long playerEntityId;

    private PlayerSpawnerSystem(EcsWorld world, long playerEntityId) {
        super(world);
        this.playerEntityId = playerEntityId;
    }

    public static PlayerSpawnerSystem getSystem(EcsWorld world, long playerEntityId) {
        if(singleton == null) {
            singleton = new PlayerSpawnerSystem(world, playerEntityId);
        }
        return singleton;
    }

    public static PlayerSpawnerSystem getSystemIfExists() {
        return singleton;
    }


    @Override
    public void run() {
        Map<Long, IComponent> playerSpawners = world.getComponentsByType(PlayerSpawnerComponent.class);
        ArrayList<Long> idsToRemove = new ArrayList<>();

        for(Map.Entry<Long, IComponent> x : playerSpawners.entrySet()) {
            LocationComponent playerLocation = (LocationComponent) world.getComponent(playerEntityId, LocationComponent.class);
            if(playerLocation != null) {
                Location spawnerLocation = ((PlayerSpawnerComponent) x.getValue()).getPlayerLocation();
                world.updateLocation(playerLocation, spawnerLocation, x.getKey());

                idsToRemove.add(x.getKey());
            }
        }

        for(long id : idsToRemove) {
            world.removeEntity(id);
        }
    }
}
