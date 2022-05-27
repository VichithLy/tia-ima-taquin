package com.tia.views;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.stream.IntStream;

import static com.tia.Constants.*;

public class GridView {
    private GridView() {

    }

    public static void drawBoard(GraphicsContext gc) {
       gc.setFill(Color.WHITE);
       gc.setLineWidth(1);
       gc.clearRect(0, 0, SIZE_BOARD, SIZE_BOARD);
       gc.setFill(Color.BLACK);
        IntStream.range(1, SIZE_BOARD).forEach(i -> gc.strokeLine(i * WIDTH_TILE, 0, i * WIDTH_TILE, WIDTH_BOARD));
        IntStream.range(1, SIZE_BOARD).forEach(j -> gc.strokeLine(0, j * WIDTH_TILE, WIDTH_BOARD, j * WIDTH_TILE));
    }
}
