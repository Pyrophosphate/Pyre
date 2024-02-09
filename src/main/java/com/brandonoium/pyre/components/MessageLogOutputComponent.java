package com.brandonoium.pyre.components;

import com.brandonoium.pyre.ecs.IComponent;

public class MessageLogOutputComponent implements IComponent {
    private String msg;

    public MessageLogOutputComponent(String msg) {
        this.msg = msg;
    }

    public String getMessage() {
        return msg;
    }

    public void setMessage(String msg) {
        this.msg = msg;
    }
}
