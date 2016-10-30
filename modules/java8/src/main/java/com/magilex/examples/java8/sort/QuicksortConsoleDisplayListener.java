package com.magilex.examples.java8.sort;

import com.magilex.examples.java8.ConsoleHelper;

import static com.magilex.examples.java8.Constants.*;
import static java.lang.System.out;

/**
 * Created by marcocamacho on 10/24/16.
 */
public class QuicksortConsoleDisplayListener implements QuicksortListener {

    private static ConsoleDisplay display = ConsoleDisplay.newInstance(padding, sleepTime);

    @Override
    public void notifyPartitionStarted(int pivotVal) {
        display.printPartitionStarted(pivotVal);
    }

    @Override
    public void notifyIterationStarted(Quicksort.PartitionIterationInfo iterationInfo, boolean ongoingVal_GreaterThan_PivotVal) {
        ConsoleHelper.removeLine();
        display.print(iterationInfo.ongoing, iterationInfo.pivotIdx, iterationInfo.ongoingIdx);
        display.printPivotVsOngoingComparation(iterationInfo.ongoingVal, iterationInfo.pivotVal, ongoingVal_GreaterThan_PivotVal);
    }

    @Override
    public void notifySwapNeeded(Quicksort.PartitionIterationInfo iterationInfo, int i) {
        display.displaySwap(iterationInfo.ongoingCopy(),
                iterationInfo.pivotIdx, String.valueOf(iterationInfo.pivotVal),
                iterationInfo.nextToPivotIdx, String.valueOf(iterationInfo.nextToPivotVal),
                iterationInfo.ongoingIdx, String.valueOf(iterationInfo.ongoingVal));
    }

    @Override
    public void notifyEndOfIteration() {
        display.pause();
    }

    @Override
    public void notifyIterationCycleEnded(Quicksort.PartitionIterationInfo iterationInfo) {
        display.printPartitionFinished();
        display.print(iterationInfo.ongoing, iterationInfo.pivotIdx, -1);
        out.println();
    }

    @Override
    public void notifyPartitionEnded(Integer[] joined) {
        display.printJoinedArray(joined);
    }

}
