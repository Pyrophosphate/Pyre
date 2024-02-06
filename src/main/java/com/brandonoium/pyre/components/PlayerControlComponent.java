package com.brandonoium.pyre.components;

import com.brandonoium.pyre.ecs.IComponent;

public class PlayerControlComponent implements IComponent {
    private int priority;

    public PlayerControlComponent(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int newPriority) {
        priority = newPriority;
    }
}
