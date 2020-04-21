package ru.undframe.icq.bot.command;

import java.util.List;

class DefaultCommand implements Command {

    private String name;
    private String lore;
    private List<Parameter> parameters;
    private CommandExecute executeConsumer;
    private CommandException exceptionallyConsumer;


    DefaultCommand(String name, String lore, List<Parameter> parameters, CommandExecute executeConsumer,CommandException exceptionallyConsumer) {
        this.name = name;
        this.lore = lore;
        this.parameters = parameters;
        this.executeConsumer = executeConsumer;
        this.exceptionallyConsumer = exceptionallyConsumer;
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
        }catch (Exception e){
            try {
                exceptionallyConsumer.exceptionally(context,e);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
