package com.brandonoium.pyre;

import com.brandonoium.bithorse.BitHorseTerminal;
import com.brandonoium.bithorse.InvalidCharSetException;
import com.brandonoium.pyre.components.*;
import com.brandonoium.pyre.ecs.EcsWorld;
import com.brandonoium.pyre.entitybuilders.PlayerBuilder;
import com.brandonoium.pyre.gamestates.EnemyTurnState;
import com.brandonoium.pyre.gamestates.PlayerTurnState;
import com.brandonoium.pyre.gamestates.StateManager;
import com.brandonoium.pyre.systems.*;
import com.brandonoium.pyre.ui.widgets.BasicWidget;
import com.brandonoium.pyre.ui.widgets.containers.ContainerWidget;
import com.brandonoium.pyre.ui.widgets.MessageLogWidget;
import com.brandonoium.pyre.ui.widgets.containers.VerticalContainer;
import com.brandonoium.pyre.util.Location;
import com.brandonoium.pyre.util.input.DefaultKeyInputMap;
import com.brandonoium.pyre.util.input.InputService;
import com.brandonoium.pyre.util.map.MapService;
import com.brandonoium.pyre.util.map.generator.EmptyRoomMapGenerator;
import com.brandonoium.pyre.util.map.generator.MapGenException;
import com.brandonoium.pyre.util.map.generator.RoomGridMapGenerator;

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

        StateChangeSystem stateChangeSystem = StateChangeSystem.getSystem(world, enemyTurnState);
        AiControlSystem aiControlSystem = AiControlSystem.getSystem(world);
        FollowSimpleTargetSystem followSystem = FollowSimpleTargetSystem.getSystem(world);



        term.setFgBgColors(Color.GRAY, Color.DARK_GRAY);


        MapService map = new MapService();
        try {
            map.generateMap(new RoomGridMapGenerator(world, 80, 60));
        } catch (MapGenException e) {
            throw new RuntimeException(e);
        }

        PlayerInputSystem playerInput = PlayerInputSystem.getSystem(world, playerEntityId, playerTurnState, map);
        InputService input = new InputService(playerInput);
        input.setKeyInputMap(DefaultKeyInputMap.getDefaultKeyInputMap());
        input.setShiftKeyInputMap(DefaultKeyInputMap.getDefaultShiftKeyInputMap());
        term.addKeyListener(input);

        ContainerWidget root = new VerticalContainer(40, 30);
        BasicWidget renderingWidget = new BasicWidget(40, 26);
        MessageLogWidget messageWidget = new MessageLogWidget(5, 4, 0, 0, 10);
        root.addChild(messageWidget);
        root.addChild(renderingWidget);
        TerminalRenderingSystem tRend = TerminalRenderingSystem.getSystem(world, renderingWidget, map, 0, 0);
        MessageLogSystem messageSystem = MessageLogSystem.getSystem(world, messageWidget);

        long enemy = world.newEntityId();
        world.addComponent(enemy, new LocationComponent(5, 5));
        world.addComponent(enemy, new TerminalRenderableComponent('W'));
        world.addComponent(enemy, new SimpleMovementTargetComponent(new Location(35, 15)));
        world.addComponent(enemy, new DescriptionComponent("A wolf. Not a playful puppy."));
        world.addComponent(enemy, new HealthComponent(5));
        world.addComponent(enemy, new IsAttackableComponent());

        BumpMovementSystem bump = BumpMovementSystem.getSystem(world);
        FramePacingSystem framePacing = FramePacingSystem.getSystem(world, 16);

        FinalTerminalRenderingSystem finalRend = FinalTerminalRenderingSystem.getSystem(world, term, root);

        DebugCollisionSystem debugCollision = DebugCollisionSystem.getSystem(world);
        RemoteExamineSystem remoteExamineSystem = RemoteExamineSystem.getSystem(world);
        ExaminationSystem examinationSystem = ExaminationSystem.getSystem(world);
        RemoveJustMovedComponentSystem justMoved = RemoveJustMovedComponentSystem.getSystem(world);
        PerformingAttackSystem attackSystem = PerformingAttackSystem.getSystem(world);

        playerTurnState.initSystems();
        enemyTurnState.initSystems();

        while(true) {
            stateMgr.runSystems();
        }
    }
}