package com.brandonoium.pyre.systems;

import com.brandonoium.pyre.ecs.EcsWorld;
import com.brandonoium.pyre.ecs.EcsSystem;
import com.brandonoium.pyre.gamestates.EnemyTurnState;

public class StateChangeSystem extends EcsSystem {

    static StateChangeSystem singleton;

    public static StateChangeSystem getSystem(EcsWorld world, EnemyTurnState state) {
        if(singleton == null) {
            singleton = new StateChangeSystem(world, state);
        }
        return singleton;
    }

    public static StateChangeSystem getSystemIfExists() {
        return singleton;
    }


    private EnemyTurnState state;

    private StateChangeSystem(EcsWorld world, EnemyTurnState state) {
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
