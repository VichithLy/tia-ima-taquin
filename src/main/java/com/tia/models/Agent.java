package com.tia.models;

import com.tia.enums.Direction;
import com.tia.enums.Letter;
import com.tia.strategies.Context;
import com.tia.strategies.NaiveStrategy;

import java.util.ArrayList;
import java.util.List;
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

    public void setValue(Letter value) {
        this.value = value;
    }

    public Box getSource() {
        return source;
    }

    public void setSource(Box source) {
        this.source = source;
    }

    public Box getDestination() {
        return destination;
    }

    public void setDestination(Box destination) {
        this.destination = destination;
    }

    public Box getCurrent() {
        return current;
    }

    public void setCurrent(Box current) {
        this.current = current;
    }

    public List<Direction> getPathDirections() {
        return pathDirections;
    }

    public void setPathDirections(List<Direction> pathDirections) {
        this.pathDirections = pathDirections;
    }

    public CountDownLatch getLatch() {
        return latch;
    }

    public void setLatch(CountDownLatch latch) {
        this.latch = latch;
    }

    // Methods

    public boolean isArrived() {
        return (current.equals(destination));
    }

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

    public void solve() {
        context.executeStrategy(this);
    }

    @Override
    public String toString() {
        return "Agent{" +
                "value=" + value +
                ", current=" + current.toString() +
                ", destination=" + destination.toString() +
                '}';
    }

    @Override
    public void run() {
        System.out.println("=====");
        System.out.println("Agent running...");

        solve();

        this.latch.countDown();
    }
}
