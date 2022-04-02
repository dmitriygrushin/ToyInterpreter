package com.company;

import com.company.Token;
import com.company.TokenType;

public class Lexer {
    String text; // String of text being Tokenized
    int pos; // Position of the lexer in the text String
    char currentCharacter; // current character the Tokenizer is up to

    public Lexer(String text) {
        this.text = text;
        this.pos = 0;
        currentCharacter = text.charAt(pos);
    }

    /* Throw error if none of the valid characters match the current character */
    public void error() throws Exception {
        throw new Exception("Invalid Token");
    }

    /* Advance the 'pos' pointer and the currentChar */
    public void advance() {
        this.pos += 1;
        if (this.pos > this.text.length() - 1) {
            this.currentCharacter = '\0';
        } else {
            this.currentCharacter = this.text.charAt(pos);
        }
    }

    /*  peek for assignment statement (likely won't need this for my interpreter since assignment is '=' not ':=')
        @returns \0 or character at peekPos */
    public char peek() {
        int peekPos = this.pos - 1;

        if (peekPos > this.text.length() - 1) {
            return '\0';
        } else {
            return this.text.charAt(peekPos);
        }
    }

    /* skip whitespace characters */
    public void skipWhitespace() {
        while (this.currentCharacter != '\0' && Character.isSpaceChar(this.currentCharacter)) {
            this.advance();
        }
    }

    /*  Concatenates integer characters that's consumed from the input into a string
        parses String to Integer then
        @returns that Integer */
    public int integer() {
        StringBuilder result = new StringBuilder();

        while (this.currentCharacter != '\0' && Character.isDigit(this.currentCharacter)) {
            result.append(this.currentCharacter);
            this.advance();
        }
       return Integer.parseInt(String.valueOf(result));
    }

    /*  Handles identifiers: i.e. variables
        Concatenates characters that's consumed from the input into a string
        @returns  Token(type ID, result) */
    public Token id () {
        StringBuilder result = new StringBuilder();

        while (this.currentCharacter != '\0' && (Character.isLetterOrDigit(this.currentCharacter)) || this.currentCharacter == '_') {
            result.append(this.currentCharacter);
            this.advance();
        }

        return new Token(TokenType.ID.name(), result.toString());
    }

    /* Actual Lexer: Uses methods above to break down Strings of text into tokens
        @returns Token */
    public Token getNextToken() throws Exception {
        while (this.currentCharacter != '\0') {
            if (Character.isSpaceChar(this.currentCharacter)) {
                // System.out.println("skipWhitespace");
                this.skipWhitespace();
                // this.advance(); // test this later to check if you can remove the skipWhitespace method
                continue;
            }

            if (Character.isAlphabetic(this.currentCharacter)) {
                // System.out.println("isAlphabetic");
                return this.id();
            }

            if (Character.isDigit(this.currentCharacter)) {
                // System.out.println("isDigit");
                return new Token(TokenType.INTEGER.name(), String.valueOf(this.integer()));
            }

            switch(this.currentCharacter) {
                case '=':
                    // System.out.println("is =");
                    this.advance();
                    return new Token(TokenType.ASSIGN.name(), "=");
                case '+':
                    // System.out.println("is +");
                    this.advance();
                    return new Token(TokenType.PLUS.name(), "+");
                case '-':
                    // System.out.println("is -");
                    this.advance();
                    return new Token(TokenType.MINUS.name(), "-");
                case '*':
                    // System.out.println("is *");
                    this.advance();
                    return new Token(TokenType.MUL.name(), "*");
                case '/':
                    // System.out.println("is /");
                    this.advance();
                    return new Token(TokenType.DIV.name(), "/");
                case '(':
                    // System.out.println("is (");
                    this.advance();
                    return new Token(TokenType.LPAREN.name(), "(");
                case ')':
                    // System.out.println("is )");
                    this.advance();
                    return new Token(TokenType.RPAREN.name(), ")");
                case ';':
                    this.advance();
                    return new Token(TokenType.SEMICOLON.name(), ";");
            }

            this.error();
        }
        return new Token(TokenType.EOF.toString(), TokenType.EOF.toString());
    }
}
/*
            if (this.currentCharacter == '=') {
                    System.out.println("is =");
                    this.advance();
                    return new Token(TokenType.ASSIGN.toString(), "=");
                    }

                    if (this.currentCharacter == '+') {
                    System.out.println("is +");
                    this.advance();
                    return new Token(TokenType.PLUS.toString(), "+");
                    }

                    if (this.currentCharacter == '-') {
                    System.out.println("is -");
                    this.advance();
                    return new Token(TokenType.MINUS.toString(), "-");
                    }

                    if (this.currentCharacter == '*') {
                    System.out.println("is *");
                    this.advance();
                    return new Token(TokenType.MUL.toString(), "*");
                    }

                    if (this.currentCharacter == '/') {
                    System.out.println("is /");
                    this.advance();
                    return new Token(TokenType.DIV.toString(), "/");
                    }

                    if (this.currentCharacter == '(') {
                    System.out.println("is (");
                    this.advance();
                    return new Token(TokenType.LPAREN.toString(), "(");
                    }

                    if (this.currentCharacter == ')') {
                    system.out.println("is )");
                    this.advance();
                    return new token(tokentype.rparen.tostring(), ")");
                    }
*/