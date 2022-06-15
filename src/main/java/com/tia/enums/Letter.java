package com.tia.enums;

public enum Letter {
    NULL(-1, ""), A(0, "A"), B(1, "B"), C(2, "C"),
    D(3, "D"), E(4, "E"), F(5, "F"),
    G(6, "G"), H(7, "H"), I(8, "I"),
    J(9, "J"), K(10, "K"), L(11, "L"),
    M(12, "M"), N(13, "N"), O(14, "O"),
    P(15, "P"), Q(16, "Q"), R(17, "R"),
    S(18, "S"), T(19, "T"), U(20, "U"),
    V(21, "V"), W(22, "W"), X(23, "X");


    private int code;
    private String text;

    Letter(int code, String text) {
        this.code = code;
        this.text = text;
    }

    public static Letter getLetterByCode(int code) {
        for (Letter symbol : Letter.values()) {
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
