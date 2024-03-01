package com.brandonoium.pyre.ui.widgets.containers;

import com.brandonoium.bithorse.BitHorseTerminalBuffer;
import com.brandonoium.pyre.ui.widgets.TerminalUiWidget;

import java.util.ArrayList;

public abstract class ContainerWidget extends TerminalUiWidget {

    private ArrayList<TerminalUiWidget> children;

    public ContainerWidget(int w, int h) {
        super(w, h);
        children = new ArrayList<>();
    }

    public ContainerWidget(int w, int h, int x, int y) {
        super(w, h, x, y);
        children = new ArrayList<>();
    }

    public BitHorseTerminalBuffer draw() {
        for(TerminalUiWidget child : children) {
            buffer.copyFromBuffer(child.draw(), child.getX(), child.getY());
        }
        return buffer;
    }

    public void addChild(TerminalUiWidget child) {
        children.add(child);
    }
}
