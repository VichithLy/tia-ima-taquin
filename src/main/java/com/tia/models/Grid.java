package com.tia.models;

public class Grid {
    private int size;

    private Box[][] boxes;

    public Grid(int size) {
        this.size = size;

        boxes = new Box[size][size];
        for (int i = 0; i < boxes.length; i++) {
            for (int j = 0; j < boxes[i].length; j++) {
                boxes[i][j] = new Box(i, j);
            }
        }
    }

    public Box getBox(int x, int y)  {
        return boxes[x][y];
    }

    public Box[][] getBoxes() {
        return boxes;
    }

    public int getSize() {
        return size;
    }
}
