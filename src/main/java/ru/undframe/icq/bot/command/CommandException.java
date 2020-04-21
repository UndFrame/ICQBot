package ru.undframe.icq.bot.command;

public interface CommandException {

    void exceptionally(CommandContext commandContext,Exception e) throws Exception;

}
