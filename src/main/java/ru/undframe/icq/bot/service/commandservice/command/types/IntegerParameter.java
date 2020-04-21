package ru.undframe.icq.bot.service.commandservice.command.types;

import ru.undframe.icq.bot.service.commandservice.command.CommandSource;
import ru.undframe.icq.bot.service.commandservice.command.StringReader;
import ru.undframe.icq.bot.service.commandservice.command.TypeParameter;
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
