package com.magilex.examples.java8.sort;

import static com.magilex.examples.java8.Constants.*;

/**
 * Created by marcocamacho on 10/24/16.
 */
public class QuicksortConsoleDisplayListener implements QuicksortListener {

    static ConsoleDisplay display = ConsoleDisplay.newInstance(padding, sleepTime);

    @Override
    public void notifyPartitionStarted() {
        display.printPartitionStarted();
    }

    @Override
    public void notifyCycleStarted(Quicksort.PartitionIterationInfo iterationInfo, boolean ongoingVal_GreaterThan_PivotVal) {
        display.printPivotVsOngoingComparationEval(iterationInfo.ongoingVal, iterationInfo.pivotVal, ongoingVal_GreaterThan_PivotVal);
        display.print(iterationInfo.ongoing, iterationInfo.pivotIdx, iterationInfo.ongoingIdx);
    }

    @Override
    public void notifySwapNeeded(Quicksort.PartitionIterationInfo iterationInfo, int i) {
        display.displaySwap(iterationInfo.ongoing, iterationInfo.pivotIdx, iterationInfo.ongoingIdx);
    }

    @Override
    public void notifyEndOfIteration() {
        display.pause();
    }

    @Override
    public void notifyEndOfPartition(Quicksort.PartitionIterationInfo iterationInfo) {
        display.printPartitionFinished();
        display.print(iterationInfo.ongoing, iterationInfo.pivotIdx, iterationInfo.ongoingIdx);
    }


}
