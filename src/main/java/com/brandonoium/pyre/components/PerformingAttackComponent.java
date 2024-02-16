package com.brandonoium.pyre.components;

import com.brandonoium.pyre.ecs.IComponent;

public class PerformingAttackComponent implements IComponent {
    private long attackerId;
    private long targetId;

    public PerformingAttackComponent(long attackerId, long targetId) {
        this.attackerId = attackerId;
        this.targetId = targetId;
    }

    public long getAttackerId() {
        return attackerId;
    }

    public long getTargetId() {
        return targetId;
    }
}
