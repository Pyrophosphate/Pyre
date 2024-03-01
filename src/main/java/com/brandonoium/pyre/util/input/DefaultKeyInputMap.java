package com.brandonoium.pyre.util.input;

import java.awt.event.KeyEvent;

public class DefaultKeyInputMap {

    public static KeyInputMap getDefaultKeyInputMap() {
        KeyInputMap map = new KeyInputMap();

        map.setMapping(KeyEvent.VK_DOWN, InputAction.MOVE_DOWN);
        map.setMapping(KeyEvent.VK_UP, InputAction.MOVE_UP);
        map.setMapping(KeyEvent.VK_LEFT, InputAction.MOVE_LEFT);
        map.setMapping(KeyEvent.VK_RIGHT, InputAction.MOVE_RIGHT);

        map.setMapping(KeyEvent.VK_NUMPAD5, InputAction.WAIT);

        map.setMapping(KeyEvent.VK_X, InputAction.EXAMINE);

        //map.setMapping(KeyEvent.);

        return map;
    }

    public static KeyInputMap getDefaultShiftKeyInputMap() {
        KeyInputMap map = new KeyInputMap();

        map.setMapping(KeyEvent.VK_X, InputAction.REMOTE_EXAMINE);

        //map.setMapping(KeyEvent.);

        return map;
    }
}
