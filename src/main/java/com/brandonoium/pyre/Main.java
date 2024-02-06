package com.brandonoium.pyre;

import com.brandonoium.bithorse.BitHorseTerminal;
import com.brandonoium.bithorse.InvalidCharSetException;
import com.brandonoium.pyre.components.LocationComponent;
import com.brandonoium.pyre.components.TerminalRenderableComponent;
import com.brandonoium.pyre.ecs.EcsWorld;
import com.brandonoium.pyre.entitybuilders.PlayerBuilder;
import com.brandonoium.pyre.gamestates.PlayerTurnState;
import com.brandonoium.pyre.gamestates.StateManager;
import com.brandonoium.pyre.systems.*;
import com.brandonoium.pyre.ui.BasicWidget;
import com.brandonoium.pyre.util.input.DefaultKeyInputMap;
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
        long playerEntityId = PlayerBuilder.buildPlayer(world);
        StateManager stateMgr = new StateManager(world);
        PlayerTurnState playerTurnState = new PlayerTurnState();
        stateMgr.setCurrentState(playerTurnState);

        PlayerInputSystem playerInput = new PlayerInputSystem(playerEntityId);
        playerInput.init(world);
        InputService input = new InputService(playerInput);
        input.setKeyInputMap(DefaultKeyInputMap.getDefaultKeyInputMap());
        term.addKeyListener(input);

        term.setFgBgColors(Color.GRAY, Color.DARK_GRAY);


        MapService map = new MapService();
        map.generateMap(new EmptyRoomMapGenerator(40, 30));

        BasicWidget root = new BasicWidget(40, 30, 0, 0);
        BasicWidget renderingWidget = new BasicWidget(40, 30, 0, 0);
        root.addChild(renderingWidget);
        TerminalRenderingSystem tRend = new TerminalRenderingSystem(renderingWidget, map, 0, 0);
        tRend.init(world);

        long enemy = world.newEntityId();
        world.addComponent(enemy, new LocationComponent(5, 5));
        world.addComponent(enemy, new TerminalRenderableComponent('W'));

        BumpMovementSystem bump = new BumpMovementSystem();
        bump.init(world);
        FramePacingSystem framePacing = new FramePacingSystem(16);
        framePacing.init(world);

        FinalTerminalRenderingSystem finalRend = new FinalTerminalRenderingSystem(term, root);
        finalRend.init(world);

        playerTurnState.addSystem(playerInput);
        playerTurnState.addSystem(bump);
        playerTurnState.addSystem(framePacing);
        playerTurnState.addSystem(tRend);
        playerTurnState.addSystem(finalRend);

        while(true) {
            stateMgr.runSystems();
        }

        //tRend.run();
    }
}