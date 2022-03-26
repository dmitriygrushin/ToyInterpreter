package com.company.parser;

import com.company.Lexer;
import com.company.Token;

import java.util.concurrent.ExecutionException;

public class Parser {
    Lexer lexer;
    Token currentToken;

    public Parser(Lexer lexer) throws Exception {
        this.lexer = lexer;
        this.currentToken = this.lexer.getNextToken();
    }

    public void error () throws Exception {
        throw new Exception("Syntax Error");
    }

    /* (Could do without it but this seems to be the best practice)
        Compare currentToken with passed Token
        matching tokens get eaten and move on to the next token (advance currentToken)
        else throw a syntax error */
    public void eat(String passedTokenType) throws Exception {
        if (!this.currentToken.getType().equals(passedTokenType)) this.error();
        this.currentToken = this.lexer.getNextToken();
    }

    /*
    Production Rules/Grammar Rules:
    */

    // Program: Assignment*



}
