package com.company;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        File inFile = null;
        if (0 < args.length) {
            inFile = new File(args[0]);
            String code = readFile(inFile);
            interpreter(code);
        } else {
            System.err.println("Invalid arguments count:" + args.length);
        }
    }

    private static void interpreter(String code) throws Exception {
        Lexer lexer = new Lexer(code);
        Parser parser = new Parser(lexer);
        parser.program();
        System.out.println(parser.printTable());
    }

    private static String readFile(File file) throws IOException {
        StringBuilder fileContents = new StringBuilder((int)file.length());
        try (Scanner scanner = new Scanner(file)) {
            while(scanner.hasNextLine()) {
                fileContents.append(scanner.nextLine() + System.lineSeparator());
            }
            return fileContents.toString();
        }
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
