package ru.undframe.icq.bot;

import com.google.gson.Gson;
import org.json.JSONObject;
import ru.mail.im.botapi.BotApiClient;
import ru.mail.im.botapi.BotApiClientController;
import ru.mail.im.botapi.api.entity.EditTextRequest;
import ru.mail.im.botapi.api.entity.SendTextRequest;
import ru.mail.im.botapi.fetcher.Chat;
import ru.undframe.icq.bot.event.DefaultEventManager;
import ru.undframe.icq.bot.event.EventManager;
import ru.undframe.icq.bot.event.EventType;
import ru.undframe.icq.bot.event.factory.DefaultEventFactory;
import ru.undframe.icq.bot.listeners.CommandListener;
import ru.undframe.icq.bot.listeners.NewMessageListener;
import ru.undframe.icq.bot.service.commandservice.command.CommandBuilder;
import ru.undframe.icq.bot.service.commandservice.command.CommandDispatcher;
import ru.undframe.icq.bot.service.commandservice.command.CommandService;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

public class UFBot implements Bot {

    private static final String PATH_TO_PROPERTIES = "bot.properties";
    private static BotApiClientController botApiClientController;

    private static Bot bot;

    static Bot getInstance() {
        return bot;
    }

    public static void main(String[] args) {
        Properties prop = new Properties();

        try {
            ClassLoader classLoader = UFBot.class.getClassLoader();
            File configFile=new File(Objects.requireNonNull(classLoader.getResource(PATH_TO_PROPERTIES)).getFile());
            FileInputStream fileInputStream = new FileInputStream(configFile);
            prop.load(fileInputStream);
        } catch (IOException e) {
            System.out.println("Ошибка в программе: файл " + PATH_TO_PROPERTIES + " не обнаружено");
            e.printStackTrace();
        }

        BotApiClient client = new BotApiClient(prop.getProperty("TOKEN"));
        bot = new UFBot();
        botApiClientController = BotApiClientController.startBot(client);
        EventManager eventManager = new DefaultEventManager();
        eventManager.addListener(EventType.NEW_MESSAGE, new NewMessageListener());
        eventManager.addListener(EventType.COMMAND, new CommandListener());
        client.addOnEventFetchListener(new DefaultEventFactory(eventManager));
        Bot.getInstance().registerService(CommandDispatcher.class, new CommandService());
        CommandDispatcher.get()
                .register(CommandBuilder.builder().name("help")
                        .visibleFunction(commandSource -> false)
                        .execute(commandContext -> {
                            Chat chat = commandContext.getSource().getChat();
                            chat.getChatId();
                            String commandHelp = CommandDispatcher.get().getCommandHelp(commandContext.getSource());
                            Bot.getInstance().sendMessage(chat, commandHelp);
                        })
                        .exceptionally((commandContext, e) -> {
                            e.printStackTrace();
                        })
                        .build());
        CommandDispatcher.get()
                .register(CommandBuilder.builder().name("start")
                        .visibleFunction(commandSource -> false)
                        .build());
        CommandDispatcher.get()
                .register(CommandBuilder.builder().name("stop")
                        .visibleFunction(commandSource -> false)
                        .build());
/*
https://coronavirus-tracker-api.herokuapp.com/v2/locations/187
 */
        CommandDispatcher.get()
                .register(CommandBuilder.builder().name("weather")
                        .lore("Узнать погоду в городе Ижевск")
                        .execute(commandContext -> {
                            Bot.getInstance().sendMessage(commandContext.getSource().getChat(),"Отправка запроса на openweathermap.org...");
                            URL url = new URL("https://api.openweathermap.org/data/2.5/weather?id=554840&appid=" + prop.getProperty("WEATHER_TOKEN"));
                            URLConnection urlConnection = url.openConnection();
                            BufferedReader in = new BufferedReader(
                                    new InputStreamReader(
                                            urlConnection.getInputStream()));
                            StringBuilder response =new StringBuilder();

                            String inputLine;
                            while ((inputLine = in.readLine()) != null)
                                response.append(inputLine);
                            in.close();
                            JSONObject json = new JSONObject(response.toString());
                            Bot.getInstance().sendMessage(commandContext.getSource().getChat(),"Погода в Ижевске: "+(json.getJSONObject("main").getInt("temp")-273));
                        })
                        .exceptionally((commandContext, e) -> {
                            Bot.getInstance().sendMessage(commandContext.getSource().getChat(),"Произошла ошибка");
                            e.printStackTrace();
                        })
                        .visibleFunction(commandSource -> true)
                        .build());
        CommandDispatcher.get()
                .register(CommandBuilder.builder().name("coronavirus")
                        .lore("Узнать информацию по коронавирусу в России")
                        .execute(commandContext -> {
                            Bot.getInstance().sendMessage(commandContext.getSource().getChat(),"Отправка запроса...");
                            URL url = new URL("https://coronavirus-tracker-api.herokuapp.com/v2/locations/187");
                            URLConnection urlConnection = url.openConnection();
                            BufferedReader in = new BufferedReader(
                                    new InputStreamReader(
                                            urlConnection.getInputStream()));
                            StringBuilder response =new StringBuilder();

                            String inputLine;
                            while ((inputLine = in.readLine()) != null)
                                response.append(inputLine);
                            in.close();
                            JSONObject json = new JSONObject(response.toString());
                            Bot.getInstance().sendMessage(commandContext.getSource().getChat(),"В России заболело: "+(json.getJSONObject("location").getJSONObject("latest").getInt("confirmed")));
                        })
                        .exceptionally((commandContext, e) -> {
                            Bot.getInstance().sendMessage(commandContext.getSource().getChat(),"Произошла ошибка");
                            e.printStackTrace();
                        })
                        .visibleFunction(commandSource -> true)
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
    public void editText(Chat chat, long messageId, String text) throws IOException {
        botApiClientController.editText(new EditTextRequest()
                .setChatId(chat.getChatId())
                .setMsgId(messageId)
                .setNewText(text)
        );
    }

    private final Map<Class<? extends Service>, Service> serviceMap = new HashMap<>();

    @Override
    public <T extends Service> void registerService(Class<T> clazz, T service) {
        Service oldService = serviceMap.put(clazz, service);
        if (oldService != null) {
            oldService.disable();
        }
        service.enable();
    }

    @Override
    public <T extends Service> void unregisterService(Class<T> clazz) {
        T service = getService(clazz);
        if (service != null)
            service.disable();
        serviceMap.remove(clazz);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Service> T getService(Class<T> clazz) {
        return (T) serviceMap.get(clazz);
    }
}
