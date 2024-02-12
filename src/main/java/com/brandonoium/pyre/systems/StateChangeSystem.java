package com.brandonoium.pyre.systems;

import com.brandonoium.pyre.ecs.EcsWorld;
import com.brandonoium.pyre.ecs.EcsSystem;
import com.brandonoium.pyre.gamestates.EnemyTurnState;

public class StateChangeSystem extends EcsSystem {

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
