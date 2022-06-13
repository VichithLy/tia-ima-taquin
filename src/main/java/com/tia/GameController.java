package com.tia;

import com.tia.enums.Direction;
import com.tia.enums.Mode;
import com.tia.models.Game;
import com.tia.views.GridView;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.Arrays;

import static com.tia.Constants.*;

public class GameController {
    @FXML
    GridPane board;
    @FXML
    GridPane solvedBoard;
    @FXML
    private ComboBox strategy;
    @FXML
    private ComboBox agentsCount;

    @FXML
    public void initialize() {
        strategy.setItems(FXCollections.observableList(Arrays.asList("Simple", "Cognitive")));
        strategy.getSelectionModel().selectFirst();

        ArrayList<Integer> agentsNumbers = new ArrayList<>();
        for (int i = 1; i < SIZE_BOARD * SIZE_BOARD; i++) {
            agentsNumbers.add(i);
        }
        agentsCount.setItems(FXCollections.observableList(agentsNumbers));
        agentsCount.getSelectionModel().selectFirst();

        GridView.drawBoards(board, solvedBoard);
    }

    @FXML
    public void init() {
        Mode mode = strategy.getSelectionModel().isSelected(0) ? Mode.SIMPLE : Mode.COGNITIVE;
        Game.init(SIZE_BOARD, NUMBER_AGENTS, mode);
        GridView.createOrUpdateBoardsAndAgents(board, solvedBoard);

        Game.printAgents();
        Game.printStatus();
    }

    @FXML
    public void up() {
        Game.getAgents().get(0).move(Direction.NORTH);

        Game.printAgents();
        Game.printStatus();
        GridView.createOrUpdateBoardsAndAgents(board, solvedBoard);
    }

    @FXML
    public void down() {
        Game.getAgents().get(0).move(Direction.SOUTH);

        Game.printAgents();
        Game.printStatus();
        GridView.createOrUpdateBoardsAndAgents(board, solvedBoard);
    }

    @FXML
    public void left() {
        Game.getAgents().get(0).move(Direction.WEST);

        Game.printAgents();
        Game.printStatus();
        GridView.createOrUpdateBoardsAndAgents(board, solvedBoard);
    }

    @FXML
    public void right() {
        Game.getAgents().get(0).move(Direction.EAST);

        Game.printAgents();
        Game.printStatus();
        GridView.createOrUpdateBoardsAndAgents(board, solvedBoard);
    }

    @FXML
    public void run() {
    }

    @FXML
    public void reset() {
        GridView.resetBoards(board, solvedBoard);
    }
}