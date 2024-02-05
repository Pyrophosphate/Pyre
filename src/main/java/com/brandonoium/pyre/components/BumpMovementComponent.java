package com.brandonoium.pyre.components;

import com.brandonoium.pyre.ecs.IComponent;

public class BumpMovementComponent implements IComponent {
    private int xBump;
    private int yBump;

    public BumpMovementComponent(int xBump, int yBump) {
        this.xBump = xBump;
        this.yBump = yBump;
    }

    public int getxBump() {
        return xBump;
    }

    public int getyBump() {
        return yBump;
    }
}
