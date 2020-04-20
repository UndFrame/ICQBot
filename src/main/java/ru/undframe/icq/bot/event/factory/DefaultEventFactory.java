package ru.undframe.icq.bot.event.factory;


import ru.mail.im.botapi.fetcher.OnEventFetchListener;
import ru.mail.im.botapi.fetcher.event.NewMessageEvent;
import ru.undframe.icq.bot.event.Event;
import ru.undframe.icq.bot.event.EventManager;
import ru.undframe.icq.bot.event.EventType;

import java.io.IOException;
import java.util.List;

public class DefaultEventFactory implements EventFactory, OnEventFetchListener {

    private EventManager defaultEventManager;


    public DefaultEventFactory(EventManager defaultEventManager) {
        this.defaultEventManager = defaultEventManager;
    }

    @Override
    public Event getEvent(ru.mail.im.botapi.fetcher.event.Event event) {
        if (event instanceof NewMessageEvent) {
            return defaultEventManager.getEvent(EventType.NEW_MESSAGE);
        }
        return null;
    }

    @Override
    public void onEventFetch(List<ru.mail.im.botapi.fetcher.event.Event> events) {
        try {
            for (ru.mail.im.botapi.fetcher.event.Event event : events) {
                getEvent(event).handle(event);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
