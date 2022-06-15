package com.tia.strategies;

import com.tia.enums.Direction;
import com.tia.models.Agent;
import com.tia.models.Box;
import com.tia.models.Game;
import com.tia.models.Grid;

import java.util.*;

public class SimpleStrategy implements Strategy {
    @Override
    public void move(Agent agent, Direction direction) {
        NaiveStrategy strategy = new NaiveStrategy();
        strategy.move(agent, direction);
    }

    @Override
    public void solve(Agent agent) {
        // System.out.println("SimpleStrategy");

        List<Box> path = findPath(agent);
        List<Direction> directions = convertPathToDirections(path);

        agent.setPathDirections(directions);

        // System.out.println(agent.getPathDirections());

        if (directions.size() != 0) {
            // System.out.println(directions.get(0));
            move(agent, directions.get(0));
        }

        /*NaiveStrategy strategy = new NaiveStrategy();
        strategy.solve(agent);*/
    }

    /**
     * BFS path finding algorithm <br>
     * - BFS: https://www.youtube.com/watch?v=KiCBXu4P-2Y <br>
     * - Queue: https://www.delftstack.com/fr/howto/java/enqueue-and-dequeue-java/ <br>
     * - Reconstruct path: https://stackoverflow.com/questions/61113331/bfs-search-in-2d-grid-java-shortest-path <br>
     *
     * @param agent
     * @return the shortest path between agent's source and destination
     */
    public List<Box> findPath(Agent agent) {
        // Global variables
        Grid grid = Game.getGrid();

        int rowsCount = Game.getGridSize();
        int colsCount = Game.getGridSize();

        // Source and destination
        Box source = agent.getCurrent();
        int sourceRow = source.getX();
        int sourceCol = source.getY();
        Box destination = agent.getDestination();

        // Queues
        Queue<Integer> rowQ = new LinkedList<>();
        Queue<Integer> colQ = new LinkedList<>();

        // Path
        Map<Box, Box> parentsMap = new HashMap<>();
        List<Box> path = new ArrayList<>();

        // Tracking
        int movesCount = 0;
        int nodesLeftInLayer = 1;
        int nodesInNextLayer = 0;

        boolean reachedEnd = false;

        // Matrix of visited boxes: if Box(i, j) has been visited = true
        boolean[][] visited = new boolean[rowsCount][colsCount];

        // Directions vectors
        int[] rowDirections = new int[]{-1, 1, 0, 0};
        int[] colDirections = new int[]{0, 0, 1, -1};

        // Solving
        rowQ.add(sourceRow);
        colQ.add(sourceCol);
        visited[sourceRow][sourceCol] = true;

        Box box = source; // Starting box
        parentsMap.put(source, null); // Source cell has not parent

        while (rowQ.size() > 0) {
            int row = rowQ.remove();
            int col = colQ.remove();
            box = grid.getBox(row, col);

            if (box.equals(destination)) {
                reachedEnd = true;
                break;
            }

            // Explore box neighbours
            for (int i = 0; i < 4; i++) {
                int newRow = row + rowDirections[i];
                int newCol = col + colDirections[i];

                // Neighbours in grid's bounds
                if (newRow < 0 || newCol < 0) continue;
                if (newRow >= rowsCount || newCol >= colsCount) continue;

                // Skip visited or blocked boxes
                if (visited[newRow][newCol]) continue;

                // Skip boxes obstacles
                if (grid.getBox(newRow, newCol).getAgent() != null) continue;

                // Not visited boxes
                rowQ.add(newRow);
                colQ.add(newCol);
                visited[newRow][newCol] = true;

                // To reconstruct the path
                parentsMap.put(new Box(newRow, newCol), new Box(row, col));

                nodesInNextLayer++;
            }

            nodesLeftInLayer--;

            if (nodesLeftInLayer == 0) {
                nodesLeftInLayer = nodesInNextLayer;
                nodesInNextLayer = 0;
                movesCount++;
            }
        }

        // Path reconstruction
        Box current = box;
        while (current != null) {
            path.add(0, current);
            current = parentsMap.get(current);
        }

        // Return path
        if (reachedEnd) {
            System.out.println("movesCount=" + movesCount);

            return path;
        }

        // If no path found
        return Collections.emptyList();
    }

    public List<Direction> convertPathToDirections(List<Box> path) {
        List<Direction> directions = new ArrayList<>();

        for (int i = 0; i < path.size() - 1; i++) {
            if (path.get(i).getX() != path.get(i + 1).getX()) {
                if (path.get(i).getX() < path.get(i + 1).getX())
                    directions.add(Direction.SOUTH);
                else if (path.get(i).getX() > path.get(i + 1).getX())
                    directions.add(Direction.NORTH);
            } else if (path.get(i).getY() != path.get(i + 1).getY()) {
                if (path.get(i).getY() < path.get(i + 1).getY())
                    directions.add(Direction.EAST);
                else if (path.get(i).getY() > path.get(i + 1).getY())
                    directions.add(Direction.WEST);
            }
        }

        return directions;
    }
}
