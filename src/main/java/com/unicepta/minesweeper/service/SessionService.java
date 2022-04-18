package com.unicepta.minesweeper.service;

import com.unicepta.minesweeper.model.Board;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SessionService {

    private final Map<UUID, Board> boards = new ConcurrentHashMap<>();

    public Board addNewBoard(final Board board) {
        return boards.put(board.getId(), board);
    }

    public Board getBoard(final String boardId) {
        return boards.get(UUID.fromString(boardId));
    }

    public Board deleteBoard(final String boardId) {
        return this.deleteBoard(UUID.fromString(boardId));
    }

    public Board deleteBoard(final UUID boardId) {
        return boards.remove(boardId);
    }

}
