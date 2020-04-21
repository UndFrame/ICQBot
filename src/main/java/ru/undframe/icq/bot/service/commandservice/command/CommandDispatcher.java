package ru.undframe.icq.bot.service.commandservice.command;

import ru.undframe.icq.bot.Bot;

public interface CommandDispatcher extends ru.undframe.icq.bot.Service{

    static CommandDispatcher get(){
        return Bot.getInstance().getService(CommandDispatcher.class);
    }

    void register(Command command);

    Command getCommand(String name);
    String getCommandHelp(CommandSource commandSource);
}
