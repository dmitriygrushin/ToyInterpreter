package com.company;

import java.util.Objects;

public class Interpreter {
    private String text;
    private Token currentToken = null;
    private int pos = 0; // position/index of where the interpreter is in the string

    public Interpreter(String text) {
        this.text = text;
    }

    // Tokenizer/Lexical Analyzer: Breaks string apart into tokens
    public Token getNextToken() throws Exception {
        String text = this.text;

        // check if there is any more input left to convert into tokens
        if (this.pos > text.length() - 1) {
            // EOF: end of line
            return new Token("EOF", '\0');
        }

        // get character at current pos
        char currentChar = text.charAt(this.pos);

        // check if a character is a digit
        if (Character.isDigit(currentChar)) {
            this.pos += 1;
            return new Token("INTEGER", currentChar);
        }

        if (currentChar == '+') {
            this.pos += 1;
            return new Token("PLUS", currentChar);
        }

        throw new Exception("Interpreter error: getNextToken");
    }

    // compare currentToken to the token passed as a parameter to make sure they're a match
    public void eat(String tokenType) throws Exception {
        if (Objects.equals(this.currentToken.getType(), tokenType)) {
            this.currentToken = this.getNextToken();
        } else {
            throw new Exception("Interpreter error: eat");
        }
    }

    // expression: INTEGER PLUS INTEGER
    public int expr() throws Exception{
        // get first token
        this.currentToken = this.getNextToken(); // need to use getNextToken since the original token is null

        // left int token (currently single digit)
        Token left = this.currentToken;
        eat("INTEGER");


        // operator token
        Token op = this.currentToken;
        eat("PLUS");


        // left int token (currently single digit)
        Token right = this.currentToken;
        eat("INTEGER");

        // after the above call the this.currentToken is set to EOF


        // convert INTEGER token into int since it's stored as char then return the sum
        return Character.getNumericValue(left.getValue()) + Character.getNumericValue(right.getValue());
    }


}
