package com.unicepta.minesweeper.model;

public class Tile {
  public static final char HIDDEN = '?';
  public static final char MINE = '*';
  public static final char EMPTY = '-';

  private char value;

  public Tile() {
    this.value = HIDDEN;
  }

  public Tile(final int nearMinesCount) {
    this.value = Character.forDigit(nearMinesCount, 10);
  }

  public Tile(final char symbol) {
    this.value = symbol;
  }

  public char getValue() {
    return value;
  }

  public void setValue(char value) {
    this.value = value;
  }
}
