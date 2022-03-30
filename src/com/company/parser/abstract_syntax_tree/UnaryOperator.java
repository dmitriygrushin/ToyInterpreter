package com.company.parser.abstract_syntax_tree;

import com.company.Token;

public class UnaryOperator implements AbstractSyntaxTree {
    Token token;
    Token operator;
    AbstractSyntaxTree expression;

    public UnaryOperator(Token operator, AbstractSyntaxTree expression) {
        this.token = this.operator = operator;
        this.expression = expression;
    }



}
