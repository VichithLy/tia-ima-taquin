package com.tia.strategies;

import com.tia.algorithms.Path;
import com.tia.enums.Direction;
import com.tia.models.Agent;
import com.tia.models.Box;

import java.util.*;

public class SimpleStrategy implements Strategy {
    /**
     * Agent basic movement (NORTH, SOUTH, EAST, WEST).
     * @param agent
     * @param direction
     */
    @Override
    public void move(Agent agent, Direction direction) {
        NaiveStrategy strategy = new NaiveStrategy();
        strategy.move(agent, direction);
    }

    /**
     * Apply strategy: compute shortest path to destination and follow it.
     * @param agent
     */
    @Override
    public void solve(Agent agent) {
        List<Box> path = Path.find(agent, true);
        List<Direction> directions = Path.convertToDirections(path);

        agent.setPathDirections(directions);

        if (directions.size() != 0) {
            move(agent, directions.get(0));
        }
    }
}
