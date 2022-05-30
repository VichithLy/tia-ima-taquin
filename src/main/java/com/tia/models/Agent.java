package com.tia.models;

import com.tia.enums.Symbol;
import javafx.scene.layout.StackPane;

public class Agent {
    private Symbol symbol;
    private Box source;
    private Box destination;
    private Box current;

    public Agent(Symbol symbol, Box current, Box destination) {
        this.symbol = symbol;
        this.destination = destination;
        this.current = current;
    }

    public Symbol getSymbol() {
        return symbol;
    }


    public Box getDestination() {
        return destination;
    }

    public Box getCurrent() {
        return current;
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
