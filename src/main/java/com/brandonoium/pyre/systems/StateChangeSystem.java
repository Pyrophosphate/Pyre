package com.brandonoium.pyre.systems;

import com.brandonoium.pyre.ecs.EcsWorld;
import com.brandonoium.pyre.ecs.ISystem;
import com.brandonoium.pyre.gamestates.EnemyTurnState;
import com.brandonoium.pyre.gamestates.StateManager;

public class StateChangeSystem extends ISystem {

    private EnemyTurnState state;

    public StateChangeSystem(EcsWorld world, EnemyTurnState state) {
        super(world);
        this.state = state;
    }

    @Override
    public void run() {
        playerTurnState();
    }

    public void playerTurnState() {
        state.playerTurnState();
    }
}
