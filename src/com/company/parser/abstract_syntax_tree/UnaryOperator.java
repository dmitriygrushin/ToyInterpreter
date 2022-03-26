package com.company.parser.abstract_syntax_tree;

import com.company.Token;

public class UnaryOperator implements AbstractSyntaxTree{
    Token token;
    Token operator;
    Token expression;

    public UnaryOperator(Token operator, Token expression) {
        this.token = this.operator = operator;
        this.expression = expression;
    }



}
