package com.tia.strategies;

import com.tia.GameUtils;
import com.tia.algorithms.BFS;
import com.tia.enums.Content;
import com.tia.enums.Direction;
import com.tia.enums.Subject;
import com.tia.messages.Mail;
import com.tia.messages.MailBox;
import com.tia.models.Agent;
import com.tia.models.Box;
import com.tia.models.Game;
import com.tia.models.Grid;

import java.util.List;
import java.util.PriorityQueue;

public class CognitiveStrategy implements Strategy {
    @Override
    public void move(Agent agent, Direction direction) {
        NaiveStrategy strategy = new NaiveStrategy();
        strategy.move(agent, direction);

        // If direction D
            // If in bounds
                // If no other Agent
                    // Move
                // Else
                    // Send message to this Agent
            // Else
                // Don't move

        /*Box current = agent.getCurrent();
        Grid grid = Game.getGrid();
        Box oldBox = grid.getBox(current.getX(), current.getY());

        switch (direction) {
            case NORTH:
                GameUtils.print("isInBounds", isInBounds(agent, Direction.EAST));
                GameUtils.print("isFree", isFree(agent, Direction.EAST));

                if (isInBounds(agent, Direction.NORTH)) {
                    if (isFree(agent, Direction.NORTH)) {
                        Box newBox = grid.getBox(current.getX() - 1, current.getY());
                        newBox.setAgent(current.getAgent());
                        agent.setCurrent(newBox);
                        oldBox.setAgent(null);
                    }
                }
                break;

            case SOUTH:
                GameUtils.print("isInBounds", isInBounds(agent, Direction.EAST));
                GameUtils.print("isFree", isFree(agent, Direction.EAST));

                if (isInBounds(agent, Direction.SOUTH)) {
                    if (isFree(agent, Direction.SOUTH)) {
                        Box newBox = grid.getBox(current.getX() + 1, current.getY());
                        newBox.setAgent(current.getAgent());
                        agent.setCurrent(newBox);
                        oldBox.setAgent(null);
                    }
                }

                break;

            case WEST:
                GameUtils.print("isInBounds", isInBounds(agent, Direction.EAST));
                GameUtils.print("isFree", isFree(agent, Direction.EAST));

                if (isInBounds(agent, Direction.WEST)) {
                    if (isFree(agent, Direction.WEST)) {
                        Box newBox = grid.getBox(current.getX(), current.getY() - 1);
                        newBox.setAgent(current.getAgent());
                        agent.setCurrent(newBox);
                        oldBox.setAgent(null);
                    }
                }

                break;

            case EAST:
                GameUtils.print("isInBounds", isInBounds(agent, Direction.EAST));
                GameUtils.print("isFree", isFree(agent, Direction.EAST));

                if (isInBounds(agent, Direction.EAST)) {
                    if (isFree(agent, Direction.EAST)) {
                        Box newBox = grid.getBox(current.getX(), current.getY() + 1);
                        newBox.setAgent(current.getAgent());
                        agent.setCurrent(newBox);
                        oldBox.setAgent(null);
                    }
                }

                break;
        }*/
    }

    private boolean isInBounds(Agent agent, Direction direction) {
        int row = agent.getCurrent().getX();
        int col = agent.getCurrent().getY();

        if (direction.equals(Direction.NORTH)) {
            if ((row - 1) < 0) return false;
        } else if (direction.equals(Direction.SOUTH)) {
            if ((row + 1) < 0) return false;
        } else if (direction.equals(Direction.WEST)) {
            if ((col - 1) < 0) return false;
        } else if (direction.equals(Direction.EAST)) {
            if ((col + 1) < 0) return false;
        }

        return true;
    }

    private boolean isFree(Agent agent, Direction direction) {
        Grid grid = Game.getGrid();

        int row = agent.getCurrent().getX();
        int col = agent.getCurrent().getY();

        if (direction.equals(Direction.NORTH)) {
            if (grid.getBox(row - 1, col).getAgent() != null) return false;
        } else if (direction.equals(Direction.SOUTH)) {
            if (grid.getBox(row + 1, col).getAgent() != null) return false;
        } else if (direction.equals(Direction.WEST)) {
            if (grid.getBox(row, col - 1).getAgent() != null) return false;
        } else if (direction.equals(Direction.EAST)) {
            if (grid.getBox(row, col + 1).getAgent() != null) return false;
        }

        return true;
    }

    public boolean isStuck(Agent agent) {
        Grid grid = Game.getGrid();
        int gridMaxIndex = grid.getSize() - 1;

        int row = agent.getCurrent().getX();
        int col = agent.getCurrent().getY();

        boolean result = false;

        /*System.out.println(row);
        System.out.println(col);*/

        if (row == 0 && col == 0) { // isInTopLeftCorner
            boolean hasEastSouthNeighbours = (grid.getBox(row, col + 1).getAgent() != null
                    && grid.getBox(row + 1, col).getAgent() != null);

            if (hasEastSouthNeighbours) result = true;

        } else if (row == 0 && col == gridMaxIndex) { // isInTopRightCorner
            boolean hasWestSouthNeighbours = (grid.getBox(row, col - 1).getAgent() != null
                    && grid.getBox(row + 1, col).getAgent() != null);

            if (hasWestSouthNeighbours) result = true;

        } else if (row == gridMaxIndex && col == 0) { // isInBottomLeftCorner
            boolean hasEastNorthNeighbours = (grid.getBox(row, col + 1).getAgent() != null
                    && grid.getBox(row - 1, col).getAgent() != null);

            if (hasEastNorthNeighbours) result = true;

        } else if (row == gridMaxIndex && col == gridMaxIndex) { // isInBottomRightCorner
            boolean hasWestNorthNeighbours = (grid.getBox(row, col - 1).getAgent() != null
                    && grid.getBox(row - 1, col).getAgent() != null);

            if (hasWestNorthNeighbours) result = true;
        } else if (row == 0) { // isInTopBorder
            boolean hasEastWestSouthNeighbours = (
                grid.getBox(row, col + 1).getAgent() != null
                && grid.getBox(row, col - 1).getAgent() != null
                && grid.getBox(row + 1, col).getAgent() != null
            );

            if (hasEastWestSouthNeighbours) result = true;

        } else if (row == gridMaxIndex) { // isInBottomBorder
            boolean hasEastWestNorthNeighbours = (
                grid.getBox(row, col + 1).getAgent() != null
                && grid.getBox(row, col - 1).getAgent() != null
                && grid.getBox(row - 1, col).getAgent() != null
            );

            if (hasEastWestNorthNeighbours) result = true;

        } else if (col == 0) { // isInLeftBorder
            boolean hasEastNorthSouthNeighbours = (
                grid.getBox(row, col + 1).getAgent() != null
                && grid.getBox(row - 1, col).getAgent() != null
                && grid.getBox(row + 1, col).getAgent() != null
            );

            if (hasEastNorthSouthNeighbours) result = true;

        } else if (col == gridMaxIndex) { // isInRightBorder
            boolean hasWestNorthSouthNeighbours = (
                grid.getBox(row, col - 1).getAgent() != null
                && grid.getBox(row - 1, col).getAgent() != null
                && grid.getBox(row + 1, col).getAgent() != null
            );

            if (hasWestNorthSouthNeighbours) result = true;
        }

        return result;
    }

    @Override
    public void solve(Agent agent) {
        System.out.println("CognitiveStrategy");

        /*List<Agent> neighbours = agent.getNeighbours();
        GameUtils.print("neighbours", neighbours);

        if (!neighbours.isEmpty()) {
            Agent neighbour = neighbours.get(0);

            Mail mail = new Mail(agent, neighbour, Subject.REQUEST, Content.MOVE);
            MailBox.sendMailTo(mail, neighbour);

            PriorityQueue<Mail> neighbourMailBox = MailBox.getAgentMails(neighbour);
            GameUtils.print("neighbourMailBox", neighbourMailBox);
        }
*/
        // If agent is stuck (all neighbours are other agents)
            // BFS without obstacle avoidance
        // Else
            // BFS with obstacle avoidance

        // TO_CONTINUE

        List<Box> path = BFS.findPathWithoutObstaclesAvoidance(agent);
        List<Direction> directions = BFS.convertPathToDirections(path);

        agent.setPathDirections(directions);
        System.out.println(agent.getPathDirections());

        if (directions.size() != 0) {
            System.out.println(directions.get(0));
            move(agent, directions.get(0));
        }

        GameUtils.print("isStuck", isStuck(agent));
    }
}