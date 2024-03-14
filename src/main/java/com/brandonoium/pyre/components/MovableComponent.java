package com.brandonoium.pyre.components;

import com.brandonoium.pyre.ecs.IComponent;

public class MovableComponent implements IComponent {

    private boolean canWalk;
    private boolean canFly;
    private boolean canPhase;

    public MovableComponent(boolean canWalk, boolean canFly, boolean canPhase) {
        this.canWalk = canWalk;
        this.canFly = canFly;
        this.canPhase = canPhase;
    }

    public boolean canWalk() {
        return canWalk;
    }

    public void setCanWalk(boolean canWalk) {
        this.canWalk = canWalk;
    }

    public boolean canFly() {
        return canFly;
    }

    public void setCanFly(boolean canFly) {
        this.canFly = canFly;
    }

    public boolean canPhase() {
        return canPhase;
    }

    public void setCanPhase(boolean canPhase) {
        this.canPhase = canPhase;
    }
}
