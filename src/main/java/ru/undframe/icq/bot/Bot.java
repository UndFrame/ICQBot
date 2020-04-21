package ru.undframe.icq.bot;

import ru.mail.im.botapi.fetcher.Chat;
import ru.undframe.icq.bot.command.CommandDispatcher;

import java.io.IOException;

public interface Bot {

    void sendMessage(Chat chat,String text) throws IOException;
    CommandDispatcher getCommandDispatcher();
    static Bot getInstance() {
        return UFBot.getInstance();
    }
}
