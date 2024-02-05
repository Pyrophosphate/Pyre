package com.brandonoium.pyre.gamestates;

import com.brandonoium.pyre.components.BumpMovementComponent;
import com.brandonoium.pyre.ecs.EcsWorld;
import com.brandonoium.pyre.ecs.ISystem;
import com.brandonoium.pyre.util.input.KeyInput;

import java.util.LinkedList;
import java.util.Queue;

public class MainGameState implements GameState {

    private Queue<ISystem> systemQueue;

    public MainGameState() {
        systemQueue = new LinkedList<>();
    }

    @Override
    public void addSystem(ISystem sys) {
        systemQueue.add(sys);
    }

    @Override
    public Queue<ISystem> getSystems() {
        return systemQueue;
    }

    @Override
    public void handleAction(StateManager mgr, EcsWorld world, KeyInput action) {
        switch (action.getAction()) {
            case MOVE_DOWN -> moveAction(world, 0, 1);
            case MOVE_UP -> moveAction(world, 0, -1);
            case MOVE_RIGHT -> moveAction(world, 1, 0);
            case MOVE_LEFT -> moveAction(world, -1, 0);
        }
    }

    @Override
    public void runSystems() {
        for(ISystem s : systemQueue) {
            s.run();
        }
    }


    private void moveAction(EcsWorld world, int dx, int dy) {
        world.addComponent(world.getPlayerEntityId(), new BumpMovementComponent(dx, dy));
    }


}
