package com.tia.models;

import com.tia.enums.Symbol;

public class Agent {
    private Symbol symbol;
    private int sourceX;
    private int sourceY;
    private int destinationX;
    private int destinationY;

    public Agent(Symbol symbol, int sourceX, int sourceY, int destinationX, int destinationY) {
        this.symbol = symbol;
        this.sourceX = sourceX;
        this.sourceY = sourceY;
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

    @Override
    public String toString() {
        return "Agent{" +
                "symbol=" + symbol +
                ", start=(" + sourceX + ", " + sourceY + ")" +
                ", destination=(" + destinationX + ", " + destinationY + ")" +
                '}';
    }
}
