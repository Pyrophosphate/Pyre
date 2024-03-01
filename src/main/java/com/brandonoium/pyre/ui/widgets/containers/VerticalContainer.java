package com.brandonoium.pyre.ui.widgets.containers;

import com.brandonoium.pyre.ui.widgets.TerminalUiWidget;

public class VerticalContainer extends ContainerWidget {

    private int nextYPosition = 0;

    public VerticalContainer(int w, int h) {
        super(w, h);
    }

    public VerticalContainer(int w, int h, int x, int y) {
        super(w, h, x, y);
    }


    @Override
    public void addChild(TerminalUiWidget child) {
        child.setPosition(child.getX(), nextYPosition);
        if(child.getWidth() < this.getWidth()) {
            child.setSize(Math.min(child.getMaxWidth(), this.getWidth()), child.getHeight());
        }
        super.addChild(child);

        nextYPosition += child.getHeight();
    }
}
