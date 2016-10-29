package com.magilex.examples.java8.sort;

import com.magilex.examples.java8.ClTextTranslator;
import com.magilex.examples.java8.ConsoleHelper;
import com.magilex.examples.java8.Constants;

import java.util.stream.IntStream;

import static com.magilex.examples.java8.sort.Helper.sleep;
import static java.lang.System.out;
import static java.util.stream.Collectors.joining;
import static org.apache.commons.lang3.BooleanUtils.toStringYesNo;
import static org.apache.commons.lang3.StringUtils.rightPad;

/**
 * Created by marcocamacho on 10/23/16.
 */
public class ConsoleDisplay {

    public static ConsoleDisplay newInstance(int padding, int tick) {
        return new ConsoleDisplay(padding, tick);
    }

    private int padding;
    private int tick;
    private ClTextTranslator clTextTranslator;

    private ConsoleDisplay(int padding, int tick) {
        this.padding = padding;
        this.tick = tick;
        this.clTextTranslator = new ClTextTranslator(tick, padding);
    }

    public void printPartitionStarted() {
        out.println("------------------------------------------------------------------------------------");
        out.println("Partitioning...");
        sleep(tick);
    }

    public void printPartitionFinished() {
        ConsoleHelper.removeLine();
        out.println("Partitioning completed");
        sleep(tick);
    }

    public void print(Object[] array, Integer pivotIdx, Integer ongoingIndex) {
        print(array, pivotIdx, ongoingIndex, "*", ".");
    }

    public void print(Object[] array, Integer pivotIdx, Integer ongoingIdx, Integer nextToPivotIdx,
                      String pivotDecor, String ongoingDecor, String nextToPivotDecor) {
        out.print (
                IntStream.range(0, array.length)
                        .mapToObj(i -> {
                            String val = array[i].toString() ;
                            val = i == pivotIdx ? pivotDecor + val : val;
                            val = i == nextToPivotIdx ? nextToPivotDecor + val : val;
                            if (nextToPivotIdx != ongoingIdx)
                                val = i == ongoingIdx ? ongoingDecor + val : val;
                            return rightPad(val, padding, Constants.PAD_CHAR);
                        }).collect(joining(""))
        );
        //sleep(tick);
    }

    public void print(Object[] array, Integer pivotIdx, Integer ongoingIdx, String pivotDecor, String ongoingDecor) {
        out.print (
                IntStream.range(0, array.length)
                        .mapToObj(i -> {
                            String val = array[i].toString() ;
                            val = i == pivotIdx ? pivotDecor + val : val;
                            val = i == ongoingIdx ? ongoingDecor + val : val;
                            return rightPad(val, padding, Constants.PAD_CHAR);
                        }).collect(joining(""))
        );
        sleep(tick);
    }

    public void print(Object[] array) {
        out.print (
                IntStream.range(0, array.length)
                        .mapToObj(i -> {
                            String val = array[i].toString() ;
                            return rightPad(val, padding, Constants.PAD_CHAR);
                        }).collect(joining(""))
        );
    }

    public void displaySwap(String [] arrInProgress,
                            int pivotIdx, String pivotVal,
                            int nextToPivotIdx, String nextToPivotVal,
                            int ongoingIdx, String ongoingVal) {
        sleep(tick);
        print(arrInProgress, pivotIdx, ongoingIdx, nextToPivotIdx, "*", ".", ".");
        out.println();
        out.print(" ");

        sleep(tick);
        ConsoleHelper.removeLine();
        ConsoleHelper.moveCursorUp();
        ConsoleHelper.removeLine();
        arrInProgress[pivotIdx] = "";
        print(arrInProgress, pivotIdx, ongoingIdx, nextToPivotIdx, "_", ".", ".");
        out.println("");

        clTextTranslator.translateLeft("*" + pivotVal, pivotIdx, 1);
        ConsoleHelper.removeLine();
        ConsoleHelper.moveCursorUp();
        ConsoleHelper.removeLine();
        arrInProgress[pivotIdx - 1] = pivotVal;
        print(arrInProgress, pivotIdx, ongoingIdx, nextToPivotIdx, "_", ".", "*");
        out.println("");

        if (ongoingIdx != nextToPivotIdx) {
            clTextTranslator.translateLeft(nextToPivotVal, nextToPivotIdx, pivotIdx - ongoingIdx - 1);
            ConsoleHelper.removeLine();
            ConsoleHelper.moveCursorUp();
            ConsoleHelper.removeLine();
            arrInProgress[ongoingIdx] = nextToPivotVal;
            print(arrInProgress, pivotIdx, ongoingIdx, nextToPivotIdx, "_", ".", "*");
            out.println("");
        }

        clTextTranslator.translateRight(ongoingVal, ongoingIdx, pivotIdx - ongoingIdx);
        ConsoleHelper.removeLine();
        ConsoleHelper.moveCursorUp();
        ConsoleHelper.removeLine();
        arrInProgress[pivotIdx] = ongoingVal;

        print(arrInProgress, pivotIdx, ongoingIdx, nextToPivotIdx, ".", ".", "*");

    }

    public void pause() {
        sleep(tick);
    }

    public void printPivotVsOngoingComparationEval(int ongoing, int pivot, boolean ongoingVal_GreaterThan_PivotVal) {
        out.print("Ongoing element (" + ongoing + ") is greater than pivot (" + pivot + ")? ");
        sleep(tick);
        out.println(toStringYesNo(ongoingVal_GreaterThan_PivotVal));
        sleep(tick);
    }

}
