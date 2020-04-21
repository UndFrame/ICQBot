package ru.undframe.icq.bot.command;

import ru.mail.im.botapi.fetcher.Chat;

public class DefaultCommandSource implements CommandSource {
    private final Chat chat;

    public DefaultCommandSource(Chat chat) {
        this.chat = chat;
    }

    @Override
    public Chat getChat() {
        return chat;
    }
}
