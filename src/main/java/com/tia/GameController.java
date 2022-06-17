package com.tia;

import com.tia.algorithms.BFS;
import com.tia.enums.Direction;
import com.tia.messages.MailBox;
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
import javafx.beans.property.SimpleIntegerProperty;
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

    @FXML
    public void init() {
        stepsCountLabel.textProperty().bind(Bindings.convert(stepsCount));
        runSetStepsCountLabelThread(0);
        exitGame = true;
        gameIsInit = true;
        gameIsRunning = false;

        Game.init(SIZE_BOARD, (int) agentsNumberBox.getValue(), returnSelectedStrategyContext());
        MailBox.init(Game.getAgents());

        GridView.createOrUpdateBoardsAndAgents(board, solvedBoard);

        // printStatus();
    }

    @FXML
    public void up() {
        Agent agent = Game.getAgents().get(0);
        List<Box> path = BFS.findPath(agent, false);
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

        // printStatus();
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
    public void stop() {
        if (gameIsInit && gameIsRunning) {
            exitGame = true;
            runShowAlertThread("Game stopped");
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
            // TO_UNCOMMENT
            while (!Game.isSolved() && !exitGame) {
//            for (int i = 0; i < 1; i++) {
                runSetStepsCountLabelThread(stepsCount.getValue() + 1);
                executeAgentsThreadPool();
                runCreateOrUpdateBoardsAndAgentsThread();
                sleepMillis(GameUtils.convertToLong(stepDurationBox.getValue()));
                // printStatus();
//                Game.printGrid();
            }

            // TO_UNCOMMENT
            if (!exitGame) {
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
        // TO_UNCOMMENT

        CountDownLatch latch = new CountDownLatch(Game.getAgents().size());
        ExecutorService executor = Executors.newFixedThreadPool(Game.getAgents().size());

        for (Agent agent : Game.getAgents()) {
            agent.setLatch(latch);
            executor.execute(agent);
        }

        // Test with one Agent
//        CountDownLatch latch = new CountDownLatch(1);
//        ExecutorService executor = Executors.newFixedThreadPool(1);
//
//        Agent agent = Game.getAgent(0);
//        agent.setLatch(latch);
//        executor.execute(agent);
//
//        executor.shutdown();

        try {
            latch.await();
        } catch (InterruptedException ex) {
            System.out.println(ex);
        }
    }

    // Utils

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
        stepDurationBox.setItems(FXCollections.observableList(Arrays.asList("250", "500", "1000", "2000")));
        stepDurationBox.getSelectionModel().select(2);
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