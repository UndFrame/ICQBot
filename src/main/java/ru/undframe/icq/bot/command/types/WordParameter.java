package ru.undframe.icq.bot.command.types;

import ru.undframe.icq.bot.command.AbstractType;
import ru.undframe.icq.bot.command.CommandSource;
import ru.undframe.icq.bot.command.StringReader;
import ru.undframe.icq.bot.exceptions.CommandParseException;

public class WordParameter extends AbstractType<String> {
    @Override
    public String read(CommandSource source, StringReader reader) throws CommandParseException {
        return reader.readUnquotedString(Short.MAX_VALUE);
    }
}
