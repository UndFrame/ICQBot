package ru.undframe.icq.bot.service.commandservice.command;

public interface CommandDispatcher extends ru.undframe.icq.bot.Service{

    void register(Command command);

    Command getCommand(String name);
    String getCommandHelp(CommandSource commandSource);
}
