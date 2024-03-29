package com.brandonoium.pyre.systems;

import com.brandonoium.bithorse.BitHorseTerminal;
import com.brandonoium.pyre.ecs.EcsWorld;
import com.brandonoium.pyre.ecs.EcsSystem;
import com.brandonoium.pyre.ui.widgets.TerminalUiWidget;

public class FinalTerminalRenderingSystem extends EcsSystem {

    static FinalTerminalRenderingSystem singleton;

    public static FinalTerminalRenderingSystem getSystem(EcsWorld world, BitHorseTerminal term, TerminalUiWidget rootWidget) {
        if(singleton == null) {
            singleton = new FinalTerminalRenderingSystem(world, term, rootWidget);
        }
        return singleton;
    }

    public static FinalTerminalRenderingSystem getSystemIfExists() {
        return singleton;
    }

    private BitHorseTerminal term;
    private TerminalUiWidget rootWidget;

    private FinalTerminalRenderingSystem(EcsWorld world, BitHorseTerminal term, TerminalUiWidget rootWidget) {
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
