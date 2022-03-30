package com.company;

public class Main {

    public static void main(String[] args) throws Exception {
        /*
        String text = "3- 500";
        Interpreter interpreter = new Interpreter(text);

        int result = interpreter.expr();

        System.out.println(result);

         */

        Lexer lexer = new Lexer("(x + 2)");

        Token token = lexer.getNextToken();
        Token token2 = lexer.getNextToken();


        System.out.println(token.toString());
        System.out.println(token2.toString());


    }
}
/*
    Program: Assignment*

    Assignment: Identifier = Exp;

    Exp: Exp + Term | Exp - Term | Term

    Term: Term * Fact  | Fact

    Fact: ( Exp ) | - Fact | + Fact | Literal | Identifier

    Identifier: Letter [Letter | Digit]*

    Letter: a|...|z|A|...|Z|_               (- Terminal -)

    Literal: 0 | NonZeroDigit Digit*

    NonZeroDigit: 1|...|9                   (- Terminal -)

    Digit: 0|1|...|9                        (- Terminal -)
 */
