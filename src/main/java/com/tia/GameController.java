package com.tia;

import com.tia.enums.Mode;
import com.tia.models.Game;
import com.tia.views.GridView;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;

import java.util.Arrays;

import static com.tia.Constants.*;

public class GameController {
    @FXML
    GridPane board;
    @FXML
    GridPane solvedBoard;
    @FXML
    private ComboBox strategy;

    private Game game;


    @FXML
    public void initialize() {
        strategy.setItems(FXCollections.observableList(Arrays.asList("Simple", "Cognitive")));
        strategy.getSelectionModel().selectFirst();

        GridView.drawBoards(board, solvedBoard);
    }

    @FXML
    public void init() {
        Mode mode = strategy.getSelectionModel().isSelected(0) ? Mode.SIMPLE : Mode.COGNITIVE;

        game = new Game(SIZE_BOARD, NUMBER_AGENTS, mode);

        reset();
        GridView.drawAgents(board, solvedBoard, game.getAgents());
    }

    @FXML
    public void run() {
        Thread t = new Thread(game);
        t.start();
    }

    @FXML
    public void reset() {
        board.getChildren().clear();
        solvedBoard.getChildren().clear();
        GridView.drawBoards(board, solvedBoard);
    }
}