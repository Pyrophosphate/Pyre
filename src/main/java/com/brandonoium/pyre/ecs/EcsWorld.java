package com.brandonoium.pyre.ecs;

import com.brandonoium.pyre.components.LocationComponent;
import com.brandonoium.pyre.util.Location;
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
    private LocationIndex entitiesByLocation;

    private long nextEntityId = 0;

    private long playerEntityId;
    private long globalEntityId;

    public EcsWorld() {
        componentsById = new HashMap<>();
        componentsByType = new HashMap<>();
        entitiesByLocation = new LocationIndex();

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
        if(component.getClass() == LocationComponent.class) {
            Location lc = ((LocationComponent) component).getLoc();
            entitiesByLocation.addEntityToLocation(lc.getX(), lc.getY(), entityId);
        }
        componentsById.get(entityId).put(component.getClass(), component);

        if(!componentsByType.containsKey(component.getClass())){
            componentsByType.put(component.getClass(), new HashMap<>());
        }
        componentsByType.get(component.getClass()).put(entityId, component);
    }

    public void removeComponent(Long entityId, IComponent component) {
        if(component.getClass() == LocationComponent.class) {
            Location lc = ((LocationComponent) component).getLoc();
            entitiesByLocation.removeEntityFromLocation(lc.getX(), lc.getY(), entityId);
        }
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
        Map<Class, IComponent> map = componentsById.get(entityId);
        if(map != null)
            return map.get(type);
        else
            return null;
    }


    public void removeEmptyEntities() {
        ArrayList<Long> ids = new ArrayList<>();

        for(Entry<Long, HashMap<Class, IComponent>> entity : componentsById.entrySet()) {
            long key = entity.getKey();
            if(componentsById.get(key).isEmpty()) {
                ids.add(key);
            }
        }

        for(long id : ids) {
            removeEntity(id);
        }
    }


    public LocationIndex getLocationIndex() {
        return entitiesByLocation;
    }


    public void updateLocation(LocationComponent oldLocation, Location newLocation, long entityId) {
        removeComponent(entityId, oldLocation);
        addComponent(entityId, new LocationComponent(newLocation));
    }
}
