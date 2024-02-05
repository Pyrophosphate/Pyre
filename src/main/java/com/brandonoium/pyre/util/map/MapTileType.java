package com.brandonoium.pyre.util.map;

import com.brandonoium.bithorse.CharSetMap;

public enum MapTileType {

    WALL(CharSetMap.BLOCK_FULL, true, false, false),
    FLOOR('.', false, true, true),
    WINDOW(CharSetMap.BLOCK_LIGHT_TRANSPARENT, false, false, false);

    private final int glyph;
    private final boolean opaque;
    private final boolean canWalk;
    private final boolean canFly;
    MapTileType(int glyph, boolean opaque, boolean canWalk, boolean canFly) {
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
}
