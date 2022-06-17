package com.tia.strategies;

import com.tia.GameUtils;
import com.tia.algorithms.BFS;
import com.tia.enums.Direction;
import com.tia.models.Agent;
import com.tia.models.Box;
import com.tia.models.Game;
import com.tia.models.Grid;

import java.util.*;

public class SimpleStrategy implements Strategy {
    @Override
    public void move(Agent agent, Direction direction) {
        NaiveStrategy strategy = new NaiveStrategy();
        strategy.move(agent, direction);
    }

    @Override
    public void solve(Agent agent) {
        List<Box> path = BFS.findPath(agent, true);
        List<Direction> directions = BFS.convertPathToDirections(path);

        agent.setPathDirections(directions);

        if (directions.size() != 0) {
            move(agent, directions.get(0));
        }
    }
}
