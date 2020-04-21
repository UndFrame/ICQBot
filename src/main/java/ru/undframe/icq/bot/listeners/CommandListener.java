package ru.undframe.icq.bot.listeners;

import ru.mail.im.botapi.fetcher.event.NewMessageEvent;
import ru.undframe.icq.bot.Bot;
import ru.undframe.icq.bot.command.Command;
import ru.undframe.icq.bot.command.CommandArgs;
import ru.undframe.icq.bot.command.DefaultCommandSource;
import ru.undframe.icq.bot.command.StringReader;
import ru.undframe.icq.bot.event.Event;
import ru.undframe.icq.bot.exceptions.WrongArguments;

import java.io.IOException;
import java.util.Arrays;

public class CommandListener implements Event {
    @Override
    public void handle(Object o){
        if (!(o instanceof NewMessageEvent))
            throw new WrongArguments(null);
        NewMessageEvent newMessageEvent = (NewMessageEvent) o;
        String text = newMessageEvent.getText();
        String substring = text.substring(1);
        String[] args = substring.split(" ");
        String[] newArgs =args.length<=1? new String[0] : new String[args.length - 1];
        System.arraycopy(args, 1, newArgs, 0, args.length - 1);
        Command command = Bot.getInstance().getCommandDispatcher().getCommand(args[0]);
        command.execute(new CommandArgs(command, Arrays.asList(newArgs), new DefaultCommandSource(newMessageEvent.getChat()), new StringReader(' ' + String.join(" ", newArgs))));
    }
}
