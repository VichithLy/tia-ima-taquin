package com.tia;

import com.tia.models.Grid;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        final int GRID_ROWS_NUMBER = 5;
        final int GRID_COLUMNS_NUMBER = 5;
        final int AGENTS_NUMBER = 4;

        Grid grid = new Grid(GRID_ROWS_NUMBER, GRID_COLUMNS_NUMBER, AGENTS_NUMBER);
        grid.printStatus();

        launch();
    }
}