package ru.undframe.icq.bot.service.commandservice.command;

import java.util.List;

public interface Command {

    String getName();
    String getLore();
    List<Parameter> getParameters();
    void execute(CommandContext context);
    boolean visible(CommandSource commandSource);

}
