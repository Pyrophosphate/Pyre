package com.brandonoium.pyre.ecs;

public interface ISystem {

    public void init(EcsWorld world);
    public void run();

}
