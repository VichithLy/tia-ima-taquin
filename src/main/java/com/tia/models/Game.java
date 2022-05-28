package com.tia.models;

import com.tia.enums.Mode;
import com.tia.enums.Symbol;
import javafx.concurrent.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game extends Task<Void> {
    private int size;
    private Grid grid;
    private List<Agent> agents;
    private Mode mode;

    public Game(int size, int agentsCount, Mode mode) {
        this.size = size;
        this.grid = new Grid(size);
        this.agents = new ArrayList<>();
        this.mode = mode;

        initAgents(grid, agentsCount);
    }

    private void print(Object name, Object var) {
        System.out.println(name + ": " + var);
    }

    private void initAgents(Grid grid, int agentsCount) {
        List<Box> sources = new ArrayList<>();
        List<Box> destinations = new ArrayList<>();

        for (int i = 0; i < agentsCount; i++) {
            Random random = new Random();
            int sourceX = random.nextInt(grid.getSize());
            int sourceY = random.nextInt(grid.getSize());
            Box source = new Box(sourceX, sourceY);

            while (sources.contains(source)) {
                sourceX = random.nextInt(grid.getSize());
                sourceY = random.nextInt(grid.getSize());
                source = new Box(sourceX, sourceY);
            }
            sources.add(source);

            int destinationX = random.nextInt(grid.getSize());
            int destinationY = random.nextInt(grid.getSize());
            Box destination = new Box(destinationX, destinationY);

            while (sources.contains(destination) || destinations.contains(destination)) {
                destinationX = random.nextInt(grid.getSize());
                destinationY = random.nextInt(grid.getSize());
                destination = new Box(destinationX, destinationY);
            }
            destinations.add(destination);

            Agent agent = new Agent(Symbol.getSymbolByCode(i), source, destination, source);
            agents.add(agent);
            grid.getBox(sourceX, sourceY).setAgent(agent);
        }

    }

    public void printStatus() {
        // TODO print agents destinations
        for (Box[] x : grid.getBoxes()) {
            for (Box y : x) {
                if (y.getAgent() == null) {
                    System.out.print("0 ");
                } else {
                    System.out.print(y.getAgent().getSymbol() + " ");
                }
            }
            System.out.println();
        }
    }

    public void printAgents() {
        for (Agent agent : agents) {
            System.out.println(agent.toString());
        }
    }

    public Grid getGrid() {
        return grid;
    }

    public List<Agent> getAgents() {
        return agents;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    @Override
    protected Void call() throws Exception {
        return null;
    }
}
