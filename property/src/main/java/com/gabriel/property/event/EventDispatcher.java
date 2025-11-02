package com.gabriel.property.event;

import com.gabriel.property.property.Property;

import java.util.ArrayList;

public class EventDispatcher {

    private ArrayList<PropertyEventListener> listeners;

    public EventDispatcher() {
        this.listeners = new ArrayList<>();
    }

    public void addEventListener(PropertyEventListener eventListener) {
        listeners.add(eventListener);
    }

    public void removeEventListener(PropertyEventListener eventListener) {
        listeners.remove(eventListener);
    }

    public void dispatchUpdateEvent(Property property) {
        listeners.forEach(l -> l.onPropertyUpdated(property));
    }

    public void dispatchPropertyAddedEvent(Property property) {
        listeners.forEach(l -> l.onPropertyAdded(property));
    }
}
