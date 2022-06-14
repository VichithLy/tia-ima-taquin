package com.tia;

import com.tia.enums.Direction;
import com.tia.enums.Strategy;
import com.tia.models.Agent;
import com.tia.models.Game;
import com.tia.strategies.CognitiveStrategy;
import com.tia.strategies.Context;
import com.tia.strategies.NaiveStrategy;
import com.tia.strategies.SimpleStrategy;
import com.tia.views.GridView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.Arrays;

import static com.tia.Constants.SIZE_BOARD;

public class GameController {
    Boolean gameIsInit = false;
    Boolean exitGame; // https://www.geeksforgeeks.org/killing-threads-in-java/

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
        initStrategiesAndSetDefault();
        initAgentsNumberAndSetDefault();
        GridView.drawBoards(board, solvedBoard);
    }

    @FXML
    public void init() {
        gameIsInit = true;

        Game.init(SIZE_BOARD, (int) chosenAgentsNumber.getValue(), returnSelectedStrategyContext());
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
        // TODO Run
        // TODO While puzzle not complete
        // TODO Agents solving

        if (gameIsInit) {
            Agent agent = Game.getAgents().get(0);

            Runnable runnable = () -> {
                System.out.println("Inside : " + Thread.currentThread().getName());

                while (!agent.isArrived() && !exitGame) {
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

                if (!exitGame) {
                    System.out.println("Agent is arrived at destination!");

                    Platform.runLater(() -> {
                        GridView.showAlertWithHeaderText();
                    });
                }

            };
            exitGame = false;
            Thread thread = new Thread(runnable);
            thread.start();
        }
    }

    @FXML
    public void reset() {
        exitGame = true;
        GridView.resetBoards(board, solvedBoard);
    }

    // ==========

    public void initStrategiesAndSetDefault() {
        chosenStrategy.setItems(FXCollections.observableList(Arrays.asList("Naive", "Simple", "Cognitive")));
        chosenStrategy.getSelectionModel().selectFirst();
    }

    public void initAgentsNumberAndSetDefault() {
        ArrayList<Integer> agentsNumbers = new ArrayList<>();
        for (int i = 1; i < SIZE_BOARD * SIZE_BOARD; i++) {
            agentsNumbers.add(i);
        }
        chosenAgentsNumber.setItems(FXCollections.observableList(agentsNumbers));
        chosenAgentsNumber.getSelectionModel().selectFirst();
    }

    public Context returnSelectedStrategyContext() {
        Context context = new Context(new NaiveStrategy());

        switch ((String) chosenStrategy.getValue()) {
            case "Naive" -> context = new Context(new NaiveStrategy());
            case "Simple" -> context = new Context(new SimpleStrategy());
            case "Cognitive" -> context = new Context(new CognitiveStrategy());
        }

        System.out.println((String) chosenStrategy.getValue());

        return context;
    }
}