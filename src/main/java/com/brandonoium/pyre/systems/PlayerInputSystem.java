package com.brandonoium.pyre.systems;

import com.brandonoium.pyre.components.BumpMovementComponent;
import com.brandonoium.pyre.components.CameraTargetComponent;
import com.brandonoium.pyre.components.IsRemoteExamineComponent;
import com.brandonoium.pyre.components.PlayerControlComponent;
import com.brandonoium.pyre.ecs.EcsWorld;
import com.brandonoium.pyre.ecs.IComponent;
import com.brandonoium.pyre.ecs.ISystem;
import com.brandonoium.pyre.entitybuilders.RemoteExamineBuilder;
import com.brandonoium.pyre.util.input.KeyInput;

import java.util.Map;

public class PlayerInputSystem implements ISystem {

    private EcsWorld world;
    private long playerEntityId;


    public PlayerInputSystem(long playerEntityId) {
        this.playerEntityId = playerEntityId;
    }

    @Override
    public void init(EcsWorld world) {
        this.world = world;
    }

    @Override
    public void run() {

    }


    public void doAction(KeyInput input) {

        Map<Long, IComponent> inputTargets = world.getComponentsByType(PlayerControlComponent.class);
        int minPriority = Integer.MAX_VALUE;
        long targetEntity = 0;
        for(Map.Entry<Long, IComponent> c : inputTargets.entrySet()) {
            PlayerControlComponent comp = (PlayerControlComponent) c.getValue();
            if(comp.getPriority() < minPriority) {
                targetEntity = c.getKey();
                minPriority = comp.getPriority();
            }
        }

        System.out.println(input);
        switch (input.getAction()) {
            case MOVE_DOWN -> {
                moveAction(targetEntity, 0, 1);
                break;
            }
            case MOVE_UP -> {
                moveAction(targetEntity, 0, -1);
                break;
            }
            case MOVE_LEFT -> {
                moveAction(targetEntity, -1, 0);
                break;
            }
            case MOVE_RIGHT -> {
                moveAction(targetEntity, 1, 0);
                break;
            }
            case EXAMINE -> {
                toggleExamineMode(targetEntity);
                break;
            }
        }
    }


    private void moveAction(long targetId, int dx, int dy) {
        world.addComponent(targetId, new BumpMovementComponent(dx, dy));

        if(targetId == playerEntityId)
            endPlayerTurn();
    }

    private void toggleExamineMode(long targetId) {
        if(world.getComponent(targetId, IsRemoteExamineComponent.class) != null) {
            world.removeEntity(targetId);
        } else {
            RemoteExamineBuilder.buildRemoteExamine(world, targetId);
        }
    }

    private void endPlayerTurn() {
        System.out.println("End player's turn.");
    }
}
