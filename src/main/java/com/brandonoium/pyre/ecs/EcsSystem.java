package com.brandonoium.pyre.ecs;

public abstract class EcsSystem {

    protected EcsWorld world;

    public EcsSystem(EcsWorld world) {
        this.world = world;
    }

    public void init() {

    }

    public void run(){

    }
}
