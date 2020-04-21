package ru.undframe.icq.bot.command;

public interface CommandDispatcher {

    void register(Command command);

    Command getCommand(String name);
}
