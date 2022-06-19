package com.tia.strategies;

import com.tia.enums.Direction;
import com.tia.models.Agent;

/**
 * Ressource: https://www.tutorialspoint.com/design_pattern/strategy_pattern.htm
 */
public interface Strategy {
    public void move(Agent agent, Direction direction);

    public void solve(Agent agent);
}
