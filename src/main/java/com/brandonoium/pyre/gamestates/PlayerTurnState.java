package com.brandonoium.pyre.gamestates;

import com.brandonoium.pyre.ecs.EcsSystem;
import com.brandonoium.pyre.systems.*;

import java.util.LinkedList;
import java.util.Queue;

public class PlayerTurnState implements GameState {

    private StateManager stateManager;
    private EnemyTurnState enemyTurnState;


    private Queue<EcsSystem> systemQueue;

    public PlayerTurnState(StateManager stateManager) {
        this.stateManager = stateManager;
        systemQueue = new LinkedList<>();
    }

    public void setEnemyTurnState(EnemyTurnState state) {
        enemyTurnState = state;
    }


    @Override
    public void initSystems() {
        this.addSystem(PlayerInputSystem.getSystemIfExists());
        this.addSystem(PerformingAttackSystem.getSystemIfExists());
        this.addSystem(RemoteExamineSystem.getSystemIfExists());
        this.addSystem(RemoveJustMovedComponentSystem.getSystemIfExists());
        this.addSystem(BumpMovementSystem.getSystemIfExists());
        this.addSystem(FramePacingSystem.getSystemIfExists());
        this.addSystem(TerminalRenderingSystem.getSystemIfExists());
        this.addSystem(MessageLogSystem.getSystemIfExists());
        this.addSystem(ExaminationSystem.getSystemIfExists());
        this.addSystem(FinalTerminalRenderingSystem.getSystemIfExists());
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


    public void enemyTurnState() {
        if(enemyTurnState != null) {
            stateManager.setCurrentState(enemyTurnState);
        } else {
            System.out.println("Attempted transition to enemy turn state, but state is null.");
        }
    }
}
