package ru.undframe.icq.bot.command;

import java.util.List;

public interface Command {


    String getName();

    String getLore();

    List<Parameter> getParameters();

    void execute(CommandContext context);

}
