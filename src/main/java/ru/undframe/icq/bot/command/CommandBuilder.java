package ru.undframe.icq.bot.command;

public class CommandBuilder {

    private String name;
    private String lore;

    public static CommandBuilder builder(){
        return new CommandBuilder();
    }

    public Command build(){
        //TODO add command
        return null;
    }


    public CommandBuilder name(String name){
        this.name = name;
        return this;
    }

    public CommandBuilder lore(String lore){
        this.lore = lore;
        return this;
    }

}
