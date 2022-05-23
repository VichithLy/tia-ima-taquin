package com.tia.models;

import com.tia.enums.Symbol;

public class Game {
    private final int size;
    private final Grid grid;
    private Agent[] agents;

    public Game(int size, int agentsNumber) {
        this.size = size;
        this.grid = new Grid(size, size);
        initAgents(grid, agentsNumber);
    }

    private void initAgents(Grid grid, int agentsNumber) {
        Box[][] boxes = grid.getBoxes();

        /*boxes[0][3].setAgent(new Agent(Symbol.A, 0,3, 0, 1));
        boxes[0][2].setAgent(new Agent(Symbol.B, 0,2, 1, 1));
        boxes[1][2].setAgent(new Agent(Symbol.C, 1,2, 2, 1));
        boxes[2][3].setAgent(new Agent(Symbol.D, 2,3, 3, 1));*/

        for (int i = 0; i < agentsNumber; i++) {
            int startX = getRandomNumber(0, size - 1);
            int startY = getRandomNumber(0, size - 1);

            while (!grid.getBox(startX, startY).isEmpty()) {
                startX = getRandomNumber(0, size - 1);
                startY = getRandomNumber(0, size - 1);
            }

            int destinationX = getRandomNumber(0, size - 1);
            int destinationY = getRandomNumber(0, size - 1);

            while (grid.getBox(startX, startY).isDestination()) {
                destinationX = getRandomNumber(0, size - 1);
                destinationY = getRandomNumber(0, size - 1);
            }

            boxes[startX][startY].setAgent(
                    new Agent(
                            Symbol.getSymbolByCode(i),
                            startX, startX,
                            destinationX, destinationY
                    )
            );
        }
    }

    private int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public Grid getGrid() {
        return grid;
    }
}
