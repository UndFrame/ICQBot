package ru.undframe.icq.bot.service.commandservice.command;

import java.util.List;

public class CommandArgs implements CommandContext {
    private final Command command;
    private final List<String> raw;
    private final CommandSource source;
    private final StringReader reader;
    private int cursor;

    public CommandArgs(Command command, List<String> raw, CommandSource source, StringReader reader) {
        this.command = command;
        this.raw = raw;
        this.source = source;
        this.reader = reader;
    }

    @Override
    public CommandSource getSource() {
        return source;
    }

    @Override
    public boolean isEmpty() {
        return raw.isEmpty();
    }

    @Override
    public boolean hasNextArg() {
        return cursor < command.getParameters().size();
    }

    @Override
    public <T> T getArg() {
        StringReader reader = this.reader;
        if (reader.canRead(1)) reader.skip(1);
        Parameter<?> parameter = command.getParameters().get(cursor++);
        return (T) parameter.getType().read(source, reader);
    }


}
