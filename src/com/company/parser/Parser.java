package com.company.parser;

import com.company.Lexer;
import com.company.Token;
import com.company.TokenType;
import com.company.parser.abstract_syntax_tree.*;
import com.company.parser.abstract_syntax_tree.Number;

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
        while (this.currentToken.getType() != TokenType.EOF.toString()) {
           this.assignment();
        }
    }

    // Assignment: Identifier = Exp;
    public AbstractSyntaxTree assignment() throws Exception {
        AbstractSyntaxTree left = this.identifier();
        Token token = this.currentToken;
        this.match(TokenType.ASSIGN.toString());
        AbstractSyntaxTree right = this.exp();
        return new Assignment(left, token, right);
    }

    /* (Warning: Left recursive)
        Exp: Exp + Term | Exp - Term | Term
        ----- Eliminating Left Recursion ----------------
                                            | First
        Exp: Term Exp'                      |
        Exp': +Term E' | -Term E' | ε       | +, -, ε
        */
    public AbstractSyntaxTree exp() throws Exception {
        this.match(TokenType.PLUS.toString());
        return new BinaryOperator(this.fact(), new Token(TokenType.PLUS.toString(), "+"), this.termBar());
    }

    public AbstractSyntaxTree expBar() throws Exception {
       if (this.currentToken.getType() == TokenType.PLUS.toString()) {
           this.match(TokenType.PLUS.toString());
           return new BinaryOperator(this.fact(), new Token(TokenType.PLUS.toString(), "+"), this.termBar());
       } else if (this.currentToken.getType() == TokenType.MINUS.toString()) {
           this.match(TokenType.MINUS.toString());
           return new BinaryOperator(this.fact(), new Token(TokenType.MINUS.toString(), "-"), this.termBar());
       } else {
           return new Number(new Token(TokenType.INTEGER.toString(), "0"));
       }
    }

    /*  (Warning: Left recursive)
        Term: Term * Fact  | Fact
        ----- Eliminating Left Recursion ----------------
                                 | First |
       Term: Fact Term'          |
       Term': * Fact Term' | ε   | *, ε
        */
    public AbstractSyntaxTree term() throws Exception {
        return new BinaryOperator(this.fact(), new Token(TokenType.MUL.toString(), "*"), this.termBar());
    }

    public AbstractSyntaxTree termBar() throws Exception {
        if (this.currentToken.getType() == TokenType.MUL.toString()) {
            this.match(TokenType.MUL.toString());
            return new BinaryOperator(this.fact(), new Token(TokenType.MUL.toString(), "*"), this.termBar());
        } else {
            return new Number(new Token(TokenType.INTEGER.toString(), "1"));
        }
    }


    // Fact: ( Exp ) | - Fact | + Fact | Literal | Identifier
    public AbstractSyntaxTree fact() throws Exception {
        if (this.currentToken.getType() == TokenType.LPAREN.toString()) {
            this.match(TokenType.LPAREN.toString());
            AbstractSyntaxTree node = this.exp();
            this.match(TokenType.RPAREN.toString());
            return node;
        } else if (this.currentToken.getType() == TokenType.MINUS.toString()) {
            this.match(TokenType.MINUS.toString());
            return new UnaryOperator(this.currentToken, this.fact());
        } else if (this.currentToken.getType() == TokenType.PLUS.toString()) {
            this.match(TokenType.PLUS.toString());
            return new UnaryOperator(this.currentToken, this.fact());
        } else if (this.currentToken.getType() == TokenType.INTEGER.toString()) {
            this.match(TokenType.INTEGER.toString());
            return new Number(this.currentToken);
        } else if (this.currentToken.getType() == TokenType.ID.toString()) {
            return this.identifier();
        } else {
            throw new Exception("Syntax error");
        }
    }

    /* Variable | This will replace the 'Letter' production */
    // Identifier: Letter [Letter | Digit]*
    public AbstractSyntaxTree identifier() throws Exception {
        AbstractSyntaxTree node = new Variable(this.currentToken);
        this.match(TokenType.ID.toString());
        return node;
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
    public AbstractSyntaxTree literal() throws Exception {
        AbstractSyntaxTree node = new Number(this.currentToken);
        this.match(TokenType.INTEGER.toString());
        return node;
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