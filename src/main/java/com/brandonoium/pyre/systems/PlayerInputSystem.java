package com.brandonoium.pyre.systems;

import com.brandonoium.pyre.components.*;
import com.brandonoium.pyre.ecs.EcsWorld;
import com.brandonoium.pyre.ecs.IComponent;
import com.brandonoium.pyre.ecs.EcsSystem;
import com.brandonoium.pyre.entitybuilders.RemoteExamineBuilder;
import com.brandonoium.pyre.gamestates.PlayerTurnState;
import com.brandonoium.pyre.util.Location;
import com.brandonoium.pyre.util.input.KeyInput;
import com.brandonoium.pyre.util.map.MapService;

import java.util.Map;

/**
 * A system that turns keyboard (for now) inputs into actions that can be assigned to the player object.
 *
 * The entity that receives the action is the PlayerControlComponent with the highest priority.
 *
 * PlayerInputSystem extends EcsSystem, but at this time, the "run()" method does nothing. run() may be used for mouse/joystick movement polling, if desired.
 */
public class PlayerInputSystem extends EcsSystem {

    //private EcsWorld world;
    private long playerEntityId;
    private PlayerTurnState playerTurnState;
    private MapService map;


    public PlayerInputSystem(EcsWorld world, long playerEntityId, PlayerTurnState playerTurnState, MapService map) {
        super(world);
        this.playerEntityId = playerEntityId;
        this.playerTurnState = playerTurnState;
        this.map = map;
    }


    @Override
    public void run() {

    }


    public void doAction(KeyInput input) {

        Map<Long, IComponent> inputTargets = world.getComponentsByType(PlayerControlComponent.class);
        int maxPriority = Integer.MIN_VALUE;
        long targetEntity = 0;
        for(Map.Entry<Long, IComponent> c : inputTargets.entrySet()) {
            PlayerControlComponent comp = (PlayerControlComponent) c.getValue();
            if(comp.getPriority() > maxPriority) {
                targetEntity = c.getKey();
                maxPriority = comp.getPriority();
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
            case WAIT -> {
                waitAction(targetEntity);
                break;
            }
        }
    }


    private void moveAction(long targetId, int dx, int dy) {
        MoveableComponent moveable = ((MoveableComponent) world.getComponent(targetId, MoveableComponent.class));
        if(moveable != null) {
            Location entityLocation = ((LocationComponent) world.getComponent(targetId, LocationComponent.class)).getLoc();

            if (canMove(moveable, entityLocation.getX() + dx, entityLocation.getY() + dy)) {
                world.addComponent(targetId, new BumpMovementComponent(dx, dy));

                if(targetId == playerEntityId)
                    endPlayerTurn();
            }
        }
    }

    private boolean canMove(MoveableComponent moveable, int targetX, int targetY) {
        if (map.isValidLocation(targetX, targetY)) {
            if (moveable.canPhase()) {
                return true;
            } else if (moveable.canFly() && map.canFlyAt(targetX, targetY)) {
                return true;
            } else if (moveable.canWalk() && map.canWalkAt(targetX, targetY)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private void waitAction(long targetId) {
        if(targetId == playerEntityId) {
            endPlayerTurn();
            world.addComponent(targetId, new MessageLogOutputComponent("Waiting..."));
        }
    }

    private void toggleExamineMode(long targetId) {
        if(world.getComponent(targetId, IsRemoteExamineComponent.class) != null) {
            world.removeEntity(targetId);
        } else {
            RemoteExamineBuilder.buildRemoteExamine(world, targetId);
        }
    }

    private void endPlayerTurn() {
        playerTurnState.enemyTurnState();
    }
}
