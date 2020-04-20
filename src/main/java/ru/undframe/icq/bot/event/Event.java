package ru.undframe.icq.bot.event;

import java.io.IOException;

public interface Event {

    void handle(Object o) throws IOException;

}
