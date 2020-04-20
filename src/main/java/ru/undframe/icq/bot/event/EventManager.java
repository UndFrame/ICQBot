package ru.undframe.icq.bot.event;

public interface EventManager {

    void addListener(EventType eventType,Event event);
    void removeListener(EventType eventType);

    Event getEvent(EventType eventType);

}
