package com.brandonoium.pyre.gamestates;

import com.brandonoium.pyre.ecs.ISystem;

import java.util.Queue;

public interface GameState {

    public void addSystem(ISystem sys);

    public Queue<ISystem> getSystems();

    public GameState runSystems();
}
