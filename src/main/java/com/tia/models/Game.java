package com.tia.models;

import com.tia.GameUtils;
import com.tia.enums.Mode;
import com.tia.enums.Letter;

import java.util.ArrayList;
import java.util.List;

public final class Game {
    private static int gridSize;
    private static Grid grid;
    private static List<Agent> agents;
    private static Mode mode;

    /**
     * Constructor of Game
     * @param gridSize
     * @param agentsCount
     * @param mode
     */
    public static void init(int gridSize, int agentsCount, Mode mode) {
        setGridSize(gridSize);
        setGrid(new Grid(gridSize));
        setAgents(new ArrayList<>());
        setMode(mode);

        initAgents(grid, agentsCount);
    }

    /**
     * Initialize grid with agents
     * @param grid
     * @param agentsCount
     */
    private static void initAgents(Grid grid, int agentsCount) {
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
            Box current = grid.getBox(randomCurrentPair[0], randomCurrentPair[1]);
            currentPairs.remove(randomCurrentPair);

            // agent's destination position
            int[] randomDestinationPair = GameUtils.getRandomItemFromList(destinationPairs);
            Box destination = grid.getBox(randomDestinationPair[0], randomDestinationPair[1]);
            destinationPairs.remove(randomDestinationPair);

            // create agent with current and destination positions in grid
            Agent agent = new Agent(Letter.getLetterByCode(i), current, destination);
            current.setAgent(agent);
            agents.add(agent);

            // put agent in grid's box
            grid.getBox(current.getX(), current.getY()).setAgent(agent);
        }
    }

    /**
     * Print agents objets in current game
     */
    public static void printAgents() {
        for (Agent agent : agents) {
            System.out.println(agent.toString());
        }
    }

    /**
     * Print grid's status
     */
    public static void printStatus() {
        System.out.println("==============");
        for (Box[] boxes : grid.getBoxes()) {
            for (Box box : boxes) {
                // System.out.print(box + " ");

                if (box.getAgent() == null) {
                    // System.out.print("0 ");
                    System.out.print(box + " ");
                } else {
                    System.out.print(box.getAgent().getValue() + "" + box + " ");
                    // System.out.print(y.getAgent().toString() + " ");
                }
            }
            System.out.println();
        }
        System.out.println("==============");
    }

    // Getters & Setters

    public static int getGridSize() {
        return gridSize;
    }

    public static void setGridSize(int size) {
        Game.gridSize = size;
    }

    public static Grid getGrid() {
        return grid;
    }

    public static void setGrid(Grid grid) {
        Game.grid = grid;
    }

    public static List<Agent> getAgents() {
        return agents;
    }

    public static void setAgents(List<Agent> agents) {
        Game.agents = agents;
    }

    public static Mode getMode() {
        return mode;
    }

    public static void setMode(Mode mode) {
        Game.mode = mode;
    }
}
