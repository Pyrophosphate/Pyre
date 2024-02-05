package com.brandonoium.pyre.systems;

import com.brandonoium.pyre.ecs.EcsWorld;
import com.brandonoium.pyre.ecs.ISystem;

import static java.lang.Thread.sleep;

public class FramePacingSystem implements ISystem {

    private long lastTime;
    private long msToWait;

    public FramePacingSystem(long pacing) {
        msToWait = pacing;
        lastTime = System.currentTimeMillis();
    }

    @Override
    public void init(EcsWorld world) {

    }

    @Override
    public void run() {
        long dt = System.currentTimeMillis() - lastTime;
        if (dt < msToWait) {
            try {
                sleep(msToWait - dt);
            } catch (InterruptedException e) {
                ;//throw new RuntimeException(e);
            }
        }
        lastTime = System.currentTimeMillis();
    }
}