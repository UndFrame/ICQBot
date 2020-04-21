package ru.undframe.icq.bot.service.commandservice.command;

public final class Parameter<T extends TypeParameter<?>>{

    private final String name;
    private final T type;

    public Parameter(String name, T type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public T getType() {
        return type;
    }
}
