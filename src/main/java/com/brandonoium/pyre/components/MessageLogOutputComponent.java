package com.brandonoium.pyre.components;

import com.brandonoium.pyre.ecs.EcsWorld;
import com.brandonoium.pyre.ecs.IComponent;

import java.util.ArrayList;

public class MessageLogOutputComponent implements IComponent {
    private String msg;
    private ArrayList<String> msgList;

    public static void addMessage(EcsWorld world, long entity, String msg) {
        MessageLogOutputComponent comp = (MessageLogOutputComponent) world.getComponent(entity, MessageLogOutputComponent.class);

        if(comp == null) {
            comp = new MessageLogOutputComponent();
            world.addComponent(entity, comp);
        }

        comp.msgList.add(msg);
    }


    private MessageLogOutputComponent() {
        msgList = new ArrayList<>();
    }

    public void clearMessages() {
        msgList.clear();
    }

    public ArrayList<String> getMessages() {
        return msgList;
    }
}
