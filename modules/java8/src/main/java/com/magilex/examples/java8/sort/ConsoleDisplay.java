package com.magilex.examples.java8.sort;

import com.magilex.examples.java8.ClTextTranslator;
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
        out.println("------------------------------------------------------------");
        out.println("Partitioning...");
        sleep(tick);
    }

    public void printPartitionFinished() {
        out.print("\r");
        out.println("Partitioning completed");
        sleep(tick);
    }

    public void print(Integer[] array, Integer pivotPosition, Integer ongoingIndex) {
        out.println (
                IntStream.range(0, array.length)
                        .mapToObj(i -> {
                            String val = array[i].toString() ;
                            val = i == pivotPosition ? "*" + val : val;
                            val = i == ongoingIndex ? "_" + val : val;
                            return rightPad(val, padding, Constants.PAD_CHAR);
                        }).collect(joining(""))
        );
        sleep(tick);
    }

    public void print(Integer[] array) {
        out.println (
                IntStream.range(0, array.length)
                        .mapToObj(i -> {
                            String val = array[i].toString() ;
                            return rightPad(val, padding, Constants.PAD_CHAR);
                        }).collect(joining(""))
        );
    }

    public void displaySwap(Integer [] arrInProgress, int pivotIdx, int currentIdx) {
        sleep(tick);
        //out.print("\r");out.print("\r");
        clTextTranslator.translateLeft(String.valueOf(arrInProgress[pivotIdx]), pivotIdx, 1);
        out.print("\r");
        clTextTranslator.translateLeft(String.valueOf(arrInProgress[pivotIdx - 1]), pivotIdx - 1, pivotIdx - currentIdx - 1);
        out.print("\r");
        clTextTranslator.translateRight(String.valueOf(arrInProgress[currentIdx]), currentIdx, pivotIdx - currentIdx);
    }

    public void pause() {
        sleep(tick);
    }

    public void printPivotVsOngoingComparationEval(int ongoing, int pivot, boolean ongoingVal_GreaterThan_PivotVal) {
        out.print("\rOngoing element (" + ongoing + ") is greater than pivot (" + pivot + ")? ");
        sleep(tick);
        out.println(toStringYesNo(ongoingVal_GreaterThan_PivotVal));
        sleep(tick);
    }

}
