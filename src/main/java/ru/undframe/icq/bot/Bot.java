package ru.undframe.icq.bot;

import ru.mail.im.botapi.fetcher.Chat;

import java.io.IOException;

public interface Bot {

    void sendMessage(Chat chat,String text) throws IOException;

    static Bot getInstance() {
        return UFBot.getInstance();
    }
}
