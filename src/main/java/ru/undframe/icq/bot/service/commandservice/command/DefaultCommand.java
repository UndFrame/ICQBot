package ru.undframe.icq.bot.service.commandservice.command;

import java.util.List;
import java.util.function.Function;

class DefaultCommand implements Command {

    private String name;
    private String lore;
    private List<Parameter> parameters;
    private CommandExecute executeConsumer;
    private CommandException exceptionallyConsumer;
    private Function<CommandSource, Boolean> visible;

    DefaultCommand(String name, String lore, List<Parameter> parameters, CommandExecute executeConsumer, CommandException exceptionallyConsumer, Function<CommandSource, Boolean> visible) {
        this.name = name;
        this.lore = lore;
        this.parameters = parameters;
        this.executeConsumer = executeConsumer;
        this.exceptionallyConsumer = exceptionallyConsumer;
        this.visible = visible;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getLore() {
        return lore;
    }

    @Override
    public List<Parameter> getParameters() {
        return parameters;
    }

    @Override
    public void execute(CommandContext context) {
        try {
            executeConsumer.execute(context);
        } catch (Exception e) {
            try {
                exceptionallyConsumer.exceptionally(context, e);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public boolean visible(CommandSource commandSource) {
        return visible.apply(commandSource);
    }
}
