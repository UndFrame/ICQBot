package ru.undframe.icq.bot.command;

import ru.mail.im.botapi.fetcher.Chat;

public interface CommandSource {
    Chat getChat();
}
