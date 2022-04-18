package com.unicepta.minesweeper.utils;

import com.unicepta.minesweeper.model.Board;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class MathUtils {

    public static Set<Integer> generateUniqueRandomNumbers(final int neededCount, final int range) {
        // This is the first that I figured out to generate random numbers that not repeated
        final List<Integer> list = IntStream.range(0, range)
                                            .boxed()
                                            .collect(Collectors.toList());
        Collections.shuffle(list);

        return IntStream.range(0, neededCount)
                        .mapToObj(list::get)
                        .collect(Collectors.toCollection(() -> new HashSet<>(neededCount)));
    }

    public static int calcTilePosition(final int boardWidth, final int x, final int y) {
        /*
           I assume that client sends dimensions in range from 1 to boardWidth
           So I need to convert it to format 0..boardWidth-1 and flatMap it
         */
        return (x - 1) * boardWidth + (y - 1);
    }

    private MathUtils() {
    }
}
