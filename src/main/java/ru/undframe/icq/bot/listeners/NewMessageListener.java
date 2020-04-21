package ru.undframe.icq.bot.listeners;

import ru.mail.im.botapi.fetcher.event.NewMessageEvent;
import ru.undframe.icq.bot.Bot;
import ru.undframe.icq.bot.event.Event;

import java.io.IOException;

public class NewMessageListener implements Event {
    @Override
    public void handle(Object o) throws IOException {
        NewMessageEvent newMessageEvent = (NewMessageEvent)o;
        Bot.getInstance().sendMessage(newMessageEvent.getChat(),"How are you?");
    }
}
