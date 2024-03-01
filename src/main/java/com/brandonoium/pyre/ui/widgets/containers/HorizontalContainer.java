package com.brandonoium.pyre.ui.widgets.containers;

import com.brandonoium.pyre.ui.widgets.TerminalUiWidget;

public class HorizontalContainer extends ContainerWidget {

    private int nextXPosition = 0;

    public HorizontalContainer(int w, int h) {
        super(w, h);
    }

    public HorizontalContainer(int w, int h, int x, int y) {
        super(w, h, x, y);
    }


    @Override
    public void addChild(TerminalUiWidget child) {
        child.setPosition(nextXPosition, child.getY());
        if(child.getHeight() < this.getHeight()) {
            child.setSize(child.getWidth(), Math.min(child.getMaxHeight(), this.getHeight()));
        }
        super.addChild(child);

        nextXPosition += child.getWidth();
    }
}