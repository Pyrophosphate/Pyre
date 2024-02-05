package com.brandonoium.pyre.util.input;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class InputService extends KeyAdapter {

    private KeyInputMap map;

    public InputService() {

    }

    public void setKeyInputMap(KeyInputMap newMap) {
        map = newMap;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //System.out.println(e.paramString());
        InputAction action = map.mapKeycode(e.getKeyCode());
        KeyInput key = new KeyInput(action, e.getKeyChar());
        System.out.println(key);
    }
}
