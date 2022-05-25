package com.tia;

import com.tia.models.Box;
import com.tia.models.Game;
import com.tia.models.Grid;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class GameApplication extends Application {
    private Game game;
    public final int SIZE = 5;
    public final int AGENTS_NUMBER = 4;

    @Override
    public void start(Stage stage) throws IOException {

        Game game = new Game(SIZE, AGENTS_NUMBER);
        game.printStatus();

        /*game.printAgents();
        System.out.println();
        game.printStatus();*/

        Text title = createMainTitle();
        GridPane board = createGameBoard(game);
        BorderPane border = createMainBorder(title, board);

        // FXMLLoader fxmlLoader = new FXMLLoader(GameApplication.class.getResource("game-view.fxml"));
        // Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        Scene scene = new Scene(border);
        stage.setTitle("IMA - Taquin");
        stage.setScene(scene);
        stage.show();
    }

    private Text createMainTitle() {
        Text text = new Text("IMA - Taquin");
        text.setFont(Font.font("Verdana", 20));
        text.setFill(Color.BLACK);

        return text;
    }

    private GridPane createGameBoard(Game game) {
        GridPane board = new GridPane();
        board.setPadding(new Insets(25, 0, 0, 0));
        // grid.setGridLinesVisible(true);

        /*for (int column = 0; column < SIZE; column++) {
            for (int row = 0; row < SIZE; row++) {
                Rectangle tile = new Rectangle(50, 50);
                tile.setFill(Color.BURLYWOOD);
                tile.setStroke(Color.BLACK);

                grid.setColumnIndex(tile, column);
                grid.setRowIndex(tile, row);

                grid.add(new StackPane(tile), column, row);
            }
        }*/

        Grid gridGame = game.getGrid();

        for (Box[] boxesRow : gridGame.getBoxes()) {
            for (Box box : boxesRow) {
                Rectangle tile = new Rectangle(50, 50, 50,50);

                tile.setStroke(Color.BLACK);

                if (box.getAgent() == null) {
                    tile.setFill(Color.WHITE);

                    /*board.setColumnIndex(tile, box.getY());
                    board.setRowIndex(tile, box.getX());*/
                    board.add(new StackPane(tile), box.getY(), box.getX());
                } else {
                    tile.setFill(Color.GRAY);

                    Text text = new Text(box.getAgent().getSymbol().getText());
                    text.setFont(Font.font("Verdana", 20));
                    StackPane stack = new StackPane();
                    stack.getChildren().addAll(tile, text);

                    board.add(stack, box.getY(), box.getX());
                }
            }
        }

        return board;
    }

    private BorderPane createMainBorder(Text title, GridPane grid) {
        BorderPane border = new BorderPane();

        border.setPadding(new Insets(25, 50, 50, 50));
        border.setAlignment(title, Pos.CENTER);
        border.setTop(title);
        border.setCenter(grid);

        return border;
    }

    public static void main(String[] args) {
        launch();
    }
}