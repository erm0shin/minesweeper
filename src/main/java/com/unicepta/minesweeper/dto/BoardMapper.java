package com.unicepta.minesweeper.dto;

import com.unicepta.minesweeper.model.Board;

public class BoardMapper {
    // We need to hide mine positions from user
    public BoardResponse mapResponse(final Board board) {
        return new BoardResponse(
                board.getId(),
                board.getWidth(),
                board.getHeight(),
                board.getTiles()
        );
    }
}
