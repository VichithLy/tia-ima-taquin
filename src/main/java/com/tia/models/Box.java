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

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    /**
     * Check if the param Object is a Box
     * and has the same x and y as this Box.
     * @param o
     * @return true or false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Box box = (Box) o;
        return x == box.x && y == box.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
