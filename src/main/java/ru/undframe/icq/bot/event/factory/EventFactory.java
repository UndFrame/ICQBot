package ru.undframe.icq.bot.event.factory;

import ru.undframe.icq.bot.event.Event;

public interface EventFactory {

    Event getEvent(ru.mail.im.botapi.fetcher.event.Event event);

}
