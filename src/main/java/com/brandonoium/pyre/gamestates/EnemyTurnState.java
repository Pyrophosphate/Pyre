package com.brandonoium.pyre.gamestates;

import com.brandonoium.pyre.ecs.ISystem;
import com.brandonoium.pyre.systems.PlayerInputSystem;

import java.util.LinkedList;
import java.util.Queue;

public class EnemyTurnState implements GameState {

    private Queue<ISystem> systemQueue;
    private StateManager stateManager;
    private PlayerTurnState playerTurnState;

    public EnemyTurnState(StateManager stateManager) {
        this.stateManager = stateManager;
        systemQueue = new LinkedList<>();
    }

    public void setPlayerTurnState(PlayerTurnState state) {
        playerTurnState = state;
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
    public GameState runSystems() {
        for(ISystem s : systemQueue) {
            s.run();
        }
        return null;
    }


    public void playerTurnState() {
        stateManager.setCurrentState(playerTurnState);
    }
}
