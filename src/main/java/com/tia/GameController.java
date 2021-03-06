package com.tia;

import com.tia.messages.MailBox;
import com.tia.models.Agent;
import com.tia.models.Game;
import com.tia.strategies.CognitiveStrategy;
import com.tia.strategies.Context;
import com.tia.strategies.NaiveStrategy;
import com.tia.strategies.SimpleStrategy;
import com.tia.views.GridView;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.tia.Constants.SIZE_BOARD;

public class GameController {
    static Boolean gameIsInit = false;
    static Boolean gameIsRunning = false;
    // Ressource: https://www.geeksforgeeks.org/killing-threads-in-java/
    static Boolean exitGame;

    @FXML
    GridPane board;
    @FXML
    GridPane solvedBoard;
    @FXML
    private ComboBox strategyBox;
    @FXML
    private ComboBox agentsNumberBox;
    @FXML
    private ComboBox stepDurationBox;
    @FXML
    private Label stepsCountLabel;

    private SimpleIntegerProperty stepsCount = new SimpleIntegerProperty(0);

    @FXML
    public void initialize() {
        initStrategiesAndSetDefault();
        initAgentsNumberAndSetDefault();
        initStepDurationAndSetDefault();
        GridView.drawBoards(board, solvedBoard);
    }

    /**
     * Initialize the game with agents.
     */
    @FXML
    public void init() {
        exitGame = true;
        gameIsInit = true;
        gameIsRunning = false;
        stepsCountLabel.textProperty().bind(Bindings.convert(stepsCount));
        runSetStepsCountLabelThread(0);

        Game.init(SIZE_BOARD, (int) agentsNumberBox.getValue(), returnSelectedStrategyContext());
        MailBox.init(Game.getAgents());

        GridView.createOrUpdateBoardsAndAgents(board, solvedBoard);
    }

    /**
     * Run the game.
     */
    @FXML
    public void run() {
        if (gameIsInit && !gameIsRunning) {
            gameIsRunning = true;
            exitGame = false;
            solveGame();
        }
    }

    /**
     * Stop the game.
     */
    @FXML
    public void stop() {
        if (gameIsInit && gameIsRunning) {
            gameIsInit = false;
            exitGame = true;
            runShowAlertThread("Game stopped");
        }
    }

    @FXML
    public void reset() {
        runSetStepsCountLabelThread(0);
        exitGame = true;
        gameIsRunning = false;
        gameIsInit = false;

        GridView.resetBoards(board, solvedBoard);
    }

    // Threads

    /**
     * Execute a threads pool of agents while the game is not finished.
     */
    public void solveGame() {
        Runnable runnable = () -> {
            while (!Game.isSolved() && exitGame == false) {
                runSetStepsCountLabelThread(stepsCount.getValue() + 1);
                executeAgentsThreadPool();
                runCreateOrUpdateBoardsAndAgentsThread();
                sleepMillis(GameUtils.convertToLong(stepDurationBox.getValue()));
            }

            if (exitGame == false) {
                runShowAlertThread("Board solved successfully!");
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

    public void runShowAlertThread(String text) {
        Platform.runLater(() -> {
            GridView.showAlert(text);
        });
    }

    public synchronized void runCreateOrUpdateBoardsAndAgentsThread() {
        Platform.runLater(() -> {
            GridView.createOrUpdateBoardsAndAgents(board, solvedBoard);
        });
    }

    public synchronized void runSetStepsCountLabelThread(int value) {
        Platform.runLater(() -> {
            stepsCount.setValue(value);
        });
    }

    /**
     * Ressource: https://ducmanhphan.github.io/2020-03-20-Waiting-threads-to-finish-completely-in-Java/
     */
    public synchronized void executeAgentsThreadPool() {
        CountDownLatch latch = new CountDownLatch(Game.getAgents().size());
        ExecutorService executor = Executors.newFixedThreadPool(Game.getAgents().size());

        for (Agent agent : Game.getAgents()) {
            agent.setLatch(latch);
            executor.execute(agent);
        }

        try {
            latch.await();
        } catch (InterruptedException ex) {
            System.out.println(ex);
        }
    }

    // Utils initialization

    public void initStrategiesAndSetDefault() {
        strategyBox.setItems(FXCollections.observableList(Arrays.asList("Naive", "Simple", "Cognitive")));
        strategyBox.getSelectionModel().select(2);
    }

    public void initAgentsNumberAndSetDefault() {
        ArrayList<Integer> agentsNumbers = new ArrayList<>();
        for (int i = 1; i < SIZE_BOARD * SIZE_BOARD; i++) {
            agentsNumbers.add(i);
        }
        agentsNumberBox.setItems(FXCollections.observableList(agentsNumbers));
        agentsNumberBox.getSelectionModel().selectFirst();
    }

    public void initStepDurationAndSetDefault() {
        stepDurationBox.setItems(FXCollections.observableList(Arrays.asList("10", "50", "250", "500", "1000", "2000")));
        stepDurationBox.getSelectionModel().select(3);
    }

    public Context returnSelectedStrategyContext() {
        Context context = new Context(new NaiveStrategy());

        switch ((String) strategyBox.getValue()) {
            case "Naive" -> context = new Context(new NaiveStrategy());
            case "Simple" -> context = new Context(new SimpleStrategy());
            case "Cognitive" -> context = new Context(new CognitiveStrategy());
        }

        return context;
    }

    // Print

    public void printParams() {
        System.out.println("Strategy=" + strategyBox.getValue().toString());
        System.out.println("Number of agents=" + agentsNumberBox.getValue().toString());
    }

    public void printStatus() {
        System.out.println("=================");
        System.out.println("Step number=" + stepsCountLabel.getText());
        printParams();
        Game.printAgents();
        Game.printGrid();
    }
}