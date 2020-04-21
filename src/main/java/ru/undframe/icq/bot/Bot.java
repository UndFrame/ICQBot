package ru.undframe.icq.bot;

import ru.mail.im.botapi.fetcher.Chat;
import java.io.IOException;

public interface Bot {

    void sendMessage(Chat chat,String text) throws IOException;
    void editText(Chat chat,long messageId, String text) throws IOException;
    static Bot getInstance() {
        return UFBot.getInstance();
    }

    <T extends Service> void registerService(Class<T> clazz, T service);
    <T extends Service> void unregisterService(Class<T> clazz);
    <T extends Service> T getService(Class<T> clazz);

}
