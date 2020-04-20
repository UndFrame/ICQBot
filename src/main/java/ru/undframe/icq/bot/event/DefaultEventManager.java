package ru.undframe.icq.bot.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class DefaultEventManager implements EventManager {

    private Map<EventType, List<Event>> eventMap = new HashMap<>();

    @Override
    public void addListener(EventType eventType, Event event) {
        List<Event> list;
        if (eventMap.containsKey(eventType)) {
            list = eventMap.get(eventType);
            list.add(event);
        }
        list = new ArrayList<>();
        list.add(event);
        eventMap.put(eventType, list);
    }

    @Override
    public void removeListener(EventType eventType) {
        eventMap.remove(eventType);
    }

    @Override
    public List<Event> getEvent(EventType eventType) {
        return eventMap.get(eventType);
    }
}
