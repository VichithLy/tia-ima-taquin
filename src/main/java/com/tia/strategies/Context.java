package com.tia.strategies;

import com.tia.models.Agent;

public class Context {
    private Strategy strategy;

    public Context(Strategy strategy){
        this.strategy = strategy;
    }

    public void executeStrategy(Agent agent){
        strategy.solve(agent);
    }
}