package com.tia.models;

import com.tia.enums.Symbol;

public class Agent {
    private Symbol symbol;
    private int x;
    private int y;
    private int finalX;
    private int finalY;

    public Agent(Symbol symbol, int x, int y, int finalX, int finalY) {
        this.symbol = symbol;
        this.x = x;
        this.y = y;
        this.finalX = finalX;
        this.finalY = finalY;
    }

    public Symbol getSymbol() {
        return symbol;
    }
}
