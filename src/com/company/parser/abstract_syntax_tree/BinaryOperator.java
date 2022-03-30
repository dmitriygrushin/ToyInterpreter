package com.company.parser.abstract_syntax_tree;

import com.company.Token;

// instead of having separate *,/,+,- classes this class will make it more generic
public class BinaryOperator implements AbstractSyntaxTree {
    Token token;
    AbstractSyntaxTree left;
    Token operator;
    AbstractSyntaxTree right;

    public BinaryOperator(AbstractSyntaxTree left, Token operator, AbstractSyntaxTree right) {
        this.left = left;
        this.token = this.operator= operator;
        this.right = right;
    }
}
