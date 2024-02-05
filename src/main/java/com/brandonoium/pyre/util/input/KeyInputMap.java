package com.brandonoium.pyre.util.input;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class KeyInputMap {
    private Map<Integer, InputAction> actionMap;

    public KeyInputMap() {
        actionMap = new HashMap<>();
    }

    public InputAction mapKeycode(int keycode) {
        return actionMap.getOrDefault(keycode, InputAction.NULL_ACTION);
    }

    public void setMapping(int keycode, InputAction action) {
        actionMap.put(keycode, action);
    }

    public Set<Entry<Integer, InputAction>> getMappings() {
        return actionMap.entrySet();
    }

    public void clearMapping(int keycode) {
        actionMap.remove(keycode);
    }
}
