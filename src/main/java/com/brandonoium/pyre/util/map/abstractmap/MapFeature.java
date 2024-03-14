package com.brandonoium.pyre.util.map.abstractmap;

import com.brandonoium.pyre.ecs.EcsWorld;
import com.brandonoium.pyre.util.map.GameMap;

import java.util.Random;

public abstract class MapFeature {

    public abstract void renderToMap(GameMap map, Random rng, EcsWorld world);
}
