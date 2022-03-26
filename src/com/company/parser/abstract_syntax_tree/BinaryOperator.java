package com.company.parser.abstract_syntax_tree;

import com.company.Token;

public class BinaryOperator implements AbstractSyntaxTree {
    Token token;
    Token left;
    Token operator;
    Token right;

    public BinaryOperator(Token left, Token operator, Token right) {
        this.left = left;
        this.token = this.operator= operator;
        this.right = right;
    }
}
