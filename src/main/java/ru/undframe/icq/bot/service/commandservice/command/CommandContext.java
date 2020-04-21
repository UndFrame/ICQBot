package ru.undframe.icq.bot.service.commandservice.command;

public interface CommandContext {

    CommandSource getSource();

    boolean isEmpty();

    boolean hasNextArg();

    <T> T getArg();

    default <T> T getArg(Class<T> __) {
        return getArg();
    }


}
