package ru.undframe.icq.bot.service.commandservice.command;

import ru.mail.im.botapi.fetcher.Chat;

public class DefaultCommandSource implements CommandSource {
    private final Chat chat;
    private final long messageId;

    public DefaultCommandSource(Chat chat, long messageId) {
        this.chat = chat;
        this.messageId = messageId;
    }

    @Override
    public Chat getChat() {
        return chat;
    }

    @Override
    public long messageId() {
        return messageId;
    }
}
