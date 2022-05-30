package com.tia;

import com.tia.enums.Mode;
import com.tia.models.Agent;
import com.tia.models.Box;
import com.tia.models.Game;
import com.tia.views.GridView;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
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

    private ObservableList<Agent> agents;

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

        agents = FXCollections.observableArrayList(game.getAgents());
        agents.addListener((ListChangeListener.Change<? extends Agent> change) -> {
            while (change.next()) {
               GridView.updateBoardsAndAgents(board, solvedBoard, agents);
            }
        });

        GridView.updateBoardsAndAgents(board, solvedBoard, agents);
    }

    @FXML
    public void run() {
        /* Thread t = new Thread(game);
        t.start();*/

        agents.set(0, new Agent(agents.get(0).getSymbol(), new Box(0,0), agents.get(0).getDestination()));
    }

    @FXML
    public void reset() {
        GridView.resetBoards(board, solvedBoard);
    }
}