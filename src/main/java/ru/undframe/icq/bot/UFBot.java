package ru.undframe.icq.bot;

import ru.mail.im.botapi.BotApiClient;
import ru.mail.im.botapi.BotApiClientController;
import ru.mail.im.botapi.api.entity.EditTextRequest;
import ru.mail.im.botapi.api.entity.SendTextRequest;
import ru.mail.im.botapi.fetcher.Chat;
import ru.undframe.icq.bot.service.commandservice.command.CommandBuilder;
import ru.undframe.icq.bot.service.commandservice.command.CommandDispatcher;
import ru.undframe.icq.bot.service.commandservice.command.CommandService;
import ru.undframe.icq.bot.event.DefaultEventManager;
import ru.undframe.icq.bot.event.EventManager;
import ru.undframe.icq.bot.event.EventType;
import ru.undframe.icq.bot.event.factory.DefaultEventFactory;
import ru.undframe.icq.bot.listeners.CommandListener;
import ru.undframe.icq.bot.listeners.NewMessageListener;

import java.io.IOException;
import java.util.Map;

public class UFBot implements Bot {

    private static BotApiClientController botApiClientController;

    private static Bot bot;

    static Bot getInstance() {
        return bot;
    }

    public static void main(String[] args) throws IOException {
        Map<String, String> getenv = System.getenv();
        if (!getenv.containsKey("TOKEN")) throw new NullPointerException("token not found");
        BotApiClient client = new BotApiClient(getenv.get("TOKEN"));
        bot = new UFBot();
        botApiClientController = BotApiClientController.startBot(client);
        EventManager eventManager = new DefaultEventManager();
        eventManager.addListener(EventType.NEW_MESSAGE, new NewMessageListener());
        eventManager.addListener(EventType.COMMAND, new CommandListener());
        client.addOnEventFetchListener(new DefaultEventFactory(eventManager));
        Bot.getInstance().getCommandDispatcher()
                .register(CommandBuilder.builder().name("help")
                        .visibleFunction(commandSource -> false)
                        .execute(commandContext -> {
                            Chat chat = commandContext.getSource().getChat();
                            chat.getChatId();
                            Bot.getInstance().sendMessage(chat, bot.getCommandDispatcher().getCommandHelp(commandContext.getSource()));
                        })
                        .build());
        Bot.getInstance().getCommandDispatcher()
                .register(CommandBuilder.builder().name("start")
                        .visibleFunction(commandSource -> false)
                        .build());
        Bot.getInstance().getCommandDispatcher()
                .register(CommandBuilder.builder().name("stop")
                        .visibleFunction(commandSource -> false)
                        .build());

    }

    @Override
    public void sendMessage(Chat chat, String text) throws IOException {
        botApiClientController.sendTextMessage(new SendTextRequest()
                .setChatId(chat.getChatId())
                .setText(text)
        );
    }

    @Override
    public void editText(Chat chat, String text) throws IOException {
        botApiClientController.editText(new EditTextRequest()
                .setChatId(chat.getChatId())
                .setNewText(text)
        );
    }

    @Override
    public CommandDispatcher getCommandDispatcher() {
        return commandDispatcher;
    }

    private CommandDispatcher commandDispatcher = new CommandService();

}
