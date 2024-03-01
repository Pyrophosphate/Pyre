package com.brandonoium.pyre.gamestates;

import com.brandonoium.pyre.ecs.EcsSystem;
import com.brandonoium.pyre.systems.*;

import java.util.LinkedList;
import java.util.Queue;

public class EnemyTurnState implements GameState {

    private Queue<EcsSystem> systemQueue;
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
    public void initSystems() {
        this.addSystem(AiControlSystem.getSystemIfExists());
        this.addSystem(PerformingAttackSystem.getSystemIfExists());
        this.addSystem(FollowSimpleTargetSystem.getSystemIfExists());
        this.addSystem(RemoveJustMovedComponentSystem.getSystemIfExists());
        this.addSystem(BumpMovementSystem.getSystemIfExists());
        this.addSystem(StateChangeSystem.getSystemIfExists());
    }

    @Override
    public void addSystem(EcsSystem sys) {
        if(sys != null)
            systemQueue.add(sys);
    }

    @Override
    public Queue<EcsSystem> getSystems() {
        return systemQueue;
    }

    @Override
    public GameState runSystems() {
        for(EcsSystem s : systemQueue) {
            s.run();
        }
        return null;
    }


    public void playerTurnState() {
        stateManager.setCurrentState(playerTurnState);
    }
}
