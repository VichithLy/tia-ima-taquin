package com.tia.strategies;

import com.tia.enums.Direction;
import com.tia.enums.Letter;
import com.tia.models.Agent;
import com.tia.models.Box;
import com.tia.models.Game;
import com.tia.models.Grid;

import java.util.LinkedList;
import java.util.Queue;

public class SimpleStrategy implements Strategy {
    @Override
    public void move(Agent agent, Direction direction) {

    }

    @Override
    public void solve(Agent agent) {
        System.out.println("SimpleStrategy");
    }

    /**
     * BFS: https://www.youtube.com/watch?v=KiCBXu4P-2Y
     * Queue: https://www.delftstack.com/fr/howto/java/enqueue-and-dequeue-java/
     *
     * @param agent
     */
    public int BFS(Agent agent) {
        // Global variables
        Grid grid = Game.getGrid();

        int rowsCount = Game.getGridSize();
        int colsCount = Game.getGridSize();

        Box source = agent.getSource();
        int sourceRow = source.getX();
        int sourceCol = source.getY();
        Box destination = agent.getDestination();

        Queue<Integer> rowQ = new LinkedList<>();
        Queue<Integer> colQ = new LinkedList<>();

        // Variables to track the number of steps taken
        int movesCount = 0;
        int nodesLeftInLayer = 1;
        int nodesInNextLayer = 0;

        // Variables used to track whether the 'E' character
        // ever gets reached during the BFS
        boolean reachedEnd = false;

        // Matrix of false values used to track whether
        // the node at position (i, j) has been visited
        boolean[][] visited = new boolean[rowsCount][colsCount];

        // Directions
        int[] rowDirections = new int[]{-1, +1, 0, 0};
        int[] colDirections = new int[]{0, 0, +1, -1};

        // ========== Solve

        rowQ.add(sourceRow);
        colQ.add(sourceCol);
        visited[sourceRow][sourceCol] = true;

        while (rowQ.size() > 0) {
            int row = rowQ.remove();
            int col = colQ.remove();

            /*System.out.println(grid.getBox(row, col) + " - "
                    + destination + " - "
                    + grid.getBox(row, col).equals(destination));*/

            if (grid.getBox(row, col).equals(destination)) {
                reachedEnd = true;
                break;
            }

            // ========== exploreNeighbours

            for (int i = 0; i < 4; i++) {
                int newRow = row + rowDirections[i];
                int newCol = col + colDirections[i];

                // Skip out of bounds locations
                if (newRow < 0 || newCol < 0) continue;
                if (newRow >= rowsCount || newCol >= colsCount) continue;

                // Skip visited locations or blocked cells
                if (visited[newRow][newCol]) continue;

                if (grid.getBox(newRow, newCol).getAgent() != null) continue;

                rowQ.add(newRow);
                colQ.add(newCol);
                visited[newRow][newCol] = true;
                nodesInNextLayer++;
            }

            nodesLeftInLayer--;

            if (nodesLeftInLayer == 0) {
                nodesLeftInLayer = nodesInNextLayer;
                nodesInNextLayer = 0;
                movesCount++;
            }
        }

        if (reachedEnd) return movesCount;

        return -1;
    }

}
