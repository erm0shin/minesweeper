package com.unicepta.minesweeper.service;

import com.unicepta.minesweeper.exception.LoseException;
import com.unicepta.minesweeper.model.Board;
import com.unicepta.minesweeper.model.Tile;

import java.util.Set;
import java.util.stream.Stream;

import static com.unicepta.minesweeper.utils.MathUtils.calcTilePosition;
import static com.unicepta.minesweeper.utils.MathUtils.generateUniqueRandomNumbers;

public class GameService {

    public Board buildNewBoard(final int width, final int height, final int numberOfMines) {
        final int tilesCount = width * height;

        // init empty board
        final var tiles = new Tile[tilesCount];
        for (int i = 0; i < tilesCount; i++) {
            tiles[i] = new Tile();
            tiles[i].setValue(Tile.HIDDEN);
        }

        // init mines
        final var mines = generateUniqueRandomNumbers(numberOfMines, tilesCount);

        return new Board(width, height, tiles, mines);
    }

    public Board markTile(final Board board, final int x, final int y) {
        final int tilePosition = calcTilePosition(board.getWidth(), x, y);

        if (isMine(tilePosition, board.getMinePostions())) {
            board.getTiles()[tilePosition] = new Tile(Tile.MINE);
            throw new LoseException(board);
        }

        return openTile(board, tilePosition);
    }

    private Board openTile(final Board board, final int tilePosition) {
        final int countOfNearMines = getCountOfNearMines(tilePosition, board.getMinePostions());
        if (countOfNearMines != 0) {
            board.getTiles()[tilePosition] = new Tile(countOfNearMines);
        } else {
            board.getTiles()[tilePosition] = new Tile(Tile.EMPTY);
            getNearTilesPosition(tilePosition)
                    // prevent OutBoundException
                    .filter(position -> position >= 0 && position <= board.getTiles().length - 1)
                    // prevent StackOverflowError
                    .filter(position -> Tile.HIDDEN == board.getTiles()[position].getValue())
                    .forEach(position -> openTile(board, position));
        }
        return board;
    }

    private boolean isMine(final int tilePosition, final Set<Integer> minesPosition) {
        return minesPosition.contains(tilePosition);
    }

    private int getCountOfNearMines(final int tilePosition, final Set<Integer> minesPosition) {
        return (int) getNearTilesPosition(tilePosition)
                .filter(minesPosition::contains)
                .count();
    }

    private Stream<Integer> getNearTilesPosition(final int tilePosition) {
        return Stream.of(
                tilePosition - 6,
                tilePosition - 5,
                tilePosition - 4,
                tilePosition - 1,
                tilePosition + 1,
                tilePosition + 4,
                tilePosition + 5,
                tilePosition + 6
        );
    }

}
