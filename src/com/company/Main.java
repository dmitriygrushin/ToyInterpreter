package com.company;

public class Main {

    public static void main(String[] args) throws Exception {
        String text = "3+5";
        Interpreter interpreter = new Interpreter(text);

        int result = interpreter.expr();

        System.out.println(result);
    }
}
