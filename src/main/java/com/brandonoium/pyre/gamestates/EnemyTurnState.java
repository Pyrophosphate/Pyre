package com.brandonoium.pyre.gamestates;

import com.brandonoium.pyre.ecs.EcsWorld;
import com.brandonoium.pyre.ecs.ISystem;
import com.brandonoium.pyre.util.input.KeyInput;

import java.util.LinkedList;
import java.util.Queue;

public class EnemyTurnState implements GameState {

    private Queue<ISystem> systemQueue;

    public EnemyTurnState() {
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

    }

    @Override
    public void runSystems() {
        for(ISystem s : systemQueue) {
            s.run();
        }
    }
}
