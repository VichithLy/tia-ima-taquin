package com.tia.views;
import com.tia.models.Agent;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.List;

import static com.tia.Constants.*;

public class GridView {


    private GridView() {

    }

    public static void resetBoards(GridPane board, GridPane solvedBoard) {
        board.getChildren().clear();
        solvedBoard.getChildren().clear();
        GridView.drawBoards(board, solvedBoard);
    }

    public static void updateBoardsAndAgents(GridPane board, GridPane solvedBoard, List<Agent> agents) {
        resetBoards(board, solvedBoard);
        drawAgents(board, solvedBoard, agents);
    }

    public static void drawBoards(GridPane board, GridPane solvedBoard) {
        // https://edencoding.com/javafx-searching-grids/
        for (int column = 0; column < SIZE_BOARD; column++) {
            for (int row = 0; row < SIZE_BOARD; row++) {
                Rectangle tile = new Rectangle(WIDTH_TILE, WIDTH_TILE);
                tile.setStroke(Color.BLACK);
                tile.setFill(Color.WHITE);
                // board.add(new StackPane(tile), column, row);
                board.add(tile, column, row);
            }
        }

        for (int column = 0; column < SIZE_BOARD; column++) {
            for (int row = 0; row < SIZE_BOARD; row++) {
                Rectangle tile = new Rectangle(WIDTH_TILE, WIDTH_TILE);
                tile.setStroke(Color.BLACK);
                tile.setFill(Color.WHITE);
                solvedBoard.add(tile, column, row);
            }
        }
    }

    public static StackPane createAgentStack(Agent agent) {
        final int PADDING = 20;

        Rectangle agentTile = new Rectangle(WIDTH_TILE - PADDING , WIDTH_TILE - PADDING);
        agentTile.setFill(Color.LIGHTGRAY);
        agentTile.setStrokeWidth(1);
        agentTile.setStroke(Color.BLACK);

        Text text = new Text(agent.getSymbol().getText());
        text.setFont(Font.font(60));

        StackPane agentStack = new StackPane(agentTile, text);

        return agentStack;
    }

    public static void drawAgents(GridPane board, GridPane solvedBoard, List<Agent> agents) {
        for (Agent agent : agents) {
            System.out.println(agent.toString());

            StackPane agentStack = createAgentStack(agent);

            /*StackPane boardTile = (StackPane) getNodeByCoordinate(board, agent.getSource().getX(), agent.getSource().getY());
            boardTile.getChildren().addAll(agentStack);*/

            board.add(agentStack, agent.getCurrent().getX(), agent.getCurrent().getY());
        }

        for (Agent agent : agents) {
            StackPane agentStack = createAgentStack(agent);
            solvedBoard.add(agentStack, agent.getDestination().getX(), agent.getDestination().getY());
        }

        System.out.println("\n------------\n");
    }


    public static Node getNodeByCoordinate(GridPane grid, Integer row, Integer column) {
        for (Node node : grid.getChildren()) {
            if(GridPane.getColumnIndex(node) == column && GridPane.getRowIndex(node) == row){
                return node;
            }
        }
        return null;
    }
}
