package com.brandonoium.pyre.systems;

import com.brandonoium.bithorse.BitHorseTerminal;
import com.brandonoium.pyre.ecs.EcsWorld;
import com.brandonoium.pyre.ecs.ISystem;
import com.brandonoium.pyre.ui.TerminalUiWidget;

public class FinalTerminalRenderingSystem extends ISystem {

    //private EcsWorld world;
    private BitHorseTerminal term;
    private TerminalUiWidget rootWidget;

    public FinalTerminalRenderingSystem(EcsWorld world, BitHorseTerminal term, TerminalUiWidget rootWidget) {
        super(world);
        this.term = term;
        this.rootWidget = rootWidget;
    }


    @Override
    public void run() {
        term.drawBufferAt(rootWidget.draw(), 0, 0);
        term.run();
    }
}
