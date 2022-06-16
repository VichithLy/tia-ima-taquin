package com.tia;

import com.tia.algorithms.BFS;
import com.tia.enums.Direction;
import com.tia.models.Agent;
import com.tia.models.Box;
import com.tia.models.Game;
import com.tia.strategies.CognitiveStrategy;
import com.tia.strategies.Context;
import com.tia.strategies.NaiveStrategy;
import com.tia.strategies.SimpleStrategy;
import com.tia.views.GridView;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.tia.Constants.SIZE_BOARD;

public class GameController {
    Boolean gameIsInit = false;
    Boolean gameIsRunning = false;
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
    private Label stepsCountLabel;

    private SimpleIntegerProperty stepsCount = new SimpleIntegerProperty(0);

    @FXML
    public void initialize() {
        initStrategiesAndSetDefault();
        initAgentsNumberAndSetDefault();
        GridView.drawBoards(board, solvedBoard);
    }

    @FXML
    public void init() {
        stepsCountLabel.textProperty().bind(Bindings.convert(stepsCount));
        runSetStepsCountLabelThread(0);
        exitGame = true;
        gameIsInit = true;
        gameIsRunning = false;

        Game.init(SIZE_BOARD, (int) chosenAgentsNumber.getValue(), returnSelectedStrategyContext());
        GridView.createOrUpdateBoardsAndAgents(board, solvedBoard);

        printStatus();
    }

    @FXML
    public void up() {
        Agent agent = Game.getAgents().get(0);

        SimpleStrategy strategy = new SimpleStrategy();
        System.out.println("=====");

        List<Box> path = BFS.findPathByAvoidingObstacles(agent);
        System.out.println("path=" + path);
        System.out.println("directions=" + BFS.convertPathToDirections(path));

        for (Box box : path) {
            int x = box.getX();
            int y = box.getY();

            GridView.colorTile(board, x, y, Color.AQUAMARINE);
        }
        // Game.printGrid();
    }

    @FXML
    public void down() {
        exitGame = true;
    }

    @FXML
    public void left() {
    }

    @FXML
    public void right() {
        Agent agent = Game.getAgents().get(0);
        NaiveStrategy strategy = new NaiveStrategy();
        strategy.move(agent, Direction.EAST);

        GridView.createOrUpdateBoardsAndAgents(board, solvedBoard);

        printStatus();
    }

    @FXML
    public void run() {
        if (gameIsInit && !gameIsRunning) {
            gameIsRunning = true;
            exitGame = false;
            solveGame();
        }
    }

    @FXML
    public void reset() {
        runSetStepsCountLabelThread(0);
        exitGame = true;
        gameIsRunning = false;

        GridView.resetBoards(board, solvedBoard);
    }

    // Threads

    public void solveGame() {
        Runnable runnable = () -> {
            while (!Game.isSolved() && !exitGame) {
                runSetStepsCountLabelThread(stepsCount.getValue() + 1);
                executeAgentsThreadPool();
                runCreateOrUpdateBoardsAndAgentsThread();
                sleepMillis(250);

                // printStatus();
            }

            if (!exitGame) {
                System.out.println("Board solved!");
                runShowAlertSolvedBoardThread();
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    public void sleepMillis(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }

    public void runShowAlertSolvedBoardThread() {
        Platform.runLater(() -> {
            GridView.showAlertSolvedBoard();
        });
    }

    public void runCreateOrUpdateBoardsAndAgentsThread() {
        Platform.runLater(() -> {
            GridView.createOrUpdateBoardsAndAgents(board, solvedBoard);
        });
    }

    public void runSetStepsCountLabelThread(int value) {
        Platform.runLater(() -> {
            stepsCount.setValue(value);
        });
    }

    /**
     * https://ducmanhphan.github.io/2020-03-20-Waiting-threads-to-finish-completely-in-Java/
     */
    public void executeAgentsThreadPool() {
        CountDownLatch latch = new CountDownLatch(Game.getAgents().size());
        ExecutorService executor = Executors.newFixedThreadPool(Game.getAgents().size());

        for (Agent agent : Game.getAgents()) {
            agent.setLatch(latch);
            executor.execute(agent);
        }

        executor.shutdown();

        try {
            latch.await();
        } catch (InterruptedException ex) {
            System.out.println(ex);
        }
    }

    // Utils

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

        return context;
    }

    public void printParams() {
        System.out.println("Strategy=" + chosenStrategy.getValue());
        System.out.println("Number of agents=" + chosenAgentsNumber.getValue());
    }

    public void printStatus() {
        System.out.println("=================");
        System.out.println("Step number=" + stepsCount);
        printParams();
        Game.printAgents();
        Game.printGrid();
    }
}