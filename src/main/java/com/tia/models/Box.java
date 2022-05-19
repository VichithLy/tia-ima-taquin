package com.tia.models;

public class Box {
    private Agent agent;
    private int x;
    private int y;

    public Box(int x, int y) {
        this.agent = null;
        this.x = x;
        this.y = y;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
