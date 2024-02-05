package com.brandonoium.bithorse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;

/**
 *
 * @author oiumb
 */
public abstract class BitHorseApp extends JFrame {

    private BitDisplay display;
    //private BitInputManager input;

    public BitHorseApp(String title, int width, int height) {
        super(title);

        display = new BitDisplay(width, height, 3);

        //input = new BitInputManager();
        //this.addKeyListener(input);
    }

    public void setupDisplay() {
        this.add(display);
        display.setPreferredSize(new Dimension(display.getImageWidth(), display.getImageHeight()));
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }


    public abstract void init();
    public abstract void run();

    public BitDisplay getDisplay() {
        return display;
    }
}