package com.brandonoium.pyre.ui.widgets;

import com.brandonoium.bithorse.BitHorseTerminalBuffer;

public class BasicWidget extends TerminalUiWidget {

    public BasicWidget(int w, int h) {
        super(w, h);
    }

    public BasicWidget(int w, int h, int x, int y) {
        super(w, h, x, y);
    }

    @Override
    public BitHorseTerminalBuffer draw() {
        return this.buffer;
    }
}
