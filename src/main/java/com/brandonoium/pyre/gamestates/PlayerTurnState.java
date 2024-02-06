package com.brandonoium.pyre.gamestates;

import com.brandonoium.pyre.components.BumpMovementComponent;
import com.brandonoium.pyre.ecs.EcsWorld;
import com.brandonoium.pyre.ecs.ISystem;

import java.util.LinkedList;
import java.util.Queue;

public class PlayerTurnState implements GameState {

    private Queue<ISystem> systemQueue;

    public PlayerTurnState() {
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
    public void runSystems() {
        for(ISystem s : systemQueue) {
            s.run();
        }
    }
}
