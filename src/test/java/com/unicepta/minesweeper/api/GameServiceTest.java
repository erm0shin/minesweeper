package com.unicepta.minesweeper.api;

import com.unicepta.minesweeper.exception.LoseException;
import com.unicepta.minesweeper.model.Board;
import com.unicepta.minesweeper.model.Tile;
import com.unicepta.minesweeper.service.GameService;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GameServiceTest {

    private static final int DEFAULT_WIDTH = 5;
    private static final int DEFAULT_HEIGHT = 5;
    private static final int DEFAULT_MINES_COUNT = 9;


    private final GameService gameService = new GameService();

    @Test
    public void createBoard() {
        var board = createTestBoard();
        assertAll(
                () -> assertNotNull(board.getId()),
                () -> assertEquals(DEFAULT_WIDTH, board.getWidth()),
                () -> assertEquals(DEFAULT_HEIGHT, board.getHeight()),
                () -> assertEquals(DEFAULT_WIDTH * DEFAULT_HEIGHT, board.getTiles().length),
                () -> assertEquals(DEFAULT_MINES_COUNT, board.getMinePostions().size())
        );
    }

    @Test
    public void allTilesAreHidden() {
        var board = createTestBoard();
        for (Tile tile : board.getTiles()) {
            assertEquals(Tile.HIDDEN, tile.getValue());
        }
    }

    @Test
    public void uncoverMine() {
        var board = createTestBoard();
        mockMines(board, 12);

        LoseException exception = assertThrows(
                LoseException.class,
                () -> gameService.markTile(board, 3, 3)
        );
        var updatedBoard = exception.getBoard();
        assertEquals(Tile.MINE, updatedBoard.getTiles()[12].getValue());
        for (int i = 0; i < DEFAULT_WIDTH * DEFAULT_HEIGHT; i++) {
            if (i != 12) {
                // Assert all other are not marked
                assertEquals(Tile.HIDDEN, updatedBoard.getTiles()[i].getValue());
            }
        }
    }

    @Test
    public void uncoverTileNearMine() {
        var board = createTestBoard();
        mockMines(board, 17, 18);

        var updatedBoard = gameService.markTile(board, 3, 3);
        assertEquals('2', updatedBoard.getTiles()[12].getValue());
        for (int i = 0; i < DEFAULT_WIDTH * DEFAULT_HEIGHT; i++) {
            if (i != 12) {
                // Assert all other are not marked
                assertEquals(Tile.HIDDEN, updatedBoard.getTiles()[i].getValue());
            }
        }
    }

    @Test
    public void uncoverAllTiles() {
        var board = createTestBoard();
        // Make so there are no mines on the board
        mockMines(board);

        var updatedBoard = gameService.markTile(board, 3, 3);
        for (int i = 0; i < DEFAULT_WIDTH * DEFAULT_HEIGHT; i++) {
            // Assert all tiles are opened
            assertEquals(Tile.EMPTY, updatedBoard.getTiles()[i].getValue());
        }
    }

    @Test
    public void uncoverUpperLeftCorner() {
        var board = createTestBoard();
        mockMines(board, 2, 7, 10, 11, 12);

        var updatedBoard = gameService.markTile(board, 1, 1);

        assertAll(
                // assert empty tile
                () -> assertEquals(Tile.EMPTY, updatedBoard.getTiles()[0].getValue()),
                // assert tile near the mines
                () -> assertEquals('2', updatedBoard.getTiles()[1].getValue()),
                () -> assertEquals('2', updatedBoard.getTiles()[5].getValue()),
                () -> assertEquals('5', updatedBoard.getTiles()[6].getValue()),
                // assert tiles with the mines
                () -> assertEquals(Tile.HIDDEN, updatedBoard.getTiles()[2].getValue()),
                () -> assertEquals(Tile.HIDDEN, updatedBoard.getTiles()[7].getValue()),
                () -> assertEquals(Tile.HIDDEN, updatedBoard.getTiles()[10].getValue()),
                () -> assertEquals(Tile.HIDDEN, updatedBoard.getTiles()[11].getValue()),
                () -> assertEquals(Tile.HIDDEN, updatedBoard.getTiles()[12].getValue())
        );
    }

    private Board createTestBoard() {
        return gameService.buildNewBoard(DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_MINES_COUNT);
    }

    private void mockMines(Board board, Integer... minesPositions) {
        board.getMinePostions().clear();
        board.getMinePostions().addAll(Arrays.asList(minesPositions));
    }

}