package com.tia.strategies;

import com.tia.enums.Direction;
import com.tia.enums.Letter;
import com.tia.models.Agent;
import com.tia.models.Box;
import com.tia.models.Game;

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
     * BFS
     * <p>
     * https://www.youtube.com/watch?v=oDqjPvD54Ss
     * https://www.youtube.com/watch?v=KiCBXu4P-2Y
     * https://www.geeksforgeeks.org/find-whether-path-two-cells-matrix/
     *
     * @param agent
     */
    public boolean findPath(Agent agent) {
        Box[][] boxes = Game.getGrid().getBoxes();

        int rowsCount = Game.getGridSize();
        int columnsCount = Game.getGridSize();
        System.out.println("rowsCount=" + rowsCount);

        Box source = agent.getSource(); // switch to current
        Box destination = agent.getDestination();

        // 1) BFS Queue
        Queue<Box> q = new LinkedList<>();

        // 2) Scan the matrix
        for (int i = 0; i < rowsCount; ++i) {
            for (int j = 0; j < columnsCount; ++j) {
                // if there exists a Box in the Board such
                // that its the source then push it to q
                if (boxes[i][j].equals(source)) {
                    q.add(boxes[i][j]);
                    System.out.println("q=" + q);
                    break;
                }
            }
        }

        // 3) Run BFS algorithm with q
        while (q.size() != 0) {
            Box x = q.peek();
            q.remove();
            int i = x.getX();
            int j = x.getY();

            // skipping cells which are not valid.
            // if outside the matrix bounds
            if (i < 0 || i >= rowsCount || j < 0 || j >= columnsCount) {
                continue;
            }

            // if they are other agents (value is not null).

            if (boxes[i][j].getAgent() != null || boxes[i][j].getAgent().getValue() != Letter.NULL) {
                continue;
            }

            // 3.1) if in the BFS algorithm process there was a
            // vertex x=(i,j) such that M[i][j] is 2 stop and
            // return true

            if (boxes[i][j].equals(destination)) {
                System.out.println("q=" + q.size());
                return true;
            }

            // marking as wall upon successful visitation

            // boxes[i][j].setAgent(new Agent());

            // pushing to queue u=(i,j+1),u=(i,j-1)
            // u=(i+1,j),u=(i-1,j)
            for (int k = -1; k <= 1; k += 2) {
                q.add(new Box(i + k, j));
                q.add(new Box(i, j + k));
            }

        }

        // BFS algorithm terminated without returning true
        // then there was no element M[i][j] which is 2, then
        // return false
        return false;
    }


    /**
     * BFS: https://www.youtube.com/watch?v=KiCBXu4P-2Y
     * Queue: https://www.delftstack.com/fr/howto/java/enqueue-and-dequeue-java/
     *
     * @param agent
     */
    public int BFS(Agent agent) {
        // Global variables
        Box[][] boxes = Game.getGrid().getBoxes();

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
        int[] colDirections = new int[]{0, 0, +1, -0};

        // ========== Solve

        rowQ.add(sourceRow);
        colQ.add(sourceCol);
        visited[sourceRow][sourceCol] = true;

        while (rowQ.size() > 0) {
            int row = rowQ.remove();
            int col = colQ.remove();

            if (boxes[row][col].equals(destination)) {
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
                if (boxes[newRow][newCol].getAgent() != null) continue;

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

        /*return findPath(boxes, rowQ, colQ, visited, reachedEnd,
                source, destination, nodesLeftInLayer,
                nodesInNextLayer, movesCount);*/
    }

    public int findPath(Box[][] boxes, Queue<Integer> rowQ, Queue<Integer> colQ,
                        boolean[][] visited, boolean reachedEnd, Box source,
                        Box destination, int nodesLeftInLayer, int nodesInNextLayer,
                        int movesCount) {

        int sourceRow = source.getX();
        int sourceCol = source.getY();

        rowQ.add(sourceRow);
        colQ.add(sourceCol);
        visited[sourceRow][sourceCol] = true;

        while (rowQ.size() > 0) {
            int row = rowQ.remove();
            int col = colQ.remove();

            if (boxes[row][col].equals(destination)) {
                reachedEnd = true;
                break;
            }

            exploreNeighbours(row, col, visited, boxes, rowQ, colQ, nodesInNextLayer);

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

    public void exploreNeighbours(int row, int col, boolean[][] visited,
                                  Box[][] boxes, Queue<Integer> rowQ,
                                  Queue<Integer> colQ, int nodesInNextLayer) {

        int rowsCount = Game.getGridSize();
        int colsCount = Game.getGridSize();

        int[] rowDirections = new int[]{-1, +1, 0, 0};
        int[] colDirections = new int[]{0, 0, +1, -0};

        for (int i = 0; i < 4; i++) {
            int newRow = row + rowDirections[i];
            int newCol = col + colDirections[i];

            // Skip out of bounds locations
            if (newRow < 0 || newCol < 0) continue;
            if (newRow >= rowsCount || newCol >= colsCount) continue;

            // Skip visited locations or blocked cells
            if (visited[newRow][newCol]) continue;
            if (boxes[newRow][newCol].getAgent() != null) continue;

            rowQ.add(newRow);
            colQ.add(newCol);
            visited[newRow][newCol] = true;
            nodesInNextLayer++;
        }
    }

    public void reconstructPath() {

    }


}
