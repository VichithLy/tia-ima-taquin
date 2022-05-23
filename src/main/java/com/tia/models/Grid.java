package com.tia.models;

import com.tia.enums.Symbol;

public class Grid {
    private int rows;
    private int columns;

    private Box[][] boxes;

    public Grid(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;

        boxes = new Box[rows][columns];
        for (int i = 0; i < boxes.length; i++) {
            for (int j = 0; j < boxes[i].length; j++) {
                boxes[i][j] = new Box(i, j);
            }
        }
    }

    /**
     * TODO
     * @param agents
     */
    public void setAgentsAtRandBox(Agent[] agents) {

    }

    public Box getBox(int x, int y)  {
        return boxes[x][y];
    }

    public void printStatus() {
        // TODO print agents destinations
        for (Box[] x : boxes) {
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

    public Box[][] getBoxes() {
        return boxes;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }
}
