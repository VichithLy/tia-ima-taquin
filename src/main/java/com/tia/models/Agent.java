package com.tia.models;

import com.tia.enums.Symbol;

public class Agent {
    private Symbol symbol;
    private Box source;
    private Box destination;
    private Box current;

    public Agent(Symbol symbol, Box source, Box destination, Box current) {
        this.symbol = symbol;
        this.source = source;
        this.destination = destination;
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

    @Override
    public String toString() {
        return "Agent{" +
                "symbol=" + symbol +
                ", source=" + source.toString() +
                ", destination=" + destination.toString() +
                ", current=" + current.toString() +
                '}';
    }
}
