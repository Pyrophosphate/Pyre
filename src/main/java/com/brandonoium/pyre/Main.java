package com.brandonoium.pyre;

import com.brandonoium.bithorse.BitHorseTerminal;
import com.brandonoium.bithorse.InvalidCharSetException;
import com.brandonoium.pyre.components.LocationComponent;
import com.brandonoium.pyre.components.TerminalRenderableComponent;
import com.brandonoium.pyre.ecs.EcsWorld;
import com.brandonoium.pyre.entitybuilders.PlayerBuilder;
import com.brandonoium.pyre.gamestates.MainGameState;
import com.brandonoium.pyre.gamestates.StateManager;
import com.brandonoium.pyre.systems.TerminalRenderingSystem;
import com.brandonoium.pyre.ui.BasicWidget;
import com.brandonoium.pyre.util.input.InputService;
import com.brandonoium.pyre.util.input.KeyInputMap;
import com.brandonoium.pyre.util.map.MapService;
import com.brandonoium.pyre.util.map.generator.EmptyRoomMapGenerator;

import java.awt.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        BitHorseTerminal term;
        try {
            term = new BitHorseTerminal("Pyre", 40, 30);
        } catch (InvalidCharSetException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        EcsWorld world = new EcsWorld();
        PlayerBuilder.buildPlayer(world);
        StateManager stateMgr = new StateManager(world);
        stateMgr.setCurrentState(new MainGameState());
        InputService input = new InputService(stateMgr);
        input.setKeyInputMap(new KeyInputMap());
        term.addKeyListener(input);
        stateMgr.setCurrentState(new MainGameState());

        term.setFgBgColors(Color.GRAY, Color.DARK_GRAY);

        BasicWidget root = new BasicWidget(40, 30, 0, 0);

        MapService map = new MapService();
        map.generateMap(new EmptyRoomMapGenerator(40, 30));

        BasicWidget renderingWidget = new BasicWidget(40, 30, 0, 0);
        root.addChild(renderingWidget);
        TerminalRenderingSystem tRend = new TerminalRenderingSystem(renderingWidget, map, 0, 0);
        tRend.init(world);

        long enemy = world.newEntityId();
        world.addComponent(enemy, new LocationComponent(5, 5));
        world.addComponent(enemy, new TerminalRenderableComponent('W'));

        tRend.run();
    }
}