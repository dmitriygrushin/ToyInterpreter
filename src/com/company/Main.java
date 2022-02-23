package com.company;

public class Main {

    public static void main(String[] args) throws Exception {
        String text = "3- 500";
        Interpreter interpreter = new Interpreter(text);

        int result = interpreter.expr();

        System.out.println(result);
    }
}
