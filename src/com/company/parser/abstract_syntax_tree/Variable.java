package com.company.parser.abstract_syntax_tree;

import com.company.Token;

public class Variable implements AbstractSyntaxTree{
    Token token;

    public Variable(Token token) {
        this.token = token;
    }
}
