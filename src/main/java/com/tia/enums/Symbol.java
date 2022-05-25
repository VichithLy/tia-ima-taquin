package com.tia.enums;

public enum Symbol {
    A(0, "A"), B(1, "B"), C(2, "C"),
    D(3, "D");

    private int code;
    private String text;

    Symbol(int code, String text) {
        this.code = code;
        this.text = text;
    }

    public static Symbol getSymbolByCode(int code) {
        for (Symbol symbol : Symbol.values()) {
            if (symbol.code == code) {
                return symbol;
            }
        }
        return null;
    }

    public String getText() {
        return text;
    }
}
