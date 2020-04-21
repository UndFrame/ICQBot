package ru.undframe.icq.bot.command;

import ru.undframe.icq.bot.exceptions.CommandParseException;

public class StringReader {

    private final String input;
    private final char[] array;

    private int cursor;

    public StringReader(String input) {
        this.input = input;
        this.array = input.toCharArray();
    }

    public int getCursor() {
        return cursor;
    }

    public void setCursor(int cursor) {
        this.cursor = cursor;
    }

    public char peek(int offset) {
        return array[cursor + offset];
    }

    public void skip(int length) {
        cursor += length;
    }

    public boolean canRead(int length) {
        return cursor + length <= array.length;
    }

    public char poll() {
        int cursor = this.cursor++;
        if (cursor > array.length) {
            throw new IllegalStateException("Out of range! (" + cursor + " > " + array.length + ")");
        }
        return array[cursor];
    }

    public boolean isEmpty() {
        return array.length - cursor == 0;
    }

    public int getRemainingLength() {
        return array.length - cursor;
    }

    public int getLength() {
        return array.length;
    }

    public String between(int start, int end) {
        return input.substring(start, end);
    }

    public String getRead() {
        return input.substring(0, cursor);
    }

    public String getRemaining() {
        return input.substring(cursor);
    }

    public String readStringUntilChar(int limit, char terminate) {
        StringBuilder builder = new StringBuilder();
        int read = 0;
        boolean escape = false;
        while (canRead(1)) {
            char next = poll();
            if (escape) {
                if (next == '\\' || next == terminate) {
                    rangeCheck(read++, limit);
                    builder.append(next);
                    escape = false;
                } else {
                    cursor--;
                    throw new CommandParseException("Invalid escape sequence: " + next);
                }
            } else if (next == '\\') {
                escape = true;
            } else if (next == terminate) {
                return builder.toString();
            } else {
                rangeCheck(read++, limit);
                builder.append(next);
            }
        }
        throw new CommandParseException("Unexpected end of quote");
    }

    public String readQuotedString(short limit) {
        if (!canRead(1)) {
            throw new CommandParseException("Строка пуста!");
        }
        char c = peek(0);
        if (c != '\'' && c != '\"') {
            throw new CommandParseException("Invalid quoted string start: " + c);
        }
        skip(1);
        return readStringUntilChar(limit, c);
    }

    public String readQuotedString(int limit) {
        return readQuotedString((short) limit);
    }

    public String readUnquotedString(short limit) {
        int start = cursor;
        int read = 0;
        while (canRead(1) && canAcceptCharacter(peek(0))) {
            rangeCheck(read++, limit);
            skip(1);
        }
        return input.substring(start, cursor);
    }

    public String readUnquotedString(int limit) {
        return readUnquotedString((short) limit);
    }

    public String readString(short limit) {
        if (!canRead(1)) {
            throw new CommandParseException("Строка пуста!");
        }
        char peek = peek(0);
        if (peek == '\'' || peek == '\"') {
            skip(1);
            return readStringUntilChar(limit, peek);
        }
        return readUnquotedString(limit);
    }

    public int readInt(int radix) {
        int start = cursor;
        while (canRead(1) && isNumber(peek(0))) {
            skip(1);
        }
        String input = this.input.substring(start, cursor);
        if (input.isEmpty()) {
            throw new NumberFormatException(input);
        }
        return Integer.parseInt(input, radix);
    }

    public short readShort(int radix) {
        int start = cursor;
        while (canRead(1) && isNumber(peek(0))) {
            skip(1);
        }
        String input = this.input.substring(start, cursor);
        if (input.isEmpty()) {
            throw new NumberFormatException(input);
        }
        return Short.parseShort(input, radix);
    }

    public long readLong(int radix) {
        int start = cursor;
        while (canRead(1) && isNumber(peek(0))) {
            skip(1);
        }
        String input = this.input.substring(start, cursor);
        if (input.isEmpty()) {
            throw new NumberFormatException(input);
        }
        return Long.parseLong(input, radix);
    }

    public byte readByte(int radix) {
        // -128 .... 127
        int start = cursor;
        for (int i = 0; i < 4 && canRead(1) && isNumber(peek(0)); i++) {
            skip(1);
        }
        String input = this.input.substring(start, cursor);
        if (input.isEmpty()) {
            throw new NumberFormatException(input);
        }
        return Byte.parseByte(input, radix);
    }

    public float readFloat() {
        int start = cursor;
        while (canRead(1) && isNumber(peek(0))) {
            skip(1);
        }
        String input = this.input.substring(start, cursor);
        if (input.isEmpty()) {
            throw new NumberFormatException(input);
        }
        return Float.parseFloat(input.replace(',', '.'));
    }

    public double readDouble() {
        int start = cursor;
        while (canRead(1) && isNumber(peek(0))) {
            skip(1);
        }
        String input = this.input.substring(start, cursor);
        if (input.isEmpty()) {
            throw new NumberFormatException(input);
        }
        return Double.parseDouble(input.replace(',', '.'));
    }

    public boolean readBoolean() {
        if (canRead(5)) { // false
            String input = between(cursor, cursor + 5);
            if (!"false".equals(input)) {
                throw unexpectedBooleanInput(input);
            }
            skip(5);
            return false;
        } else if (canRead(4)) { // true
            String input = between(cursor, cursor + 4);
            if (!"true".equals(input)) {
                throw unexpectedBooleanInput(input);
            }
            skip(4);
            return true;
        }
        throw new CommandParseException("Неизвестное логическое значение!");
    }

    private static CommandParseException unexpectedBooleanInput(String input) {
        return new CommandParseException("Неизвестное логическое значение: " + input);
    }

    private static void rangeCheck(int read, int limit) {
        if (read > limit) {
            throw new CommandParseException("Достигнут лимит символов для чтения: (" + read + " >  " + limit + ")");
        }
    }

    private static boolean canAcceptCharacter(char c) {
        return Character.isAlphabetic(c) || isNumber(c) || c == '_' || c == '+' || c == '@' || c == '/';
    }

    private static boolean isNumber(char c) {
        return (c >= '0' && c <= '9') || c == '.' || c == '-' || c == ',';
    }

}
