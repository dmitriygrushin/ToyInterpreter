package com.company.parser.abstract_syntax_tree;

import com.company.Token;

public class Number implements AbstractSyntaxTree{
    Token token;
    int value;

    public Number(Token token) {
        this.token = token;
        // could due without it but makes it more convenient
        this.value = Integer.parseInt(token.getValue());
    }
}
