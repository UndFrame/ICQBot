package ru.undframe.icq.bot.event.factory;

import ru.undframe.icq.bot.event.Event;

import java.util.List;

public interface EventFactory {

    List<Event> getEvent(ru.mail.im.botapi.fetcher.event.Event event);

}
