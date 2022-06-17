package com.tia.models;

import java.util.Objects;

public class Box {
    private int x;
    private int y;
    private Agent agent;

    public Box(int x, int y) {
        this.x = x;
        this.y = y;
        this.agent = null;
    }

    // Getters & Setters

    public synchronized Agent getAgent() {
        return agent;
    }

    public synchronized void setAgent(Agent agent) {
        this.agent = agent;
    }

    public synchronized int getX() {
        return x;
    }

    public synchronized int getY() {
        return y;
    }

    // Functions

    /**
     * Check if the param Object is a Box
     * and has the same x and y as this Box.
     * @param o
     * @return true or false
     */
    @Override
    public synchronized boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Box box = (Box) o;
        return x == box.x && y == box.y;
    }

    @Override
    public synchronized int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public synchronized String toString() {
        return "(" + x + "," + y + ")";
    }
}
