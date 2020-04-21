package ru.undframe.icq.bot.command;

import java.util.UUID;

public interface CommandContext {

    CommandSource getSource();

    boolean isEmpty();

    boolean hasNextArg();

    <T> T getArg();

    default <T> T getArg(Class<T> __) {
        return getArg();
    }


}
