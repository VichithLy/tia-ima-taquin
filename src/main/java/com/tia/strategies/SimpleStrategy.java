package com.tia.strategies;

import com.tia.enums.Direction;
import com.tia.models.Agent;

public class SimpleStrategy implements Strategy {
    @Override
    public void move(Agent agent, Direction direction) {

    }

    @Override
    public void solve(Agent agent) {
        System.out.println("SimpleStrategy");
    }
}
