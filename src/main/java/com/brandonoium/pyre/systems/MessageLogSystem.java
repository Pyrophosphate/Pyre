package com.brandonoium.pyre.systems;

import com.brandonoium.pyre.components.MessageLogOutputComponent;
import com.brandonoium.pyre.ecs.EcsWorld;
import com.brandonoium.pyre.ecs.IComponent;
import com.brandonoium.pyre.ecs.EcsSystem;
import com.brandonoium.pyre.ui.MessageLogWidget;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

/**
 * A system that runs within the ECS to write text messages to a MessageLogWidget.
 *
 * MessageLogOutputComponents can be added to any entity and will be written to the given widget. The component is removed after the message is written.
 */
public class MessageLogSystem extends EcsSystem {

    static MessageLogSystem singleton;

    public static MessageLogSystem getSystem(EcsWorld world, MessageLogWidget widget) {
        if(singleton == null) {
            singleton = new MessageLogSystem(world, widget);
        }
        return singleton;
    }

    public static MessageLogSystem getSystemIfExists() {
        return singleton;
    }

    private MessageLogWidget widget;

    private MessageLogSystem(EcsWorld world, MessageLogWidget widget) {
        super(world);

        this.widget = widget;
    }


    @Override
    public void run() {
        Map<Long, IComponent> messages = world.getComponentsByType(MessageLogOutputComponent.class);
        ArrayList<Long> idsToRemove = new ArrayList<>(messages.size());

        for(Entry<Long, IComponent> msg : messages.entrySet()) {
            String message = ((MessageLogOutputComponent)msg.getValue()).getMessage();
            widget.postMessage(message);

            idsToRemove.add(msg.getKey());
        }

        for(long id : idsToRemove) {
            world.removeComponent(id, MessageLogOutputComponent.class);
        }
    }
}
