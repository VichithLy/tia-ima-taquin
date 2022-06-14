package com.tia.strategies;

import com.tia.models.Agent;

public class CognitiveStrategy implements Strategy {
    @Override
    public void solve(Agent agent) {
        System.out.println("CognitiveStrategy");
    }
}