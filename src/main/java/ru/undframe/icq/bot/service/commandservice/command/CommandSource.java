package ru.undframe.icq.bot.service.commandservice.command;

import ru.mail.im.botapi.fetcher.Chat;

public interface CommandSource {
    Chat getChat();
}
