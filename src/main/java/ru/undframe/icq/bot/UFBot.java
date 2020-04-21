package ru.undframe.icq.bot;

import ru.mail.im.botapi.BotApiClient;
import ru.mail.im.botapi.BotApiClientController;
import ru.mail.im.botapi.api.entity.SendTextRequest;
import ru.mail.im.botapi.fetcher.Chat;
import ru.undframe.icq.bot.command.CommandBuilder;
import ru.undframe.icq.bot.command.CommandDispatcher;
import ru.undframe.icq.bot.command.DefaultCommandDispatcher;
import ru.undframe.icq.bot.command.Parameter;
import ru.undframe.icq.bot.command.types.IntegerParameter;
import ru.undframe.icq.bot.command.types.WordParameter;
import ru.undframe.icq.bot.event.DefaultEventManager;
import ru.undframe.icq.bot.event.EventManager;
import ru.undframe.icq.bot.event.EventType;
import ru.undframe.icq.bot.event.factory.DefaultEventFactory;
import ru.undframe.icq.bot.exceptions.CommandParseException;
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
                .register(CommandBuilder.builder().name("test")
                        .lore("command for test")
                        .parameter(new Parameter<>("sub command", new WordParameter()))
                        .parameter(new Parameter<>("integer2", new IntegerParameter()))
                        .execute(commandContext -> {
                            String value = commandContext.getArg(String.class);
                            Integer value2 = commandContext.getArg(Integer.class);
                            Bot.getInstance().sendMessage(commandContext.getSource().getChat(), value + " " + value2);
                        }).exceptionally((s, e) -> {
                            if (e instanceof CommandParseException
                            || e instanceof NumberFormatException) {
                                Bot.getInstance().sendMessage(s.getSource().getChat(), "Произошла ошибка при формировании аргуметов ");
                            }else
                            Bot.getInstance().sendMessage(s.getSource().getChat(), "Произошла ошибка: " + e.getMessage());
                        }).build());

        Bot.getInstance().getCommandDispatcher()
                .register(CommandBuilder.builder().name("help")
                        .build());

        Bot.getInstance().getCommandDispatcher()
                .register(CommandBuilder.builder().name("start")
                        .build());
        Bot.getInstance().getCommandDispatcher()
                .register(CommandBuilder.builder().name("stop")
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
    public CommandDispatcher getCommandDispatcher() {
        return commandDispatcher;
    }

    private CommandDispatcher commandDispatcher = new DefaultCommandDispatcher();

}
