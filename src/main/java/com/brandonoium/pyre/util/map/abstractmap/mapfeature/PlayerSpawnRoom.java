package com.brandonoium.pyre.util.map.abstractmap.mapfeature;

import com.brandonoium.pyre.components.PlayerSpawnerComponent;
import com.brandonoium.pyre.ecs.EcsWorld;
import com.brandonoium.pyre.util.map.GameMap;

import java.util.Random;

public class PlayerSpawnRoom extends MapRoom {

    private EcsWorld world;

    public PlayerSpawnRoom(int xPosition, int yPosition, int width, int height, EcsWorld world) {
        super(xPosition, yPosition, width, height);
        this.world = world;
    }


    @Override
    public void renderToMap(GameMap map, Random rng) {
        super.renderToMap(map, rng);

        int minX = this.getX() + 1;
        int maxX = minX + this.getWidth() - 2;
        int minY = this.getY() + 1;
        int maxY = minY + this.getHeight() - 2;

        int x = rng.nextInt(minX, maxX);
        int y = rng.nextInt(minY, maxY);

        world.addComponent(world.newEntityId(), new PlayerSpawnerComponent(x, y));
    }
}
