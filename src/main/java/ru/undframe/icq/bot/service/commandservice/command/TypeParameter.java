package ru.undframe.icq.bot.service.commandservice.command;

import ru.undframe.icq.bot.exceptions.CommandParseException;


public interface TypeParameter<T> {

    String getName();
    T read(CommandSource source, StringReader reader) throws CommandParseException;


}
