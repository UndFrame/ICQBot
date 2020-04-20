package ru.undframe.icq.bot.exceptions;

import ru.mail.im.botapi.fetcher.Chat;

public class WrongArguments extends RuntimeException {

    private Chat chat;

    public WrongArguments(Chat chat) {
        this.chat = chat;
    }

    public Chat getSource() {
        return chat;
    }
}
