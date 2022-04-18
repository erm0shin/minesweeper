package com.unicepta.minesweeper.dto;

import com.unicepta.minesweeper.model.Tile;

import java.util.UUID;

public class BoardResponse {
    private UUID id;
    private int width;
    private int height;
    private Tile[] tiles;

    public BoardResponse(final UUID id, final int width, final int height, final Tile[] tiles) {
        this.id = id;
        this.width = width;
        this.height = height;
        this.tiles = tiles;
    }
}
