package com.brandonoium.pyre.components;

import com.brandonoium.pyre.ecs.IComponent;

public class NameComponent implements IComponent {
    private String name;

    public NameComponent(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
