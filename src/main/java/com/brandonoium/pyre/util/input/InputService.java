package com.brandonoium.pyre.util.input;

import com.brandonoium.pyre.gamestates.StateManager;
import com.brandonoium.pyre.systems.PlayerInputSystem;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class InputService extends KeyAdapter {

    private KeyInputMap map;
    private KeyInputMap shiftMap;
    private PlayerInputSystem pInput;

    public InputService(PlayerInputSystem playerInput) {
        pInput = playerInput;
    }

    public void setKeyInputMap(KeyInputMap newMap) {
        map = newMap;
    }

    public void setShiftKeyInputMap(KeyInputMap newMap) {
        shiftMap = newMap;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        InputAction action;
        if(e.isShiftDown()) {
            action = shiftMap.mapKeycode(e.getKeyCode());
        } else {

            action = map.mapKeycode(e.getKeyCode());
        }
        KeyInput key = new KeyInput(action, e.getKeyChar());
        //System.out.println(key);
        pInput.doAction(key);
    }
}
