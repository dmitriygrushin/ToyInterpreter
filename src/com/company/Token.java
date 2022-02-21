package com.company;

public class Token {
    private final String type;
    private final char value;

    public Token(String type, char value) {
        this.type = type;
        this.value = value;
    }

    public char getValue() {
        return value;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Token{" +
                "type='" + type + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
