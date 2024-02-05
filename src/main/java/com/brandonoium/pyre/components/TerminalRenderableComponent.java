package com.brandonoium.pyre.components;

import com.brandonoium.pyre.ecs.IComponent;

public class TerminalRenderableComponent implements IComponent {
    private int glyph;

    public TerminalRenderableComponent(int glyph) {
        this.glyph = glyph;
    }

    public int getGlyph() {
        return glyph;
    }

    public void setGlyph(int glyph) {
        this.glyph = glyph;
    }
}
