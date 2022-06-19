package com.tia.models;

import com.tia.algorithms.Path;
import com.tia.enums.Direction;
import com.tia.enums.Letter;
import com.tia.strategies.Context;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class Agent implements Runnable {
    private Letter value;
    private Box source;
    private Box destination;
    private Box current;
    private List<Direction> pathDirections;
    private int priority;
    private Context context;
    private CountDownLatch latch;

    public Agent(Letter value, Box current, Box destination, Context context) {
        this.value = value;
        this.destination = destination;
        this.source = current;
        this.current = current;
        this.pathDirections = new ArrayList<>();
        this.priority = value.getCode();
        this.context = context;
    }

    // Getters & Setters

    public Letter getValue() {
        return value;
    }

    public Box getDestination() {
        return destination;
    }

    public Box getCurrent() {
        return current;
    }

    public synchronized void setCurrent(Box current) {
        this.current = current;
    }

    public void setPathDirections(List<Direction> pathDirections) {
        this.pathDirections = pathDirections;
    }

    public int getPriority() {
        return priority;
    }

    public void setLatch(CountDownLatch latch) {
        this.latch = latch;
    }

    // Methods

    public boolean isArrived() {
        return (current.equals(destination));
    }

    /**
     * @return a list of agents or null if no agents around
     */
    public List<Agent> getNeighbours() {
        Grid grid = Game.getGrid();
        List<Agent> neighbours = new ArrayList<>();

        int[] rowDirections = new int[]{-1, 1, 0, 0};
        int[] colDirections = new int[]{0, 0, 1, -1};

        for (int i = 0; i < 4; i++) {
            int nextRow = this.current.getX() + rowDirections[i];
            int nextCol = this.current.getY() + colDirections[i];

            if (nextRow < 0 || nextCol < 0) continue;
            if (nextRow >= Game.getGridSize() || nextCol >= Game.getGridSize()) continue;

            Agent agent = grid.getBox(nextRow, nextCol).getAgent();
            if (agent != null) neighbours.add(agent);
        }

        return neighbours;
    }

    /**
     * @return an agent
     */
    public Agent getRandomNeighbour() {
        List<Agent> neighbours = getNeighbours();
        if (!neighbours.isEmpty()) {
            Random rand = new Random();
            return neighbours.get(rand.nextInt(neighbours.size()));
        }

        return null;
    }

    /**
     * @param agent
     * @return an agent, or null if no agents around
     */
    public Agent getRandomNeighbourExceptOne(Agent agent) {
        List<Agent> neighbours = getNeighbours();
        if (!neighbours.isEmpty()) {
            Random rand = new Random();
            neighbours.remove(agent);
            return neighbours.get(rand.nextInt(neighbours.size()));
        }

        return null;
    }

    /**
     * Get the neighbor in the box the agent wants to go.
     *
     * @param direction
     * @return an agent, or null if no neighbours
     */
    public Agent getNeighbour(Direction direction) {
        Grid grid = Game.getGrid();
        int row = this.getCurrent().getX();
        int col = this.getCurrent().getY();

        Agent neighbour = null;

        if (direction.equals(Direction.NORTH)) {
            neighbour = grid.getBox(row - 1, col).getAgent();
        } else if (direction.equals(Direction.SOUTH)) {
            neighbour = grid.getBox(row + 1, col).getAgent();
        } else if (direction.equals(Direction.WEST)) {
            neighbour = grid.getBox(row, col - 1).getAgent();
        } else if (direction.equals(Direction.EAST)) {
            neighbour = grid.getBox(row, col + 1).getAgent();
        }

        return neighbour;
    }

    /**
     * Get an agent of minimum priority around.
     *
     * @param one
     * @return an agent, or null if no neighbour
     */
    public Agent getMinPriorityNeighbourAgentExceptOne(Agent one) {
        List<Agent> neighbours = getNeighbours();

        if (neighbours.contains(one))
            neighbours.remove(one);

        try {
            Agent agent = neighbours.stream()
                    .min(Comparator.comparingInt(Agent::getPriority))
                    .get();

            return agent;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @return a list of boxes
     */
    public List<Box> getBoxNeighbours() {
        Grid grid = Game.getGrid();
        List<Box> neighbours = new ArrayList<>();

        int[] rowDirections = new int[]{-1, 1, 0, 0};
        int[] colDirections = new int[]{0, 0, 1, -1};

        for (int i = 0; i < 4; i++) {
            int nextRow = this.current.getX() + rowDirections[i];
            int nextCol = this.current.getY() + colDirections[i];

            if (nextRow < 0 || nextCol < 0) continue;
            if (nextRow >= Game.getGridSize() || nextCol >= Game.getGridSize()) continue;

            neighbours.add(grid.getBox(nextRow, nextCol));
        }
        return neighbours;
    }

    /**
     * @return a list of free boxes (with no agent in it)
     */
    public List<Box> getFreeBoxNeighbours() {
        Grid grid = Game.getGrid();
        List<Box> neighbours = new ArrayList<>();

        int[] rowDirections = new int[]{-1, 1, 0, 0};
        int[] colDirections = new int[]{0, 0, 1, -1};

        for (int i = 0; i < 4; i++) {
            int nextRow = this.current.getX() + rowDirections[i];
            int nextCol = this.current.getY() + colDirections[i];

            if (nextRow < 0 || nextCol < 0) continue;
            if (nextRow >= Game.getGridSize() || nextCol >= Game.getGridSize()) continue;

            Agent agent = grid.getBox(nextRow, nextCol).getAgent();
            if (agent == null)
                neighbours.add(grid.getBox(nextRow, nextCol));
        }

        return neighbours;
    }

    /**
     * @return a random free direction (with no agent)
     */
    public Direction getRandomFreeDirection() {
        List<Box> boxes = getFreeBoxNeighbours();

        if (!boxes.isEmpty()) {
            Random rand = new Random();
            Box randBox = boxes.get(rand.nextInt(boxes.size()));

            List<Box> boxesToDirection = new ArrayList<>();
            boxesToDirection.add(current);
            boxesToDirection.add(randBox);

            return Path.convertToDirections(boxesToDirection).get(0);
        }

        return null;
    }

    /**
     * @return true if agent is stuck (it cannot move because there are agents around), else false
     */
    public boolean isStuck() {
        Grid grid = Game.getGrid();
        int gridMaxIndex = grid.getSize() - 1;

        int row = getCurrent().getX();
        int col = getCurrent().getY();

        boolean result = false;

        if ((row > 0 && col > 0) && (row < gridMaxIndex && col < gridMaxIndex)) { // isInsideBoard
            if (getNeighbours().size() == 4) result = true;
        } else if (
                (row == 0 && col == 0) // isInTopRightCorner
                        || (row == 0 && col == gridMaxIndex) // isInBottomLeftCorner
                        || (row == gridMaxIndex && col == 0) // isInTopBorder
                        || (row == gridMaxIndex && col == gridMaxIndex) // isInBottomRightCorner
        ) {
            if (getNeighbours().size() == 2) result = true;
        } else if (
                (row == 0) //isInTopBorder
                        || (row == gridMaxIndex) // isInBottomBorder
                        || (col == 0) // isInLeftBorder
                        || (col == gridMaxIndex) // isInRightBorder
        ) {
            if (getNeighbours().size() == 3) result = true;
        }

        return result;
    }

    /**
     * Solving strategy
     */
    public synchronized void solve() {
        context.executeStrategy(this);
    }

    @Override
    public void run() {
        solve();
        this.latch.countDown();
    }

    @Override
    public String toString() {
        return "Agent{" +
                "value=" + value +
                ", current=" + current.toString() +
                ", destination=" + destination.toString() +
                '}';
    }
}
