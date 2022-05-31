package com.tia.models;

import com.tia.GameUtils;
import com.tia.enums.Mode;
import com.tia.enums.Symbol;

import java.util.ArrayList;
import java.util.List;

public class Game {
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
        final int SIZE_GRID = grid.getSize();

        int[] gridCoords = new int[SIZE_GRID];
        for (int i = 0; i < SIZE_GRID; i++) {
            gridCoords[i] = i;
        }

        List<int[]> currentPairs = GameUtils.getAllPossiblePairs(gridCoords);
        List<int[]> destinationPairs = GameUtils.getAllPossiblePairs(gridCoords);

        for (int i = 0; i < agentsCount; i++) {
            // agent's current position
            int[] randomCurrentPair = GameUtils.getRandomItemFromList(currentPairs);
            Box current = new Box(randomCurrentPair[0], randomCurrentPair[1]);
            currentPairs.remove(randomCurrentPair);
            // agent's destination position
            int[] randomDestinationPair = GameUtils.getRandomItemFromList(destinationPairs);
            Box destination = new Box(randomDestinationPair[0], randomDestinationPair[1]);
            destinationPairs.remove(randomDestinationPair);
            // create agent with current and destination positions in grid
            Agent agent = new Agent(Symbol.getSymbolByCode(i), current, destination);
            agents.add(agent);
            // put agent in grid's box
            grid.getBox(current.getX(), current.getY()).setAgent(agent);
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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Grid getGrid() {
        return grid;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public List<Agent> getAgents() {
        return agents;
    }

    public void setAgents(List<Agent> agents) {
        this.agents = agents;
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public void printAgents() {
        for (Agent agent : agents) {
            System.out.println(agent.toString());
        }
    }
}
