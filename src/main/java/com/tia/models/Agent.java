package com.tia.models;

import com.tia.GameUtils;
import com.tia.enums.Direction;
import com.tia.enums.Letter;

public class Agent {
    private Letter value;
    private Box source;
    private Box destination;
    private Box current;

    public Agent(Letter value, Box current, Box destination) {
        this.value = value;
        this.destination = destination;
        this.source = current;
        this.current = current;
    }

    // Methods

    public void move(Direction direction) {
        Grid grid = Game.getGrid();
        Box oldBox = grid.getBox(current.getX(), current.getY());

        if (!isArrived()) {
            switch (direction) {
                case NORTH:
                    if (canMove(Direction.NORTH)) {
                        System.out.println("Move NORTH");
                        // Agent
                        setCurrent(grid.getBox(current.getX() - 1, current.getY()));
                        // Grid
                        Box newBox = grid.getBox(current.getX(), current.getY());
                        newBox.setAgent(oldBox.getAgent());
                        oldBox.setAgent(null);
                    }
                    break;

                case SOUTH:
                    if (canMove(Direction.SOUTH)) {
                        System.out.println("Move SOUTH");
                        // Agent
                        setCurrent(grid.getBox(current.getX() + 1, current.getY()));
                        // Grid
                        Box newBox = grid.getBox(current.getX(), current.getY());
                        newBox.setAgent(oldBox.getAgent());
                        oldBox.setAgent(null);
                    }
                    break;

                case WEST:
                    if (canMove(Direction.WEST)) {
                        System.out.println("Move WEST");
                        // Agent
                        setCurrent(grid.getBox(current.getX(), current.getY() - 1));
                        // Grid
                        Box newBox = grid.getBox(current.getX(), current.getY());
                        newBox.setAgent(oldBox.getAgent());
                        oldBox.setAgent(null);
                    }
                    break;

                case EAST:
                    if (canMove(Direction.EAST)) {
                        System.out.println("Move EAST");
                        // Agent
                        setCurrent(grid.getBox(current.getX(), current.getY() + 1));
                        // Grid
                        Box newBox = grid.getBox(current.getX(), current.getY());
                        newBox.setAgent(oldBox.getAgent());
                        oldBox.setAgent(null);
                    }
                    break;
            }
        }
    }

    private boolean canMove(Direction direction) {
        Boolean value = false;
        int gridMaxIndex = Game.getGrid().getSize() - 1;

       if (direction.equals(Direction.NORTH)) {
           value = (current.getX() > 0);
           GameUtils.print("NORTH", value);
       } else if (direction.equals(Direction.SOUTH)) {
           value = (current.getX() < gridMaxIndex);
           GameUtils.print("SOUTH", value);
       } else if (direction.equals(Direction.WEST)) {
           value = (current.getY() > 0);
           GameUtils.print("WEST", value);
       } else if (direction.equals(Direction.EAST)) {
           value = (current.getY() < gridMaxIndex);
           GameUtils.print("EAST", value);
       }

        return value;
    }

    public boolean isArrived() {
        GameUtils.print("isArrived", (current.equals(destination)));
        return (current.equals(destination));
    }

    // Getters & Setters

    public Letter getValue() {
        return value;
    }

    public Box getSource() {
        return source;
    }

    public Box getDestination() {
        return destination;
    }

    public Box getCurrent() {
        return current;
    }

    public void setValue(Letter value) {
        this.value = value;
    }

    public void setSource(Box source) {
        this.source = source;
    }

    public void setDestination(Box destination) {
        this.destination = destination;
    }

    public void setCurrent(Box current) {
        this.current = current;
    }

    // Functions

    @Override
    public String toString() {
        return "Agent{" +
                "value=" + value +
                ", current=" + current.toString() +
                ", destination=" + destination.toString() +
                '}';
    }
}
