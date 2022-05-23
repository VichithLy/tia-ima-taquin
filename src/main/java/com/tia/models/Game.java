package com.tia.models;

import com.tia.enums.Symbol;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private final int size;
    private final Grid grid;
    private List<Agent> agents;

    public Game(int size, int agentsNumber) {
        this.size = size;
        this.grid = new Grid(size, size);
        this.agents = new ArrayList<>();
        initAgents(grid, agentsNumber);
    }

    private void initAgents(Grid grid, int agentsNumber) {
        Box[][] boxes = grid.getBoxes();

        /*boxes[0][3].setAgent(new Agent(Symbol.A, 0,3, 0, 1));
        boxes[0][2].setAgent(new Agent(Symbol.B, 0,2, 1, 1));
        boxes[1][2].setAgent(new Agent(Symbol.C, 1,2, 2, 1));
        boxes[2][3].setAgent(new Agent(Symbol.D, 2,3, 3, 1));*/

        for (int i = 0; i < agentsNumber; i++) {
            int sourceX = getRandomNumber(0, size - 1);
            int sourceY = getRandomNumber(0, size - 1);

            while (!grid.getBox(sourceX, sourceY).isEmpty()) {
                sourceX = getRandomNumber(0, size - 1);
                sourceY = getRandomNumber(0, size - 1);
            }

            Box startBox = grid.getBox(sourceX, sourceY);

            int destinationX = getRandomNumber(0, size - 1);
            int destinationY = getRandomNumber(0, size - 1);

            // TODO
            while (startBox.isDestination() && destinationX == sourceX && destinationY == sourceY) {
                destinationX = getRandomNumber(0, size - 1);
                destinationY = getRandomNumber(0, size - 1);
            }

            Agent agent =  new Agent(Symbol.getSymbolByCode(i), sourceX, sourceY, destinationX, destinationY);
            boxes[sourceX][sourceY].setAgent(agent);
            boxes[sourceX][sourceY].setIsSource(true);
            boxes[destinationX][destinationY].setIsSource(true);
            agents.add(agent);
        }
    }

    // TODO
    private boolean boxIsDestination(Box box) {
        if (box.getAgent() == null)
            return false;

        System.out.println(box.getAgent().getDestinationX());
        System.out.println(box.getAgent().getDestinationY());

        return (box.getAgent().getDestinationX() == box.getX() &&
                box.getAgent().getDestinationY() == box.getY());
    }

    public void printStatus() {
        // TODO print agents destinations
        for (Box[] x : grid.getBoxes()) {
            for (Box y : x) {
                if (y.getAgent() == null) {
                    System.out.print("0 ");
                } else if (y.isDestination()) {
                    System.out.println("x"+ y.getAgent().getSymbol() + " ");
                } else {
                    System.out.print(y.getAgent().getSymbol() + " ");
                }

            }
            System.out.println();
        }

        /*else if (boxIsDestination(y)) {
            System.out.println("x"+ y.getAgent().getSymbol() + " ");
        }*/
    }

    public void printAgents() {
        for (Agent agent : agents) {
            System.out.println(agent.toString());
        }
    }

    private int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public Grid getGrid() {
        return grid;
    }
}
