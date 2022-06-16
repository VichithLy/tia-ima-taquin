package com.tia.models;

import com.tia.GameUtils;
import com.tia.enums.Letter;
import com.tia.strategies.Context;

import java.util.ArrayList;
import java.util.List;

public final class Game {
    private static int gridSize;
    private static Grid grid;
    private static List<Agent> agents;
    private static Context context;

    /**
     * Constructor of Game
     * @param gridSize
     * @param agentsCount
     * @param context
     */
    public static void init(int gridSize, int agentsCount, Context context) {
        setGridSize(gridSize);
        setGrid(new Grid(gridSize));
        setAgents(new ArrayList<>());
        setContext(context);

        initAgents(grid, agentsCount, context);
    }

    // Methods

    /**
     * Initialize grid with agents
     * @param grid
     * @param agentsCount
     */
    private static void initAgents(Grid grid, int agentsCount, Context context) {
        /*final int SIZE_GRID = grid.getSize();

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
            Agent agent = new Agent(Letter.getLetterByCode(i), current, destination, context);
            current.setAgent(agent);
            agents.add(agent);
        }*/

        testAgents();
    }

    public static void testAgents() {
        Box current = grid.getBox(1, 1); // TO CHANGE
        Box destination = grid.getBox(3, 4);
        Agent agent = new Agent(Letter.getLetterByCode(0), current, destination, context);

        current.setAgent(agent);
        agents.add(agent);

        Box current2 = grid.getBox(3, 4); // TO CHANGE
        Box destination2 = grid.getBox(1, 1);
        Agent agent2 = new Agent(Letter.getLetterByCode(1), current2, destination2, context);

        current2.setAgent(agent2);
        agents.add(agent2);
    }

    /**
     *
     * @param index
     * @return
     */
    public static Agent getAgent(int index) {
        return getAgents().get(index);
    }

    public static boolean isSolved() {
        List<Agent> solvedAgents = new ArrayList<>();

        for (Agent agent : agents) {
            if (agent.isArrived())
                solvedAgents.add(agent);
        }

        return (solvedAgents.size() == agents.size());
    }

    // Print

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
    public static void printGrid() {
        for (Box[] boxes : grid.getBoxes()) {
            for (Box box : boxes) {
                // System.out.print(box + " ");

                if (box.getAgent() == null) {
                    // System.out.print("0 ");
                    System.out.print(box + " ");
                } else {
                    System.out.print("  " + box.getAgent().getValue() + "   ");
                    // System.out.print(y.getAgent().toString() + " ");
                }
            }
            System.out.println();
        }
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

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        Game.context = context;
    }
}
