package com.brandonoium.pyre.ecs;

import com.brandonoium.pyre.util.LocationIndex;

import java.util.*;
import java.util.Map.Entry;

/**
 * The core of the ECS design pattern, EcsWorld maintains lists of all components by type and by entityID.
 */
public class EcsWorld {
    // These HashMaps are a simple way to get started with an ECS, but performance may not hold up for large projects.
    // If performance becomes an issue, either implement a sparse array pool or grab an existing library.
    private HashMap<Long, HashMap<Class, IComponent>> componentsById;
    private HashMap<Class, HashMap<Long, IComponent>> componentsByType;

    // Location is the only specific component type that will have its own index.
    private LocationIndex componentsByLocation;

    private long nextEntityId = 0;

    private long playerEntityId;
    private long globalEntityId;

    public EcsWorld() {
        componentsById = new HashMap<>();
        componentsByType = new HashMap<>();
        componentsByLocation = new LocationIndex();

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
        nextEntityId++;

        return nextEntityId - 1;
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
        if(!componentsByType.containsKey(type))
            componentsByType.put(type, new HashMap<>());
        return componentsByType.get(type);
    }

    public Map<Class, IComponent> getComponentsById(long entityId) {
        return componentsById.get(entityId);
    }

    public IComponent getComponent(long entityId, Class type) {
        return componentsById.get(entityId).get(type);
    }
}
