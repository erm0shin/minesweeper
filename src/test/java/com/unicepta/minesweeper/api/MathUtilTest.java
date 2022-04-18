package com.unicepta.minesweeper.api;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.unicepta.minesweeper.utils.MathUtils.calcTilePosition;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class MathUtilTest {

    @ParameterizedTest
    @MethodSource("boardData")
    void calcTilePositionTest(int boardWidth, int x, int y, int result) {
        assertEquals(result, calcTilePosition(boardWidth, x, y));
    }

    static Stream<Arguments> boardData() {
        return Stream.of(
                arguments(5, 1, 1, 0),
                arguments(5, 1, 2, 1),
                arguments(5, 1, 5, 4),
                arguments(5, 2, 1, 5),
                arguments(5, 3, 3, 12),
                arguments(5, 4, 3, 17),
                arguments(5, 5, 5, 24)
        );
    }

}
