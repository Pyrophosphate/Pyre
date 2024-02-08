package com.brandonoium.pyre.components;

import com.brandonoium.pyre.ecs.IComponent;

public class DescriptionComponent implements IComponent {
    private String desc;

    public DescriptionComponent(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
