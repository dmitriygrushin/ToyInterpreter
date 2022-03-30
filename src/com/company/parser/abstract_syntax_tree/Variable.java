package com.company.parser.abstract_syntax_tree;

import com.company.Token;

public class Variable implements AbstractSyntaxTree {
    Token token;
    String value;

    public Variable(Token token) {
        this.token = token;
        this.value = this.token.getValue();
    }
}
