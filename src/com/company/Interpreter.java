package com.company;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Interpreter {
    private String text;
    private Token currentToken = null;
    private LinkedList<Token> tokens;

    public Interpreter(String text) throws Exception {
        this.text = text;
        tokens = LexicalAnalyzer(text);
    }
    public boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }

    // Tokenizer/Lexical Analyzer: Breaks string apart into tokens
    // Returns: list of all tokens in the String this.text
    public LinkedList<Token> LexicalAnalyzer(String text) throws Exception {

        Pattern p = Pattern.compile("(\\d+)|([+-])");
        Matcher m = p.matcher(text);

        LinkedList<Token> tokens = new LinkedList<>();

        while(m.find()) {
            String token = m.group(0); // group 0 will return the entire match like. group 1 would be null for +

            if (isNumeric(token)) {
                // check if token is a integer
                tokens.add(new Token("INTEGER", token));
            } else if (token.equals("+")) {
                // check if the token is an operator (+)
                tokens.add(new Token("PLUS", token));
            } else if (token.equals("-")) {
                // check if the token is an operator (-)
                tokens.add(new Token("MINUS", token));
            } else {
                throw new Exception("Interpreter error: getNextToken");
            }
        }

        return tokens;

    }

    // compare currentToken type to the token type passed as a parameter to make sure they're a match
    public void eat(String tokenType) throws Exception {
        if (Objects.equals(this.currentToken.getType(), tokenType)) {
            this.currentToken = this.tokens.poll(); // currentToken will now be the next token
        } else {
            throw new Exception("Interpreter error: eat");
        }
    }

    // expression: INTEGER PLUS INTEGER
    public int expr() throws Exception {
        this.currentToken = this.tokens.poll();

        // left int token
        Token left = this.currentToken;
        eat("INTEGER");

        Token op = this.currentToken;

        if (op.getType().equals("PLUS")) {
            // (+) operator token
            eat("PLUS");
        } else if (op.getType().equals("MINUS")) {
            eat("MINUS");
        }
        // right int token
        Token right = this.currentToken;
        eat("INTEGER");

        // after the above call the this.currentToken is set to EOF

        // convert INTEGER token into int since it's stored as string then return the sum
        if (op.getType().equals("PLUS")) {
            return Integer.parseInt(left.getValue()) + Integer.parseInt(right.getValue());
        }
        if (op.getType().equals("MINUS")) {
            return Integer.parseInt(left.getValue()) - Integer.parseInt(right.getValue());
        }

        throw new Exception("Incorrect operator");
    }

}
