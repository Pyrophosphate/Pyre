package com.brandonoium.pyre.util.input;

import com.brandonoium.pyre.gamestates.StateManager;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class InputService extends KeyAdapter {

    private KeyInputMap map;
    private StateManager stateManager;

    public InputService(StateManager state) {
        stateManager = state;
    }

    public void setKeyInputMap(KeyInputMap newMap) {
        map = newMap;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        InputAction action = map.mapKeycode(e.getKeyCode());
        KeyInput key = new KeyInput(action, e.getKeyChar());
        System.out.println(key);
        stateManager.doAction(key);
    }
}
