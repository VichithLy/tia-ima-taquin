package com.tia;

import com.tia.enums.Direction;
import com.tia.enums.Strategy;
import com.tia.models.Agent;
import com.tia.models.Game;
import com.tia.views.GridView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.tia.Constants.*;

public class GameController {
    @FXML
    GridPane board;
    @FXML
    GridPane solvedBoard;
    @FXML
    private ComboBox chosenStrategy;
    @FXML
    private ComboBox chosenAgentsNumber;

    @FXML
    public void initialize() {
        chosenStrategy.setItems(FXCollections.observableList(Arrays.asList("Simple", "Cognitive")));
        chosenStrategy.getSelectionModel().selectFirst();

        ArrayList<Integer> agentsNumbers = new ArrayList<>();
        for (int i = 1; i < SIZE_BOARD * SIZE_BOARD; i++) {
            agentsNumbers.add(i);
        }
        chosenAgentsNumber.setItems(FXCollections.observableList(agentsNumbers));
        chosenAgentsNumber.getSelectionModel().selectFirst();

        GridView.drawBoards(board, solvedBoard);
    }

    @FXML
    public void init() {
        Strategy strategy = chosenStrategy.getSelectionModel().isSelected(0) ? Strategy.SIMPLE : Strategy.COGNITIVE;
        Game.init(SIZE_BOARD, NUMBER_AGENTS, strategy);
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
    public void run() throws InterruptedException {
        // TODO Run
        // TODO While puzzle not complete
        // TODO Agents solving

        Agent agent = Game.getAgents().get(0);

        /* agent.solve();
        GridView.createOrUpdateBoardsAndAgents(board, solvedBoard);*/

        Runnable runnable = () -> {
            System.out.println("Inside : " + Thread.currentThread().getName());

            while (!agent.isArrived()) {
                agent.solve();
                Platform.runLater(() -> {
                     GridView.createOrUpdateBoardsAndAgents(board, solvedBoard);
                });
                System.out.println("=====");

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new IllegalStateException(e);
                }
            }
            System.out.println("Agent is arrived at destination!");
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    @FXML
    public void reset() {
        GridView.resetBoards(board, solvedBoard);
    }
}