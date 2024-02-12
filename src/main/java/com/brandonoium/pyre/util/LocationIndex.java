package com.brandonoium.pyre.util;

import java.util.List;

public class LocationIndex {
    private QuadTreeNode<Long> quadTreeRoot;
    private int currentSize;
    private int x, y;


    public LocationIndex() {
        x = 0;
        y = 0;
        currentSize = 512;
        quadTreeRoot = new QuadTreeNode<>(0, 0, currentSize);
    }


    public List<Long> getEntitiesAt(int x, int y) {
        return quadTreeRoot.getDataAtLocation(x, y);
    }

    public void addEntityToLocation(int x, int y, long entityId) {
        quadTreeRoot.addDataAtLocation(x, y, entityId);
    }


    private void expandArea(GrowthDirection direction) {
        switch (direction) {
            case NE -> {
                y -= currentSize;
                break;
            }
            case NW -> {
                y -= currentSize;
                x -= currentSize;
                break;
            }
            case SW -> {
                x -= currentSize;
                break;
            }
        }

        currentSize *= 2;

        QuadTreeNode newRoot = new QuadTreeNode(x, y, currentSize);
        newRoot.growChildren();

        switch (direction) {
            case NE -> {
                newRoot.setSw(quadTreeRoot);
                break;
            }
            case NW -> {
                newRoot.setSe(quadTreeRoot);
                break;
            }
            case SW -> {
                newRoot.setNe(quadTreeRoot);
                break;
            }
            case SE -> {
                newRoot.setNw(quadTreeRoot);
                break;
            }
        }

        quadTreeRoot = newRoot;
    }



    private enum GrowthDirection {
        NE, NW, SE, SW;
    }
}
