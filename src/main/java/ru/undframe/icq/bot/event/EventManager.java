package ru.undframe.icq.bot.event;

import java.util.List;

public interface EventManager {

    void addListener(EventType eventType,Event event);
    void removeListener(EventType eventType);

    List<Event> getEvent(EventType eventType);

}
