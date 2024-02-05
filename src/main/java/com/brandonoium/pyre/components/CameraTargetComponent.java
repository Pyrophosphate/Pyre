package com.brandonoium.pyre.components;

import com.brandonoium.pyre.ecs.IComponent;

public class CameraTargetComponent implements IComponent {
    private int priority;

    public CameraTargetComponent(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int newPriority) {
        priority = newPriority;
    }
}
