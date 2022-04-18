package com.unicepta.minesweeper.dto;

public class UncoverTileRequest {
  public String boardId;
  public int x;
  public int y;

  public UncoverTileRequest(final String boardId, final int x, final int y) {
    this.boardId = boardId;
    this.x = x;
    this.y = y;
  }
}
