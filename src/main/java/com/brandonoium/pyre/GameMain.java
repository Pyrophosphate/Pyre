package com.brandonoium.pyre;

import com.brandonoium.bithorse.BitDisplay;
import com.brandonoium.bithorse.BitHorseApp;

public class GameMain extends BitHorseApp {

    private int width = 320;
    private int height = 260;
    private BitDisplay display;

    public GameMain(String title) {
        super(title, 320, 260);
    }

    @Override
    public void init() {
        display = super.getDisplay();

        this.setupDisplay();
    }

    @Override
    public void run() {
        display.paintScaled();
    }
}
