package com.tia.models;

public class Grid {
    private int size;
    private Box[][] boxes;

    public Grid(int size) {
        this.size = size;

        // WARNING: in 2D arrays, rows come first (y,x)
        boxes = new Box[size][size];
        for (int column = 0; column < boxes.length; column++) {
            for (int row = 0; row < boxes[column].length; row++) {
                boxes[column][row] = new Box(column, row);
            }
        }
    }

    // Getters & Setters

    public synchronized Box getBox(int x, int y) {
        return boxes[x][y];
    }

    public Box[][] getBoxes() {
        return boxes;
    }

    public int getSize() {
        return size;
    }
}
