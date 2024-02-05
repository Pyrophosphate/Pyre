package com.brandonoium.pyre.systems;

import com.brandonoium.bithorse.BitHorseTerminal;
import com.brandonoium.pyre.ecs.EcsWorld;
import com.brandonoium.pyre.ecs.ISystem;

public class FinalTerminalRenderingSystem implements ISystem {

    private EcsWorld world;
    private BitHorseTerminal term;

    public FinalTerminalRenderingSystem(BitHorseTerminal term) {
        this.term = term;
    }

    @Override
    public void init(EcsWorld world) {
        this.world = world;
    }

    @Override
    public void run() {
        term.drawBufferAt(root.draw(), 0, 0);
        term.run();
    }
}
