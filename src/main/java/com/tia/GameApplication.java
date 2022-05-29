package com.tia;

import com.tia.models.Box;
import com.tia.models.Game;
import com.tia.models.Grid;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
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
import static com.tia.Constants.*;

public class GameApplication extends Application {
    private Game game;
    public final int SIZE = 5;
    public final int AGENTS_NUMBER = 4;

    @Override
    public void start(Stage stage) throws IOException {

        /*Game game = new Game(SIZE, AGENTS_NUMBER);
        game.printStatus();*/

        /*game.printAgents();
        System.out.println();
        game.printStatus();*/

        /*Text title = createMainTitle();
        GridPane board = createGameBoard(game);
        BorderPane border = createMainBorder(title, board);*/

        FXMLLoader fxmlLoader = new FXMLLoader(GameApplication.class.getResource("game-view.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, WIDTH_SCENE, HEIGHT_SCENE);
        // Scene scene = new Scene(border);
        stage.setTitle("IMA - Taquin");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private Text createMainTitle() {
        Text text = new Text("IMA - Taquin");
        text.setFont(Font.font("Verdana", 20));
        text.setFill(Color.BLACK);

        return text;
    }

    public static void main(String[] args) {
        launch();
    }
}