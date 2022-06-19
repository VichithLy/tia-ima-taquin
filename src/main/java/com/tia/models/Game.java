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
     * Initialize the game with all params.
     *
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
     * Initialize the grid with agents.
     *
     * @param grid
     * @param agentsCount
     */
    private static void initAgents(Grid grid, int agentsCount, Context context) {
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
            Agent agent = new Agent(Letter.getLetterByCode(i), current, destination, context);
            current.setAgent(agent);
            agents.add(agent);
        }

//        testCases();
    }

    public static void testCases() {
        // CASE 1
        addAgent(0, 0, 0, 0, 2);
        addAgent(1, 0, 1, 0, 1);
        addAgent(2, 1, 0, 1, 0);

        // CASE 2
//        addAgent(0,0,4, 0, 2);
//        addAgent(1,0,3, 0, 3);
//        addAgent(2,1,4, 1, 4);

        // CASE 3
//        addAgent(0,4,0, 0, 2);
//        addAgent(1,3,0, 3, 0);
//        addAgent(2,4,1, 4, 1);

        // CASE 4
//        addAgent(0,4,4, 0, 2);
//        addAgent(1,4,3, 4, 3);
//        addAgent(2,3,4, 3, 4);

        // CASE 5
//        addAgent(0,2,0, 0, 0);
//        addAgent(1,1,0, 1, 0);
//        addAgent(2,2,1, 2, 1);
//        addAgent(3,3,0, 3, 0);

        // CASE 6
//        addAgent(0,2,4, 0, 0);
//        addAgent(1,2,3, 2, 3);
//        addAgent(2,1,4, 1, 4);
//        addAgent(3,3,4, 3, 4);

        // CASE 7
//        addAgent(0,0,2, 0, 0);
//        addAgent(1,0,1, 0, 1);
//        addAgent(2,0,3, 0, 3);
//        addAgent(3,1,2, 1, 2);

        // CASE 8
//        addAgent(0,4,2, 4, 0);
//        addAgent(1,4,1, 4, 1);
//        addAgent(2,4,3, 4, 3);
//        addAgent(3,3,2, 3, 2);

        // CASE 9
//        addAgent(0,2,2, 3, 4);
//        addAgent(1,2,3, 2, 3);
//        addAgent(2,2,1, 2, 1);
//        addAgent(3,1,2, 1, 2);
//        addAgent(4,3,2, 3, 2);
//
//        addAgent(5,3,1, 3, 1);
//        addAgent(6,3,3, 3, 3);
//        addAgent(7,4,2, 4, 2);

        // CASE 10
//        addAgent(0,3,3, 3, 4);
//        addAgent(1,3,4, 3, 3);

        // CASE 11
//        addAgent(0,3,4, 3, 4);
//        addAgent(1,4,3, 4, 0);
//        addAgent(2,3,3, 4, 3);
//        addAgent(3,4,2, 4, 2);
//        addAgent(4,2,3, 2, 3);

//        addAgent(0,3,4, 3, 3);
//        addAgent(1,3,3, 3, 4);
//        addAgent(2,4,3, 4, 4);
//        addAgent(3,2,4, 2, 4);

//        addAgent(0, 0, 0, 0, 4);
    }

    /**
     * @param index
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     */
    public static void addAgent(int index, int x1, int y1, int x2, int y2) {
        Box current = grid.getBox(x1, y1); // TO CHANGE
        Box destination = grid.getBox(x2, y2);
        Agent agent = new Agent(Letter.getLetterByCode(index), current, destination, context);

        current.setAgent(agent);
        agents.add(agent);
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
     * Print agents.
     */
    public static void printAgents() {
        for (Agent agent : agents) {
            System.out.println(agent.toString());
        }
    }

    /**
     * Print grid's status.
     */
    public static void printGrid() {
        System.out.println("\n==========\n");
        for (Box[] boxes : grid.getBoxes()) {
            for (Box box : boxes) {
                if (box.getAgent() == null) {
                    System.out.print(box + " ");
                } else {
                    System.out.print("  " + box.getAgent().getValue() + "   ");
                }
            }
            System.out.println();
        }
        System.out.println("\n==========\n");
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

    public static void setContext(Context context) {
        Game.context = context;
    }
}
