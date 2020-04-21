package ru.undframe.icq.bot.command;

import java.util.HashMap;
import java.util.Map;

public class DefaultCommandDispatcher implements CommandDispatcher {

    private Map<String,Command> commandMap = new HashMap<>();

    @Override
    public void register(Command command) {
        commandMap.put(command.getName(),command);
    }

    @Override
    public Command getCommand(String name) {
        return commandMap.get(name);
    }
}
