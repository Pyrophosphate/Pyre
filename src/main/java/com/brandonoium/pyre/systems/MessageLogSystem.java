package com.brandonoium.pyre.systems;

import com.brandonoium.pyre.components.MessageLogOutputComponent;
import com.brandonoium.pyre.ecs.EcsWorld;
import com.brandonoium.pyre.ecs.IComponent;
import com.brandonoium.pyre.ecs.ISystem;
import com.brandonoium.pyre.ui.MessageLogWidget;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

public class MessageLogSystem extends ISystem {

    private MessageLogWidget widget;

    public MessageLogSystem(EcsWorld world, MessageLogWidget widget) {
        super(world);

        this.widget = widget;
    }


    @Override
    public void run() {
        //widget.shiftOldMessages();

        Map<Long, IComponent> messages = world.getComponentsByType(MessageLogOutputComponent.class);
        ArrayList<Long> idsToRemove = new ArrayList<>();

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
