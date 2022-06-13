package com.tia.strategies;

import com.tia.enums.Direction;
import com.tia.models.Agent;

public class NaiveStrategy implements Strategy {

    @Override
    public void solve(Agent agent) {
        System.out.println("NaiveStrategy Solving...");

        int currentCol = agent.getCurrent().getY();
        int currentRow = agent.getCurrent().getX();
        int destinationCol = agent.getDestination().getY();
        int destinationRow = agent.getDestination().getX();


        // Go to right column first
        // then go to right row

        // If current column != destination column
        if (currentRow == destinationRow) {
            System.out.println("Row OK");

            if (currentCol == destinationCol) {
                System.out.println("Column OK");
            } else if (currentCol > destinationCol) {
                agent.move(Direction.WEST);
            } else if (currentCol < destinationCol) {
                agent.move(Direction.EAST);
            }

        } else if (currentRow > destinationRow) {
            agent.move(Direction.NORTH);
        } else if (currentRow < destinationRow) {
            agent.move(Direction.SOUTH);
        }
    }
}
