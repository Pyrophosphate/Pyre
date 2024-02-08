package com.brandonoium.pyre.util.map;

import com.brandonoium.bithorse.CharSetMap;

public enum MapTileType {

    WALL("Wall", CharSetMap.BLOCK_FULL, true, false, false),
    FLOOR("Floor", '.', false, true, true),
    WINDOW("Window", CharSetMap.BLOCK_LIGHT_TRANSPARENT, false, false, false);

    private final String friendlyName;
    private final int glyph;
    private final boolean opaque;
    private final boolean canWalk;
    private final boolean canFly;

    MapTileType(String friendlyName, int glyph, boolean opaque, boolean canWalk, boolean canFly) {
        this.friendlyName = friendlyName;
        this.glyph = glyph;
        this.opaque = opaque;
        this.canWalk = canWalk;
        this.canFly = canFly;
    }

    public boolean canFly() {
        return canFly;
    }

    public boolean canWalk() {
        return canWalk;
    }

    public boolean isOpaque() {
        return opaque;
    }

    public int getGlyph() {
        return glyph;
    }

    public String getFriendlyName() {
        return friendlyName;
    }
}
