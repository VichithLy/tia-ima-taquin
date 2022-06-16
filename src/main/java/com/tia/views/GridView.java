package com.tia.views;

import com.tia.models.Agent;

import com.tia.models.Game;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.List;

import static com.tia.Constants.*;

public class GridView {
    private GridView() {}

    /**
     * @param initialBoard
     * @param solvedBoard
     */
    public static void drawAgentsInBoards(GridPane initialBoard, GridPane solvedBoard) {
        // IMPORTANT: in 2D arrays, rows come first (y,x)
        // We have to reverse x and y in agent's coords

        List<Agent> agents = Game.getAgents();

        for (Agent agent : agents) {
            StackPane agentStack = createAgentStack(agent);
            initialBoard.add(agentStack, agent.getCurrent().getY(), agent.getCurrent().getX());
        }

        for (Agent agent : agents) {
            StackPane agentStack = createAgentStack(agent);
            solvedBoard.add(agentStack, agent.getDestination().getY(), agent.getDestination().getX());
        }
    }

    /**
     * @param initialBoard
     * @param solvedBoard
     */
    public static void createOrUpdateBoardsAndAgents(GridPane initialBoard, GridPane solvedBoard) {
        resetBoards(initialBoard, solvedBoard);
        drawAgentsInBoards(initialBoard, solvedBoard);
    }

    /**
     * @param board
     * @param column
     * @param row
     */
    private static void addTileToBoard(GridPane board, int column, int row) {
        Rectangle tile = new Rectangle(WIDTH_TILE, WIDTH_TILE);
        tile.setStroke(Color.BLACK);
        tile.setFill(Color.WHITE);
        board.add(tile, column, row);
    }

    /**
     * @param board
     * @param row
     * @param column
     * @param color
     */
    public static void colorTile(GridPane board, int row, int column, Color color) {
        for (Node node : board.getChildren()) {
            if (board.getRowIndex(node) == row
                    && board.getColumnIndex(node) ==  column) {

                if (node instanceof Rectangle) {
                    Rectangle tile = (Rectangle) node;
                    tile.setFill(color);
                }
            }
        }

    }

    /**
     * @param initialBoard
     * @param solvedBoard
     */
    public static void drawBoards(GridPane initialBoard, GridPane solvedBoard) {
        // https://edencoding.com/javafx-searching-grids/
        for (int column = 0; column < SIZE_BOARD; column++) {
            for (int row = 0; row < SIZE_BOARD; row++) {
                addTileToBoard(initialBoard, column, row);
                addTileToBoard(solvedBoard, column, row);
            }
        }
    }

    /**
     * @param initialBoard
     * @param solvedBoard
     */
    public static void resetBoards(GridPane initialBoard, GridPane solvedBoard) {
        initialBoard.getChildren().clear();
        solvedBoard.getChildren().clear();
        GridView.drawBoards(initialBoard, solvedBoard);
    }

    /**
     * @param agent
     * @return StackPane
     */
    public static StackPane createAgentStack(Agent agent) {
        final int PADDING = 20;

        Rectangle agentTile = new Rectangle(WIDTH_TILE - PADDING , WIDTH_TILE - PADDING);
        agentTile.setFill(Color.LIGHTGRAY);
        agentTile.setStrokeWidth(1);
        agentTile.setStroke(Color.BLACK);

        Text text = new Text(agent.getValue().getText());
        text.setFont(Font.font(60));

        StackPane agentStack = new StackPane(agentTile, text);

        return agentStack;
    }

    /**
     * https://devstory.net/11529/javafx-alert-dialog
     */
    public static void showAlertSolvedBoard() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("TIA - Puzzle - Taquin");
        alert.setHeaderText("Results");
        alert.setContentText("Board solved successfully!");

        alert.showAndWait();
    }
}
