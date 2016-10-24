package com.magilex.examples.java8;

import java.util.function.*;
import java.util.stream.IntStream;

import static com.magilex.examples.java8.sort.Helper.sleep;
import static java.lang.System.out;
import static org.apache.commons.lang3.StringUtils.leftPad;

/**
 * Created by marcocamacho on 10/2/16.
 */
public class ClTextTranslator {

    private static char PAD_CHAR = '.';

    private int tick;
    private int padding;

    public ClTextTranslator(int tick, int padding) {
        this.tick = tick;
        this.padding = padding;
    }

    public static void main(String[] args) {

        ClTextTranslator clTextTranslator = new ClTextTranslator(500, 3);

        clTextTranslator.translateRight("abc", 0, 10);
        out.println("");
        clTextTranslator.translateLeft("def", 10, 10);

    }

    public void translateRight(String string, int initPos, int length) {
        int startRange = initPos;
        int endRange = initPos + length + 1;
        translate(string, initPos, startRange, endRange, emit);
    }

    public void translateLeft(String string, int initPos, int length) {
        if (length > initPos) throw new IllegalStateException("length cannot be greater than init position");

        int startRange = initPos - length;
        int endRange = initPos + 1;
        translate(string, initPos, startRange, endRange, reverse);
    }

    private void translate(String string, int initPos, int startRange, int endRange, IntFunction<IntBinaryOperator> map) {
        IntStream.range(startRange, endRange)
                .map(i -> map.apply(i).applyAsInt(startRange, endRange))
                .forEach(i -> {
                    if (i != initPos) out.print("\r");
                    out.print(leftPad(string, (i * padding) + string.length(), PAD_CHAR));
                    sleep(tick);
                });
    }

    static IntFunction<IntBinaryOperator> emit = i -> (startR, endR) -> i;

    static IntFunction<IntBinaryOperator> reverse = i -> (startR, endR) -> endR - (i - startR + 1);

    static IntFunction<IntBinaryOperator> tri = x -> (y, z) -> x+y+z;
    static BiFunction<Integer, Integer, IntBinaryOperator> quad = (x, y) -> (z, a) -> x+y+z;
    static BiFunction<Integer, Integer, BiFunction<Integer, Integer, IntUnaryOperator>> penta = (x, y) -> (z, a) -> (b) -> x+y+z+a+b;

}