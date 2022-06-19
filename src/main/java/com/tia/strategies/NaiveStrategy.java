package com.tia.strategies;

import com.tia.enums.Direction;
import com.tia.models.Agent;
import com.tia.models.Box;
import com.tia.models.Game;
import com.tia.models.Grid;

public class NaiveStrategy implements Strategy {

    /**
     * Agent basic movement (NORTH, SOUTH, EAST, WEST).
     *
     * @param agent     Agent
     * @param direction Direction
     */
    @Override
    public void move(Agent agent, Direction direction) {
        Box current = agent.getCurrent();
        Grid grid = Game.getGrid();
        Box oldBox = grid.getBox(current.getX(), current.getY());

        switch (direction) {
            case NORTH:
                if (canMove(agent, Direction.NORTH)) {
                    Box newBox = grid.getBox(current.getX() - 1, current.getY());
                    newBox.setAgent(current.getAgent());
                    agent.setCurrent(newBox);
                    oldBox.setAgent(null);
                }
                break;

            case SOUTH:
                if (canMove(agent, Direction.SOUTH)) {
                    Box newBox = grid.getBox(current.getX() + 1, current.getY());
                    newBox.setAgent(current.getAgent());
                    agent.setCurrent(newBox);
                    oldBox.setAgent(null);
                }
                break;

            case WEST:
                if (canMove(agent, Direction.WEST)) {
                    Box newBox = grid.getBox(current.getX(), current.getY() - 1);
                    newBox.setAgent(current.getAgent());
                    agent.setCurrent(newBox);
                    oldBox.setAgent(null);
                }
                break;

            case EAST:
                if (canMove(agent, Direction.EAST)) {
                    Box newBox = grid.getBox(current.getX(), current.getY() + 1);
                    newBox.setAgent(current.getAgent());
                    agent.setCurrent(newBox);
                    oldBox.setAgent(null);
                }
                break;
        }
    }

    /**
     * Apply strategy: go to the right column first then go to the right row.
     *
     * @param agent
     */
    @Override
    public void solve(Agent agent) {
        int currentCol = agent.getCurrent().getY();
        int currentRow = agent.getCurrent().getX();
        int destinationCol = agent.getDestination().getY();
        int destinationRow = agent.getDestination().getX();

        if (currentRow == destinationRow) {
            if (currentCol > destinationCol) {
                move(agent, Direction.WEST);
            } else if (currentCol < destinationCol) {
                move(agent, Direction.EAST);
            }

        } else if (currentRow > destinationRow) {
            move(agent, Direction.NORTH);
        } else if (currentRow < destinationRow) {
            move(agent, Direction.SOUTH);
        }
    }

    private boolean canMove(Agent agent, Direction direction) {
        Box current = agent.getCurrent();
        Grid grid = Game.getGrid();
        Boolean value = false;
        int gridMaxIndex = Game.getGrid().getSize() - 1;

        if (direction.equals(Direction.NORTH)
                && grid.getBox(current.getX() - 1, current.getY()).getAgent() == null) {
            value = (current.getX() > 0);
        } else if (direction.equals(Direction.SOUTH)
                && grid.getBox(current.getX() + 1, current.getY()).getAgent() == null) {
            value = (current.getX() < gridMaxIndex);
        } else if (direction.equals(Direction.WEST)
                && grid.getBox(current.getX(), current.getY() - 1).getAgent() == null) {
            value = (current.getY() > 0);
        } else if (direction.equals(Direction.EAST)
                && grid.getBox(current.getX(), current.getY() + 1).getAgent() == null) {
            value = (current.getY() < gridMaxIndex);
        }

        return value;
    }
}
