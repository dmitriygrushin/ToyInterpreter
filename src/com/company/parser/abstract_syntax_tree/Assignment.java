package com.company.parser.abstract_syntax_tree;

import com.company.Token;

public class Assignment implements AbstractSyntaxTree {
    Token token;
    AbstractSyntaxTree left;
    Token operator;
    AbstractSyntaxTree right;

    public Assignment(AbstractSyntaxTree left,Token operator, AbstractSyntaxTree right ) {
        this.left = left;
        this.token = this.operator = operator;
        this.right = right;
    }
}
