package ru.undframe.icq.bot.service.commandservice.command;

public interface CommandException {

    void exceptionally(CommandContext commandContext,Exception e) throws Exception;

}
