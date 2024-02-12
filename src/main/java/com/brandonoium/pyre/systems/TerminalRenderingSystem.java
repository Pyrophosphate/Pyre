package com.brandonoium.pyre.systems;

import com.brandonoium.bithorse.CursorOutOfBoundsException;
import com.brandonoium.pyre.components.CameraTargetComponent;
import com.brandonoium.pyre.components.LocationComponent;
import com.brandonoium.pyre.components.TerminalRenderableComponent;
import com.brandonoium.pyre.ecs.EcsWorld;
import com.brandonoium.pyre.ecs.IComponent;
import com.brandonoium.pyre.ecs.EcsSystem;
import com.brandonoium.pyre.ui.TerminalUiWidget;
import com.brandonoium.pyre.util.map.MapService;

import java.util.Map;
import java.util.Map.Entry;

/**
 * A system for drawing TerminalRenderableComponents and the underlying map data to a terminal window.
 *
 * Reads TerminalRenderableComponents, CameraTargetComponents and LocationComponents, does not write to any components.
 */
public class TerminalRenderingSystem  extends EcsSystem {

    private TerminalUiWidget widget;
    private int xPos, yPos;
    private MapService map;


    public TerminalRenderingSystem(EcsWorld world, TerminalUiWidget widget, MapService map, int x, int y) {
        super(world);
        this.widget = widget;
        xPos = x;
        yPos = y;
        this.map = map;
    }


    @Override
    public void run() {
        int camX = 0, camY = 0;
        int width = widget.getWidth();
        int height = widget.getHeight();
        Map<Long, IComponent> cameraTargets = world.getComponentsByType(CameraTargetComponent.class);
        int minPriority = Integer.MIN_VALUE;
        long targetEntity = 0;
        for(Entry<Long, IComponent> c : cameraTargets.entrySet()) {
            CameraTargetComponent comp = (CameraTargetComponent) c.getValue();
            if(comp.getPriority() > minPriority) {
                targetEntity = c.getKey();
                minPriority = comp.getPriority();
            }
        }
        LocationComponent lc = (LocationComponent) world.getComponentsById(targetEntity).get(LocationComponent.class);
        camX = lc.getLoc().getX();
        camY = lc.getLoc().getY();
        int xOffset = camX - (width / 2);
        int yOffset = camY - (height / 2);

        drawMap(xOffset, yOffset);

        Map<Long, IComponent> renderables = world.getComponentsByType(TerminalRenderableComponent.class);

        try {
            for(Entry<Long, IComponent> r : renderables.entrySet()) {
                TerminalRenderableComponent rend = (TerminalRenderableComponent) r.getValue();
                LocationComponent l = (LocationComponent) world.getComponentsById(r.getKey()).get(LocationComponent.class);

                try {
                    widget.getBuffer().setCursor(l.getLoc().getX() - xOffset, l.getLoc().getY() - yOffset);
                    widget.getBuffer().printGlyph(rend.getGlyph());
                } catch (CursorOutOfBoundsException e) {
                    ;
                }
            }
        } catch (NullPointerException e) {
            return;
        }
    }


    private void drawMap(int xOffset, int yOffset) {
        for(int y = 0; y < widget.getHeight(); y++) {
            for(int x = 0; x < widget.getWidth(); x++) {
                try {
                    widget.getBuffer().setCursor(x, y);
                    widget.getBuffer().printGlyph(map.getGlyphAt(x + xOffset, y + yOffset));
                } catch (CursorOutOfBoundsException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}
