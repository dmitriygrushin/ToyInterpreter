package com.company.parser;

import com.company.Lexer;
import com.company.Token;
import com.company.TokenType;
import com.company.parser.abstract_syntax_tree.*;
import com.company.parser.abstract_syntax_tree.Number;

import java.util.HashMap;

public class Parser {
    Lexer lexer;
    Token currentToken;
    HashMap<String, Integer> dic;

    public Parser(Lexer lexer) throws Exception {
        this.lexer = lexer;
        this.currentToken = this.lexer.getNextToken();
        this.dic = new HashMap<>();
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
        while (this.currentToken.getValue() != TokenType.EOF.name()) {
            this.assignment();
        }
    }

    // Assignment: Identifier = Exp; Identifier ASSIGN Exp;
    public void assignment() throws Exception {
        this.identifier();
        this.match(TokenType.ASSIGN.name());
        this.exp();
    }

    /* (Warning: Left recursive)
        Exp: Exp + Term | Exp - Term | Term
        ----- Eliminating Left Recursion ----------------
                                            | First
        Exp: Term Exp'                      |
        Exp': +Term E' | -Term E' | ε       | +, -, ε
        */
    public void exp() throws Exception {
        this.term();
        this.expBar();
    }

    public void expBar() throws Exception {
        if (this.currentToken.getValue() == TokenType.PLUS.name()) {
            this.match(TokenType.PLUS.name());
            this.term();
            this.expBar();
        }

        if (this.currentToken.getValue() == TokenType.MINUS.name()) {
            this.match(TokenType.MINUS.name());
            this.term();
            this.expBar();
        }
    }

    /*  (Warning: Left recursive)
        Term: Term * Fact  | Fact
        ----- Eliminating Left Recursion ----------------
                                 | First |
       Term: Fact Term'          |
       Term': * Fact Term' | ε   | *, ε
        */
    public void term() throws Exception {
        this.fact();
        this.termBar();
    }

    public void termBar() throws Exception {
        if (this.currentToken.getType() == TokenType.MUL.name()) {
            this.match(TokenType.MUL.name());
            this.fact();
            this.termBar();
        }
    }


    // Fact: ( Exp ) | - Fact | + Fact | Literal | Identifier
    public void fact() throws Exception {
        if (this.currentToken.getType() == TokenType.LPAREN.name()) {
            this.match(TokenType.LPAREN.name());
            this.exp();
            if (this.currentToken.getType() == TokenType.RPAREN.name()) {
                this.match(TokenType.RPAREN.name());
            }
        } else if (this.currentToken.getType() == TokenType.MINUS.name()) {
            this.match(TokenType.MINUS.name());
            this.fact();
        } else if (this.currentToken.getType() == TokenType.PLUS.name()) {
            this.match(TokenType.PLUS.name());
            this.fact();
        }  else if (this.currentToken.getType() == TokenType.INTEGER.name()) {
            this.literal();
        }else if (this.currentToken.getType() == TokenType.ID.name()) {
            this.identifier();
        } else {
            this.error();
        }
    }

    public void printTable() {
        dic.forEach((key, value) -> System.out.println(key + " " + value));
    }

    /* Variable | This will replace the 'Letter' production */
    // Identifier: Letter [Letter | Digit]*
    public void identifier() throws Exception {
        System.out.println("--id--");
    }

    /*  Letters will be used for variables so there needs to be place to store them */
    // Letter: a|...|z|A|...|Z|_               (- Terminal -)
    public AbstractSyntaxTree letter() throws Exception {
        AbstractSyntaxTree node = new Variable(this.currentToken);
        this.match(TokenType.ID.toString());
        return node;
    }

    /*  Will need to change 'literal' method
        after changing the nonZeroDigit & digit methods.
        For the moment this will just be treated as a simple integer type */

    // Literal: 0 | NonZeroDigit Digit*
    public void literal() throws Exception {
        AbstractSyntaxTree node = new Number(this.currentToken);
        this.match(TokenType.INTEGER.toString());
    }

    /*  update the below 2 methods later: nonDigitZero and digit
        since both of them can NOT be the same token.type
        the grammar does NOT allow digits with leading 0s besides the #: 0 */

    // NonZeroDigit: 1|...|9                   (- Terminal -)
    public AbstractSyntaxTree nonDigitZero() throws Exception {
        AbstractSyntaxTree node = new Number(this.currentToken);
        this.match(TokenType.INTEGER.toString());
        return node;
    }

    // Digit: 0|1|...|9                        (- Terminal -)
    public AbstractSyntaxTree digit() throws Exception {
        AbstractSyntaxTree node = new Number(this.currentToken);
        this.match(TokenType.INTEGER.toString());
        return node;
    }
}
