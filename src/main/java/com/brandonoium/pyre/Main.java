package com.brandonoium.pyre;

import com.brandonoium.bithorse.BitHorseTerminal;
import com.brandonoium.bithorse.InvalidCharSetException;
import com.brandonoium.pyre.components.AIControlComponent;
import com.brandonoium.pyre.components.LocationComponent;
import com.brandonoium.pyre.components.SimpleMovementTargetComponent;
import com.brandonoium.pyre.components.TerminalRenderableComponent;
import com.brandonoium.pyre.ecs.EcsWorld;
import com.brandonoium.pyre.entitybuilders.PlayerBuilder;
import com.brandonoium.pyre.gamestates.EnemyTurnState;
import com.brandonoium.pyre.gamestates.PlayerTurnState;
import com.brandonoium.pyre.gamestates.StateManager;
import com.brandonoium.pyre.systems.*;
import com.brandonoium.pyre.ui.BasicWidget;
import com.brandonoium.pyre.ui.MessageLogWidget;
import com.brandonoium.pyre.util.Location;
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
        PlayerTurnState playerTurnState = new PlayerTurnState(stateMgr);
        stateMgr.setCurrentState(playerTurnState);
        EnemyTurnState enemyTurnState = new EnemyTurnState(stateMgr);
        playerTurnState.setEnemyTurnState(enemyTurnState);
        enemyTurnState.setPlayerTurnState(playerTurnState);

        StateChangeSystem stateChangeSystem = new StateChangeSystem(world, enemyTurnState);
        AiControlSystem aiControlSystem = new AiControlSystem(world);
        FollowSimpleTargetSystem followSystem = new FollowSimpleTargetSystem(world);



        term.setFgBgColors(Color.GRAY, Color.DARK_GRAY);


        MapService map = new MapService();
        map.generateMap(new EmptyRoomMapGenerator(40, 30));

        PlayerInputSystem playerInput = new PlayerInputSystem(world, playerEntityId, playerTurnState, map);
        InputService input = new InputService(playerInput);
        input.setKeyInputMap(DefaultKeyInputMap.getDefaultKeyInputMap());
        term.addKeyListener(input);

        BasicWidget root = new BasicWidget(40, 30, 0, 0);
        BasicWidget renderingWidget = new BasicWidget(40, 36, 0, 4);
        root.addChild(renderingWidget);
        MessageLogWidget messageWidget = new MessageLogWidget(40, 4, 0, 0, 10);
        root.addChild(messageWidget);
        TerminalRenderingSystem tRend = new TerminalRenderingSystem(world, renderingWidget, map, 0, 0);
        MessageLogSystem messageSystem = new MessageLogSystem(world, messageWidget);

        long enemy = world.newEntityId();
        world.addComponent(enemy, new LocationComponent(5, 5));
        world.addComponent(enemy, new TerminalRenderableComponent('W'));
        //world.addComponent(enemy, new AIControlComponent());
        world.addComponent(enemy, new SimpleMovementTargetComponent(new Location(35, 15)));

        BumpMovementSystem bump = new BumpMovementSystem(world);
        FramePacingSystem framePacing = new FramePacingSystem(world, 16);

        FinalTerminalRenderingSystem finalRend = new FinalTerminalRenderingSystem(world, term, root);

        playerTurnState.addSystem(playerInput);
        playerTurnState.addSystem(bump);
        playerTurnState.addSystem(framePacing);
        playerTurnState.addSystem(tRend);
        playerTurnState.addSystem(messageSystem);
        playerTurnState.addSystem(finalRend);

        enemyTurnState.addSystem(aiControlSystem);
        enemyTurnState.addSystem(followSystem);
        enemyTurnState.addSystem(bump);
        enemyTurnState.addSystem(stateChangeSystem);

        while(true) {
            stateMgr.runSystems();
        }

        //tRend.run();
    }
}