package com.tia.models;

import com.tia.enums.Symbol;

public class Agent {
    private Symbol symbol;
    private Box source;
    private Box destination;
    private Box current;

    public Agent(Symbol symbol, Box current, Box destination) {
        this.symbol = symbol;
        this.destination = destination;
        this.source = current;
        this.current = current;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public Box getSource() {
        return source;
    }

    public Box getDestination() {
        return destination;
    }

    public Box getCurrent() {
        return current;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    public void setSource(Box source) {
        this.source = source;
    }

    public void setDestination(Box destination) {
        this.destination = destination;
    }

    public void setCurrent(Box current) {
        this.current = current;
    }

    @Override
    public String toString() {
        return "Agent{" +
                "symbol=" + symbol +
                ", current=" + current.toString() +
                ", destination=" + destination.toString() +
                '}';
    }
}
