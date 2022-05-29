package com.tia.views;

import com.tia.models.Agent;
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

    public static void drawBoards(GridPane board, GridPane solvedBoard) {
        // https://edencoding.com/javafx-searching-grids/
        for (int column = 0; column < SIZE_BOARD; column++) {
            for (int row = 0; row < SIZE_BOARD; row++) {
                Rectangle tile = new Rectangle(WIDTH_TILE - 1, WIDTH_TILE - 1);
                tile.setStrokeWidth(0.5);
                tile.setStroke(Color.BLACK);
                tile.setFill(Color.WHITE);

                board.setColumnIndex(tile, column);
                board.setRowIndex(tile, row);
                board.add(tile, column, row);
            }
        }

        for (int column = 0; column < SIZE_BOARD; column++) {
            for (int row = 0; row < SIZE_BOARD; row++) {
                Rectangle tile = new Rectangle(WIDTH_TILE - 1, WIDTH_TILE - 1);
                tile.setStrokeWidth(0.5);
                tile.setStroke(Color.BLACK);
                tile.setFill(Color.WHITE);

                solvedBoard.setColumnIndex(tile, column);
                solvedBoard.setRowIndex(tile, row);
                solvedBoard.add(tile, column, row);
            }
        }
    }

    public static void drawAgents(GridPane board, GridPane solvedBoard, List<Agent> agents) {
        for (Agent agent : agents) {
            System.out.println(agent.toString());

            Rectangle tile = new Rectangle(WIDTH_TILE - 20 , WIDTH_TILE - 20);
            tile.setFill(Color.LIGHTGRAY);
            tile.setStrokeWidth(0);

            Text text = new Text(agent.getSymbol().getText());
            text.setFont(Font.font("Verdana", 60));
            StackPane stack = new StackPane();
            stack.getChildren().addAll(tile, text);

            board.add(stack, agent.getSource().getX(), agent.getSource().getY());
        }

        for (Agent agent : agents) {
            Rectangle tile = new Rectangle(WIDTH_TILE - 20 , WIDTH_TILE - 20);
            tile.setFill(Color.LIGHTGRAY);
            tile.setStrokeWidth(0);

            Text text = new Text(agent.getSymbol().getText());
            text.setFont(Font.font("Verdana", 60));
            StackPane stack = new StackPane();
            stack.getChildren().addAll(tile, text);

            solvedBoard.add(stack, agent.getDestination().getX(), agent.getDestination().getY());
        }

        System.out.println("\n------------\n");
    }
}
