package ru.undframe.icq.bot.listeners;

import ru.mail.im.botapi.fetcher.event.NewMessageEvent;
import ru.undframe.icq.bot.UFBot;
import ru.undframe.icq.bot.command.Command;
import ru.undframe.icq.bot.command.CommandArgs;
import ru.undframe.icq.bot.event.Event;
import ru.undframe.icq.bot.exceptions.WrongArguments;

import java.io.IOException;
import java.util.Arrays;

public class CommandListener implements Event {
    @Override
    public void handle(Object o) throws IOException {
        if (!(o instanceof NewMessageEvent))
            throw new WrongArguments(null);
        NewMessageEvent newMessageEvent = (NewMessageEvent) o;
        String text = newMessageEvent.getText();
        String substring = text.substring(1);
        String[] args = substring.split(" ");
        String[] newArgs = new String[args.length - 1];
        for (int i = 1; i < args.length; i++) {
            newArgs[i - 1] = args[i];
        }
        Command command = UFBot.getInstance().getCommand(args[0]);
        try {
            command.execute(new CommandArgs(newMessageEvent.getChat(), Arrays.asList(newArgs), command));
        }catch (WrongArguments wrongArguments){
            command.exceptionally(wrongArguments);
        }
    }
}
