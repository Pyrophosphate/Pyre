package com.brandonoium.pyre.gamestates;

import com.brandonoium.pyre.ecs.EcsWorld;
import com.brandonoium.pyre.ecs.ISystem;
import com.brandonoium.pyre.util.input.KeyInput;

import java.util.Queue;

public interface GameState {

    public void addSystem(ISystem sys);

    public Queue<ISystem> getSystems();

    public void runSystems();
}
