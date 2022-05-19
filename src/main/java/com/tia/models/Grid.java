package com.tia.models;

import com.tia.enums.Symbol;

import java.util.Arrays;

public class Grid {
    private int rows;
    private int columns;

    private Box[][] boxes;

    public Grid(int rows, int columns, int agentsNumber) {
        this.rows = rows;
        this.columns = columns;

        boxes = new Box[5][5];
        for (int i = 0; i < boxes.length; i++) {
            for (int j = 0; j < boxes[i].length; j++) {
                boxes[i][j] = new Box(i, j);
            }
        }

        boxes[0][3].setAgent(new Agent(Symbol.A, 0,3, 0, 1));
        boxes[0][2].setAgent(new Agent(Symbol.B, 0,2, 1, 1));
        boxes[1][2].setAgent(new Agent(Symbol.C, 1,2, 2, 1));
        boxes[2][3].setAgent(new Agent(Symbol.D, 2,3, 3, 1));

        // Initialize agents
    }

    public void printStatus() {
        for (Box[] x : boxes)
        {
            for (Box y : x)
            {
                if (y.getAgent() == null) {
                    System.out.print("0 ");
                } else {
                    System.out.print(y.getAgent().getSymbol() + " ");
                }
            }
            System.out.println();
        }
    }
}
