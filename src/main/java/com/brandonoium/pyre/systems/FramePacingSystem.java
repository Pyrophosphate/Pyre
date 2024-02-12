package com.brandonoium.pyre.systems;

import com.brandonoium.pyre.ecs.EcsWorld;
import com.brandonoium.pyre.ecs.EcsSystem;

import static java.lang.Thread.sleep;

public class FramePacingSystem extends EcsSystem {

    private long lastTime;
    private long msToWait;

    public FramePacingSystem(EcsWorld world, long pacing) {
        super(world);
        msToWait = pacing;
        lastTime = System.currentTimeMillis();
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
