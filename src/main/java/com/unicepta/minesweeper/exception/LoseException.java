package com.unicepta.minesweeper.exception;

import com.unicepta.minesweeper.model.Board;

public class LoseException extends RuntimeException{
    private final Board board;

    public LoseException(final Board board) {
        this.board = board;
    }

    public Board getBoard() {
        return board;
    }
}
