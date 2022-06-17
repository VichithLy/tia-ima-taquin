package com.tia.strategies;

import com.tia.enums.Direction;
import com.tia.models.Agent;
import com.tia.models.Box;
import com.tia.models.Game;
import com.tia.models.Grid;

public class NaiveStrategy implements Strategy {

    /**
     * While Agent is not arrived.
     * Go to NORTH, SOUTH, EAST or WEST Box in board.
     * Check if Agent can move to Box (inside board's bounds).
     * and destination Box is empty (no other Agent).
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
                    // New current
                    Box newBox = grid.getBox(current.getX() - 1, current.getY());
                    newBox.setAgent(current.getAgent());
                    agent.setCurrent(newBox);
                    // Old current
                    oldBox.setAgent(null);
                }
                break;

            case SOUTH:
                if (canMove(agent, Direction.SOUTH)) {
                    // New current
                    Box newBox = grid.getBox(current.getX() + 1, current.getY());
                    newBox.setAgent(current.getAgent());
                    agent.setCurrent(newBox);
                    // Old current
                    oldBox.setAgent(null);
                }
                break;

            case WEST:
                if (canMove(agent, Direction.WEST)) {
                    // New current
                    Box newBox = grid.getBox(current.getX(), current.getY() - 1);
                    newBox.setAgent(current.getAgent());
                    agent.setCurrent(newBox);
                    // Old current
                    oldBox.setAgent(null);
                }
                break;

            case EAST:
                if (canMove(agent, Direction.EAST)) {
                    // New current
                    Box newBox = grid.getBox(current.getX(), current.getY() + 1);
                    newBox.setAgent(current.getAgent());
                    agent.setCurrent(newBox);
                    // Old current
                    oldBox.setAgent(null);
                }
                break;
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

    /**
     * Go to the right column first
     * then go to the right row
     *
     * @param agent
     */
    @Override
    public void solve(Agent agent) {
        System.out.println("NaiveStrategy solving...");

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
}
