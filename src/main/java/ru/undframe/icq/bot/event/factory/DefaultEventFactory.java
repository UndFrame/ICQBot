package ru.undframe.icq.bot.event.factory;


import ru.mail.im.botapi.fetcher.OnEventFetchListener;
import ru.mail.im.botapi.fetcher.event.NewMessageEvent;
import ru.undframe.icq.bot.event.Event;
import ru.undframe.icq.bot.event.EventManager;
import ru.undframe.icq.bot.event.EventType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DefaultEventFactory implements EventFactory, OnEventFetchListener {

    private EventManager defaultEventManager;


    public DefaultEventFactory(EventManager defaultEventManager) {
        this.defaultEventManager = defaultEventManager;
    }

    @Override
    public List<Event> getEvent(ru.mail.im.botapi.fetcher.event.Event event) {
        if (event instanceof NewMessageEvent) {
            NewMessageEvent newMessageEvent = (NewMessageEvent) event;
            if (newMessageEvent.getText().startsWith("/"))
                return defaultEventManager.getEvent(EventType.COMMAND);

            return defaultEventManager.getEvent(EventType.NEW_MESSAGE);
        }
        return new ArrayList<>();
    }

    @Override
    public void onEventFetch(List<ru.mail.im.botapi.fetcher.event.Event> events) {
        try {
            for (ru.mail.im.botapi.fetcher.event.Event event : events)
                for (Event ev : getEvent(event))
                    ev.handle(event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
