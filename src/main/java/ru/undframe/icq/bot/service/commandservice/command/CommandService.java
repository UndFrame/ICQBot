package ru.undframe.icq.bot.service.commandservice.command;

import java.util.HashMap;
import java.util.Map;

public class CommandService implements CommandDispatcher {

    private Map<String,Command> commandMap = new HashMap<>();

    @Override
    public void register(Command command) {
        commandMap.put(command.getName(),command);
    }

    @Override
    public Command getCommand(String name) {
        return commandMap.get(name);
    }

    @Override
    public String getCommandHelp(CommandSource commandSource) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Command command : commandMap.values()) {
            if(!command.visible(commandSource)) continue;
            stringBuilder.append("/");
            stringBuilder.append(command.getName());
            for (Parameter parameter : command.getParameters()) {
                stringBuilder.append(" ");
                stringBuilder.append(parameter.getName());
            }
            stringBuilder.append(" - ");
            stringBuilder.append(command.getLore());
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
