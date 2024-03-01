package com.brandonoium.pyre.ui.widgets;

import com.brandonoium.bithorse.BitHorseTerminalBuffer;
import com.brandonoium.bithorse.CharSetMap;
import com.brandonoium.bithorse.CursorOutOfBoundsException;

public class VScrollBarWidget extends TerminalUiWidget {
    private int partnerHeight;

    public VScrollBarWidget(int h, int x, int y, int partnerHeight) {
        super(1, h, x, y);
        this.partnerHeight = partnerHeight;
    }

    @Override
    public BitHorseTerminalBuffer draw() {
        int height = getHeight();
        if(height < 4)
            return this.buffer;
        else if(height >= 4) {
            try {
                buffer.setCursor(0, 0);
                buffer.printGlyph(CharSetMap.UP_ARROW);
                for(int y = 1; y < height - 1; y++) {
                    buffer.setCursor(0, y);
                    buffer.printGlyph(CharSetMap.SINGLE_PIPE_UD);
                }
                buffer.setCursor(0, height - 1);
                buffer.printGlyph(CharSetMap.DOWN_ARROW);
            } catch (CursorOutOfBoundsException e) {
                throw new RuntimeException(e);
            }
        }

        return buffer;
    }

    @Override
    public int getMinWidth() {
        return 1;
    }

    @Override
    public int getMaxWidth() {
        return 1;
    }

    @Override
    public int getMinHeight() {
        return 4;
    }
}
