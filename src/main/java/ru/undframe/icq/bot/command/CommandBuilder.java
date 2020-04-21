package ru.undframe.icq.bot.command;

import java.util.ArrayList;
import java.util.List;

public class CommandBuilder {

    private String name = "";
    private String lore = "";
    private List<Parameter> parameters = new ArrayList<>();
    private CommandExecute executeConsumer;
    private CommandException exceptionallyConsumer;


    public static CommandBuilder builder(){
        return new CommandBuilder();
    }

    public Command build(){
        return new DefaultCommand(name,lore,parameters,executeConsumer == null?(__)-> {} :executeConsumer,exceptionallyConsumer == null?(__,___)->{}:exceptionallyConsumer);
    }


    public CommandBuilder name(String name){
        this.name = name;
        return this;
    }

    public CommandBuilder lore(String lore){
        this.lore = lore;
        return this;
    }


    public CommandBuilder execute(CommandExecute executeConsumer){
        this.executeConsumer = executeConsumer;
        return this;
    }

    public CommandBuilder exceptionally(CommandException exceptionallyConsumer){
        this.exceptionallyConsumer = exceptionallyConsumer;
        return this;
    }


    public CommandBuilder parameter(Parameter parameter){
        this.parameters.add(parameter);
        return this;
    }

}
