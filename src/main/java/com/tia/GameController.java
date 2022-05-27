package com.tia;

import com.tia.models.Game;
import com.tia.views.GridView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;

import java.util.Arrays;

import static com.tia.Constants.*;

public class GameController {
    @FXML
    Canvas board;
    @FXML
    private CheckBox simple;
    @FXML
    private CheckBox cognitive;
    @FXML
    private ComboBox strategy;

    private GraphicsContext gc;
    private Game game;


    @FXML
    public void initialize() {
        gc = board.getGraphicsContext2D();
        strategy.setItems(FXCollections.observableList(Arrays.asList("Simple", "Cognitive")));
    }

    @FXML
    public void init() {
        GridView.drawBoard(gc);
    }

    @FXML
    public void run() {
        Thread t = new Thread(game);
        t.start();
    }

    @FXML
    public void reset() {
        gc.clearRect(0, 0, WIDTH_BOARD, WIDTH_BOARD);
    }
}