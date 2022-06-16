package com.tia.strategies;

import com.tia.GameUtils;
import com.tia.algorithms.BFS;
import com.tia.enums.Direction;
import com.tia.models.Agent;
import com.tia.models.Box;
import com.tia.models.Game;
import com.tia.models.Grid;

import java.util.List;

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
            return (row - 1) >= 0;
        } else if (direction.equals(Direction.SOUTH)) {
            return (row + 1) >= 0;
        } else if (direction.equals(Direction.WEST)) {
            return (col - 1) >= 0;
        } else if (direction.equals(Direction.EAST)) {
            return (col + 1) >= 0;
        }

        return true;
    }

    private boolean isFree(Agent agent, Direction direction) {
        Grid grid = Game.getGrid();

        int row = agent.getCurrent().getX();
        int col = agent.getCurrent().getY();

        if (direction.equals(Direction.NORTH)) {
            return grid.getBox(row - 1, col).getAgent() == null;
        } else if (direction.equals(Direction.SOUTH)) {
            return grid.getBox(row + 1, col).getAgent() == null;
        } else if (direction.equals(Direction.WEST)) {
            return grid.getBox(row, col - 1).getAgent() == null;
        } else if (direction.equals(Direction.EAST)) {
            return grid.getBox(row, col + 1).getAgent() == null;
        }

        return true;
    }

    public boolean isStuck(Agent agent) {
        Grid grid = Game.getGrid();
        int gridMaxIndex = grid.getSize() - 1;

        int row = agent.getCurrent().getX();
        int col = agent.getCurrent().getY();

        boolean result = false;

        if ((row > 0 && col > 0) && (row < gridMaxIndex && col < gridMaxIndex)) { // isInsideBoard
            if (agent.getNeighbours().size() == 4) result = true;
        } else if (
            (row == 0 && col == 0) // isInTopRightCorner
            || (row == 0 && col == gridMaxIndex) // isInBottomLeftCorner
            || (row == gridMaxIndex && col == 0) // isInTopBorder
            || (row == gridMaxIndex && col == gridMaxIndex) // isInBottomRightCorner
        ) {
            System.out.println(agent.getNeighbours().size());
            if (agent.getNeighbours().size() == 2) result = true;
        }  else if (
            (row == 0) //isInTopBorder
            || (row == gridMaxIndex) // isInBottomBorder
            || (col == 0) // isInLeftBorder
            || (col == gridMaxIndex) // isInRightBorder
        ) {
            if (agent.getNeighbours().size() == 3) result = true;
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