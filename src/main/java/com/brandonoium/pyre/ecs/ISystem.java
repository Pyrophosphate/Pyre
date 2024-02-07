package com.brandonoium.pyre.ecs;

public class ISystem {

    protected EcsWorld world;

    public ISystem(EcsWorld world) {
        this.world = world;
    }

    public void init() {

    }

    public void run(){

    }
}
