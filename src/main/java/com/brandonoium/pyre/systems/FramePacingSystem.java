package com.brandonoium.pyre.systems;

import com.brandonoium.pyre.ecs.EcsWorld;
import com.brandonoium.pyre.ecs.EcsSystem;

import static java.lang.Thread.sleep;

public class FramePacingSystem extends EcsSystem {

    static FramePacingSystem singleton;

    public static FramePacingSystem getSystem(EcsWorld world, long pacing) {
        if(singleton == null) {
            singleton = new FramePacingSystem(world, pacing);
        }
        return singleton;
    }

    public static FramePacingSystem getSystemIfExists() {
        return singleton;
    }

    private long lastTime;
    private long msToWait;

    private FramePacingSystem(EcsWorld world, long pacing) {
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
