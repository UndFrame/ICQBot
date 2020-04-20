package ru.undframe.icq.bot;

import ru.mail.im.botapi.BotApiClient;
import ru.mail.im.botapi.BotApiClientController;
import ru.mail.im.botapi.api.entity.SendTextRequest;
import ru.mail.im.botapi.fetcher.Chat;
import ru.undframe.icq.bot.event.DefaultEventManager;
import ru.undframe.icq.bot.event.EventManager;
import ru.undframe.icq.bot.event.EventType;
import ru.undframe.icq.bot.event.factory.DefaultEventFactory;
import ru.undframe.icq.bot.listeners.NewMessageListener;

import java.io.IOException;
import java.util.Map;

public class UFBot implements Bot{

    private static BotApiClientController botApiClientController;

    private static Bot bot;

    public static Bot getInstance(){
        return bot;
    }

    public static void main(String[] args) throws IOException {
        Map<String, String> getenv = System.getenv();
        if (!getenv.containsKey("TOKEN")) throw new NullPointerException("token not found");
        BotApiClient client = new BotApiClient(getenv.get("TOKEN"));
        bot = new UFBot();
        botApiClientController = BotApiClientController.startBot(client);
        EventManager eventManager = new DefaultEventManager();
        eventManager.addListener(EventType.NEW_MESSAGE,new NewMessageListener());
        client.addOnEventFetchListener(new DefaultEventFactory(eventManager));
    }

    @Override
    public void sendMessage(Chat chat, String text) throws IOException {
        botApiClientController.sendTextMessage(new SendTextRequest()
                .setChatId(chat.getChatId())
                .setText(text)
        );
    }
}
