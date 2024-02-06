package com.brandonoium.pyre.gamestates;

import com.brandonoium.pyre.ecs.EcsWorld;
import com.brandonoium.pyre.util.input.KeyInput;

public class StateManager {
    private GameState currentState;
    private EcsWorld world;

    public StateManager(EcsWorld world) {
        this.world = world;
    }


    public void setCurrentState(GameState state) {
        currentState = state;
    }

    public void runSystems() {
        currentState.runSystems();
    }
}
