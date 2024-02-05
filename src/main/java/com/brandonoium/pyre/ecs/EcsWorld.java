package com.brandonoium.pyre.ecs;

import java.util.*;
import java.util.Map.Entry;

public class EcsWorld {
    private HashMap<Long, HashMap<Class, IComponent>> componentsById;
    private HashMap<Class, HashMap<Long, IComponent>> componentsByType;

    private long nextEntityId = 0;

    private long playerEntityId;
    private long globalEntityId;

    public EcsWorld() {
        componentsById = new HashMap<>();
        componentsByType = new HashMap<>();

        globalEntityId = newEntityId();
    }


    public void setPlayerEntityId(long pid) {
        playerEntityId = pid;
    }

    public long getPlayerEntityId() {
        return playerEntityId;
    }

    public long getGlobalEntityId() {
        return globalEntityId;
    }


    public long newEntityId() {
        componentsById.put(nextEntityId, new HashMap<>());

        return nextEntityId++;
    }

    public long addNewEntityWithComponents(Set<IComponent> components) {
        long id = newEntityId();

        for(IComponent c : components) {
            addComponent(id, c);
        }

        return id;
    }

    public void removeEntity(long id) {
        Set<Entry<Class, IComponent>> components = componentsById.get(id).entrySet();
        for(Entry<Class, IComponent> c : components) {
            IComponent tc = componentsByType.get(c.getValue().getClass()).remove(id);
        }

        componentsById.remove(id);
    }


    public void addComponent(long entityId, IComponent component) {
        componentsById.get(entityId).put(component.getClass(), component);

        if(!componentsByType.containsKey(component.getClass())){
            componentsByType.put(component.getClass(), new HashMap<>());
        }
        componentsByType.get(component.getClass()).put(entityId, component);
    }

    public void removeComponent(Long entityId, IComponent component) {
        componentsByType.get(component.getClass()).remove(entityId);

        componentsById.get(entityId).remove(component.getClass());
    }

    public void removeComponent(Long entityId, Class type) {
        componentsByType.get(type).remove(entityId);

        componentsById.get(entityId).remove(type);
    }

    public Map<Long, IComponent> getComponentsByType(Class type) {
        return componentsByType.get(type);
    }

    public Map<Class, IComponent> getComponentsById(long entityId) {
        return componentsById.get(entityId);
    }
}
