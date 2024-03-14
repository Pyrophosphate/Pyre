package com.brandonoium.pyre.systems;

import com.brandonoium.pyre.components.*;
import com.brandonoium.pyre.ecs.EcsSystem;
import com.brandonoium.pyre.ecs.EcsWorld;
import com.brandonoium.pyre.ecs.IComponent;
import com.brandonoium.pyre.entitybuilders.PlayerBuilder;
import com.brandonoium.pyre.gamestates.EnemyTurnState;
import com.brandonoium.pyre.util.Location;

import javax.sound.midi.SysexMessage;
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
            Location spawnerLocation = ((PlayerSpawnerComponent) x.getValue()).getPlayerLocation();
            if(playerLocation != null) {
                System.out.println("Found player with ID " + x.getKey());
                world.updateLocation(playerLocation, spawnerLocation, playerEntityId);

                idsToRemove.add(x.getKey());
            } else {
                //long pid = PlayerBuilder.buildPlayer(world);
                world.addComponent(playerEntityId, new LocationComponent(spawnerLocation));
            }
        }

        for(long id : idsToRemove) {
            world.removeEntity(id);
        }
    }
}
