package com.tia.models;

public class Box {
    private Agent agent;
    private boolean isSource;
    private boolean isDestination;
    private int x;
    private int y;

    public Box(int x, int y) {
        this.agent = null;
        this.x = x;
        this.y = y;
        this.isSource = false;
        this.isDestination = false;
    }

    public Agent getAgent() {
        return agent;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public void setIsSource(boolean isSource) {
        isSource = isSource;
    }

    public void setIsDestination(boolean isDestination) {
        isDestination = isDestination;
    }

    public boolean isSource() {
        return isSource;
    }

    public boolean isDestination() {
        return isDestination;
    }

    public boolean isEmpty() {
        return this.agent == null;
    }


}
