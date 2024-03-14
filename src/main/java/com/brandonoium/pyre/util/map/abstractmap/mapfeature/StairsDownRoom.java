package com.brandonoium.pyre.util.map.abstractmap.mapfeature;

import com.brandonoium.pyre.components.PlayerSpawnerComponent;
import com.brandonoium.pyre.ecs.EcsWorld;
import com.brandonoium.pyre.entitybuilders.StairsDownBuilder;
import com.brandonoium.pyre.util.map.GameMap;

import java.util.Random;

public class StairsDownRoom extends MapRoom {

    public StairsDownRoom(int xPosition, int yPosition, int width, int height) {
        super(xPosition, yPosition, width, height);
    }


    @Override
    public void renderToMap(GameMap map, Random rng, EcsWorld world) {
        super.renderToMap(map, rng, world);

        int minX = this.getX() + 1;
        int maxX = minX + this.getWidth() - 2;
        int minY = this.getY() + 1;
        int maxY = minY + this.getHeight() - 2;

        int x = rng.nextInt(minX, maxX);
        int y = rng.nextInt(minY, maxY);

        StairsDownBuilder.buildStairsDown(world, x, y);
    }
}
