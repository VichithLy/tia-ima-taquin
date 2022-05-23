package com.tia.models;

import com.tia.enums.Symbol;

public class Agent {
    private Symbol symbol;
    private int startX;
    private int startY;
    private int destinationX;
    private int destinationY;

    public Agent(Symbol symbol, int startX, int startY, int destinationX, int destinationY) {
        this.symbol = symbol;
        this.startX = startX;
        this.startY = startY;
        this.destinationX = destinationX;
        this.destinationY = destinationY;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public int getDestinationX() {
        return destinationX;
    }

    public int getDestinationY() {
        return destinationY;
    }
}
