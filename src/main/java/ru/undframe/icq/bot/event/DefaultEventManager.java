package ru.undframe.icq.bot.event;

import java.util.*;

public final class DefaultEventManager implements EventManager{

    private Map<EventType,Event> eventMap = new HashMap<>();

    @Override
    public void addListener(EventType eventType, Event event) {
        eventMap.put(eventType,event);
    }

    @Override
    public void removeListener(EventType eventType) {
        eventMap.remove(eventType);
    }

    @Override
    public Event getEvent(EventType eventType) {
        return eventMap.get(eventType);
    }
}
