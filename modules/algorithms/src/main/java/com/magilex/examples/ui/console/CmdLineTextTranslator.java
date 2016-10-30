package com.magilex.examples.ui.console;

import com.magilex.examples.algorithms.sorting.quicksort.Constants;
import org.apache.commons.lang3.StringUtils;

import java.util.function.*;
import java.util.stream.IntStream;

import static com.magilex.examples.Helper.sleep;
import static java.lang.System.out;

/**
 * Created by marcocamacho on 10/2/16.
 */
public class CmdLineTextTranslator {

    private int tick;
    private int padding;

    public CmdLineTextTranslator(int tick, int padding) {
        this.tick = tick;
        this.padding = padding;
    }

    public static void main(String[] args) {

        CmdLineTextTranslator textTranslator = new CmdLineTextTranslator(900, 3);

        textTranslator.translateRight("rt", 0, 10);
        out.println("");
        textTranslator.translateLeft("lf", 10, 10);
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
                    if (i != initPos) ConsoleHelper.removeLine();

                    out.print(StringUtils.leftPad(string, (i * padding) + string.length(), Constants.PAD_CHAR));
                    sleep(tick);
                });
    }


    static IntFunction<IntBinaryOperator> emit = i -> (startR, endR) -> i;

    static IntFunction<IntBinaryOperator> reverse = i -> (startR, endR) -> endR - (i - startR + 1);

    static IntFunction<IntBinaryOperator> tri = x -> (y, z) -> x+y+z;
    static BiFunction<Integer, Integer, IntBinaryOperator> quad = (x, y) -> (z, a) -> x+y+z;
    static BiFunction<Integer, Integer, BiFunction<Integer, Integer, IntUnaryOperator>> penta = (x, y) -> (z, a) -> (b) -> x+y+z+a+b;

}
