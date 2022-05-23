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

    public boolean isEmpty() {
        return this.agent == null;
    }

    public boolean isDestination() {
        if (agent == null)
            return false;

        return (agent.getDestinationX() == x &&
                agent.getDestinationY() == y);
    }
}
