package com.tia;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

import static com.tia.Constants.HEIGHT_SCENE;
import static com.tia.Constants.WIDTH_SCENE;

public class GameApplication extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GameApplication.class.getResource("game-view.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, WIDTH_SCENE, HEIGHT_SCENE);
        stage.setTitle("IMA - Taquin");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                GameController.gameIsInit = false;
                GameController.gameIsRunning = false;
                GameController.exitGame = true;
                System.exit(0);
            }
        });
    }
}