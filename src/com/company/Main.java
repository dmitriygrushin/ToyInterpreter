package com.company;

import com.company.parser.Parser;

public class Main {

    public static void main(String[] args) throws Exception {
        /* String text = "3- 500";
        Interpreter interpreter = new Interpreter(text);

        int result = interpreter.expr();

        System.out.println(result); */

        Lexer lexer = new Lexer("x = 2 + 2;");

        Parser parser = new Parser(lexer);

        parser.program();
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


    -----------------Eliminating Left Recursion------------------------------------------------
       Program: Assignment*

       Assignment: Identifier = Exp;

       Exp: Term Exp'                      |
       Exp': +Term E' | -Term E' | ε       | +, -, ε

       Term: Fact Term'          |
       Term': * Fact Term' | ε   | *, ε

       Fact: ( Exp ) | - Fact | + Fact | Literal | Identifier

       Identifier: Letter [Letter | Digit]*

       Literal: 0 | NonZeroDigit Digit*


 */
