package com.unicepta.minesweeper.model;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class Board {

  private final UUID id;
  private final int width;
  private final int height;
  private final Tile[] tiles;
  private final Set<Integer> minePostions;

  public Board(final int width, final int height, final Tile[] tiles, final Set<Integer> minePostions) {
    this.id = UUID.randomUUID();
    this.width = width;
    this.height = height;
    this.tiles = tiles;
    this.minePostions = minePostions;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public Tile[] getTiles() {
    return tiles;
  }

  public UUID getId() {
    return id;
  }

  public Set<Integer> getMinePostions() {
    return minePostions;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final Board board = (Board) o;
    return width == board.width &&
           height == board.height &&
           Objects.equals(id, board.id) &&
           Arrays.equals(tiles, board.tiles) &&
           Objects.equals(minePostions, board.minePostions);
  }

  @Override
  public int hashCode() {
    int result = Objects.hash(id, width, height, minePostions);
    result = 31 * result + Arrays.hashCode(tiles);
    return result;
  }
}
