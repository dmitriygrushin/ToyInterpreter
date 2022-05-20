package com.company;

import com.company.Lexer;
import com.company.Token;
import com.company.TokenType;

import java.util.HashMap;

public class Parser {
    Lexer lexer;
    Token currentToken;
    HashMap<String, Integer> dictionary; // will store the key, val of identifiers

    public Parser(Lexer lexer) throws Exception {
        this.lexer = lexer;
        this.currentToken = this.lexer.getNextToken();
        this.dictionary = new HashMap<>();
    }

    public void error () throws Exception {
        throw new Exception("Syntax Error");
    }

    /* (Could do without it but this seems to be the best practice for error checking)
        Compare currentToken with passed Token
        matching tokens get eaten and move on to the next token (advance currentToken)
        else throw a syntax error */
    public void match(String passedTokenType) throws Exception {
        if (!this.currentToken.getType().equals(passedTokenType)) this.error();
        this.currentToken = this.lexer.getNextToken();
    }

    /* ---------------------------------
        Production Rules/Grammar Rules:
    ---------------------------------- */

    // Program: Assignment*
    public void program() throws Exception {
        this.assignment();
        while (!this.currentToken.getValue().equals(TokenType.EOF.name())) {
            this.assignment();
        }
    }

    // Assignment: Identifier = Exp; ----> Identifier ASSIGN Exp SEMICOLON
    public void assignment() throws Exception {
        String variableName = this.identifier();
        this.match(TokenType.ASSIGN.name());
        int variableValue = this.exp();
        this.match(TokenType.SEMICOLON.name());
        dictionary.put(variableName, variableValue);
    }

    /* (Warning: Left recursive)
        Exp: Exp + Term | Exp - Term | Term
        ----- Eliminating Left Recursion ----------------
                                                | First
        Exp: Term Exp'                          |
        Exp': +Term Exp' | -Term Exp' |  ε      | +, -, ε
        */
    public int exp() throws Exception {
        return this.term() + this.expBar();
    }

    public int expBar() throws Exception {
        //System.out.println("testing expBar");
        if (this.currentToken.getType().equals(TokenType.PLUS.name())) {
            //System.out.println("expBar() - match(plus)");
            this.match(TokenType.PLUS.name());
            return this.term() + this.expBar();
        } else if (this.currentToken.getType().equals(TokenType.MINUS.name())) {
            this.match(TokenType.MINUS.name());
            return -this.term() + this.expBar();
        } else {
            return 0; // ε add nothing
        }
    }

    /*  (Warning: Left recursive)
        Term: Term * Fact  | Fact
        ----- Eliminating Left Recursion ----------------
                                 | First |
       Term: Fact Term'          |
       Term': * Fact Term' | ε   | *, ε
        */
    public int term() throws Exception {
        return this.fact() * this.termBar();
    }

    public int termBar() throws Exception {
        if (this.currentToken.getType().equals(TokenType.MUL.name())) {
            this.match(TokenType.MUL.name());
            return this.fact() * this.termBar();
        } else {
            return 1;
        }
    }

    // Fact: ( Exp ) | - Fact | + Fact | Literal | Identifier
    public int fact() throws Exception {
        //System.out.println("testing factor");
        if (this.currentToken.getType().equals(TokenType.LPAREN.name())) {
            this.match(TokenType.LPAREN.name());
            int expression = this.exp();
            if (this.currentToken.getType().equals(TokenType.RPAREN.name())) {
                this.match(TokenType.RPAREN.name());
            }
            return expression;
        } else if (this.currentToken.getType().equals(TokenType.MINUS.name())) {
            this.match(TokenType.MINUS.name());
            return -this.fact();
        } else if (this.currentToken.getType().equals(TokenType.PLUS.name())) {
            //System.out.println("fact() - match(plus)");
            this.match(TokenType.PLUS.name());
            return this.fact();
        }  else if (this.currentToken.getType().equals(TokenType.INTEGER.name())) {
            ////System.out.println("fact() - match(int)");
            return this.literal();
        }else if (this.currentToken.getType().equals(TokenType.ID.name())) {
            return Integer.parseInt(this.identifier());
        }

        throw new Exception("Syntax Error");
    }

    // will print the key value pairs from the dictionary of variables
    public StringBuilder printTable() {
        StringBuilder result = new StringBuilder();
        for (String name: dictionary.keySet()) {
            String key = name.toString();
            String value = dictionary.get(name).toString();
            String keyValPair = dictionary.size() > 1 ? key + " = " + value + "\n" : key + " = " + value;
            result.append(keyValPair);
        }
        return result;
    }

    // Identifier: Letter [Letter | Digit]*
    public String identifier() throws Exception {
       /*   if dictionary contains this variable then return its integer value
            else return its variable name to put into the dictionary */
        String variable;
        if (dictionary.containsKey(this.currentToken.getValue())) {
             variable = String.valueOf(dictionary.get(this.currentToken.getValue()));
        } else {
            variable = this.currentToken.getValue();
        }
        this.match(TokenType.ID.name());
        // If Identifier's `value` is returned then it needs to be parsed into its integer type
        return variable;
    }

    // Literal: 0 | NonZeroDigit Digit*
    public int literal() throws Exception {
        int lit =  Integer.parseInt(currentToken.getValue());
        this.match(TokenType.INTEGER.toString());
        return lit;
    }

}
