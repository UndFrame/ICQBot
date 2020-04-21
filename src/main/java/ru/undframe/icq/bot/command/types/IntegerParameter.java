package ru.undframe.icq.bot.command.types;

import ru.undframe.icq.bot.command.CommandSource;
import ru.undframe.icq.bot.command.StringReader;
import ru.undframe.icq.bot.command.TypeParameter;
import ru.undframe.icq.bot.exceptions.CommandParseException;

public class IntegerParameter implements TypeParameter<Integer> {
    @Override
    public String getName() {
        return null;
    }

    @Override
    public Integer read(CommandSource source, StringReader reader) throws CommandParseException {
        return reader.readInt(10);
    }

}
