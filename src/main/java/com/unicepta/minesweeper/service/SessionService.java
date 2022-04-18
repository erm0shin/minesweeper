package com.unicepta.minesweeper.service;

import com.unicepta.minesweeper.model.Board;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SessionService {

    private final Map<UUID, Board> boards = new ConcurrentHashMap<>();

    public Board addNewBoard(final Board board) {
        boards.put(board.getId(), board);
        return board;
    }

    public Board getBoard(final String boardId) {
        return this.getBoard(UUID.fromString(boardId));
    }

    public Board getBoard(final UUID boardId) {
        return boards.get(boardId);
    }

    public Board deleteBoard(final String boardId) {
        return this.deleteBoard(UUID.fromString(boardId));
    }

    public Board deleteBoard(final UUID boardId) {
        return boards.remove(boardId);
    }

    // I don't like this, but I need it for tests
    public Map<UUID, Board> getBoards() {
        return boards;
    }
}
