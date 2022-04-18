package com.unicepta.minesweeper.api;

import com.unicepta.minesweeper.model.Board;
import com.unicepta.minesweeper.service.GameService;
import com.unicepta.minesweeper.service.SessionService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class SessionServiceTest {

    private static final int DEFAULT_WIDTH = 5;
    private static final int DEFAULT_HEIGHT = 5;
    private static final int DEFAULT_MINES_COUNT = 9;

    private final SessionService sessionService = new SessionService();
    private final GameService gameService = new GameService();

    @Test
    public void addBoard() {
        var board = createTestBoard();
        var addedBoard = sessionService.addNewBoard(board);
        assertAll(
                () -> assertEquals(board, addedBoard),
                () -> assertEquals(board, sessionService.getBoards().get(board.getId()))
        );
    }

    @Test
    public void selectBoard() {
        var board = createTestBoard();
        sessionService.addNewBoard(board);
        assertAll(
                () -> assertEquals(board, sessionService.getBoards().get(board.getId())),
                () -> assertEquals(board, sessionService.getBoard(board.getId()))
        );
        sessionService.deleteBoard(board.getId());
        assertNull(sessionService.getBoards().get(board.getId()));
    }

    @Test
    public void deleteBoard() {
        var board = createTestBoard();
        sessionService.addNewBoard(board);
        assertNotNull(sessionService.getBoards().get(board.getId()));

        sessionService.deleteBoard(board.getId());
        assertNull(sessionService.getBoards().get(board.getId()));
    }

    private Board createTestBoard() {
        return gameService.buildNewBoard(DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_MINES_COUNT);
    }

}
