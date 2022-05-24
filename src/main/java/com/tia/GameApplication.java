package com.tia;

import com.tia.models.Game;
import com.tia.models.Grid;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GameApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GameApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        final int SIZE = 5;
        final int AGENTS_NUMBER = 4;

        Game game = new Game(5, AGENTS_NUMBER);
        game.printAgents();
        System.out.println();
        game.printStatus();

        // launch();
    }
}