package com.brandonoium.pyre.gamestates;

import com.brandonoium.pyre.ecs.EcsSystem;

import java.util.Queue;

public interface GameState {

    public void initSystems();

    public void addSystem(EcsSystem sys);

    public Queue<EcsSystem> getSystems();

    public GameState runSystems();
}
