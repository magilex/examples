package com.magilex.examples.java8.sort;

import java.util.Arrays;

import static com.magilex.examples.java8.Constants.padding;
import static com.magilex.examples.java8.Constants.sleepTime;
import static java.lang.System.out;
import static java.util.Arrays.stream;

/**
 * Created by marcocamacho on 9/29/16.
 */
public class Quicksort {

    static ConsoleDisplay display = ConsoleDisplay.newInstance(padding, sleepTime);

    public static void main(String[] args) {
        out.println("Tony Hoare's 1959 quicksort algorithm");

        Integer[] unsorted = new Integer[] {4,6,1,9,10,3,30,5};

        Quicksort quicksort = new Quicksort();
        quicksort.masterListener = QuicksortListener.QuicksortListenerFactory.create(new QuicksortConsoleDisplayListener());

        int count = 1;
        quicksort.init(unsorted);
    }

    QuicksortListener masterListener;

    public Integer[] init(Integer[] unsorted) {
        return partition(unsorted);
    }

    public static class PartitionIterationInfo {

        Integer [] ongoing;
        int pivotIdx, ongoingIdx, nextToPivotIdx, ongoingVal, pivotVal, nextToPivotVal;

        public PartitionIterationInfo(Integer[] ongoing, int pivotIdx, int pivotVal) {
            this.ongoing = ongoing;
            this.pivotIdx = pivotIdx;
            this.pivotVal = pivotVal;
        }

        public String[] ongoingCopy2() {
            return stream(Arrays.copyOf(ongoing, ongoing.length))
                    .map(String::valueOf).toArray(String[]::new);
        }
    }

    private Integer[] partition(Integer [] ongoing) {

        masterListener.notifyPartitionStarted();

        int lastIdx = ongoing.length - 1;
        int pivotVal = ongoing[lastIdx]; // Rightmost element

        PartitionIterationInfo iterationInfo = new PartitionIterationInfo(ongoing, lastIdx, pivotVal);

        for (int i = 0; i < iterationInfo.pivotIdx; i++) {

            iterationInfo.ongoingIdx = i;
            iterationInfo.ongoingVal = ongoing[i];

            boolean ongoingVal_GreaterThan_PivotVal = iterationInfo.ongoingVal > pivotVal;

            masterListener.notifyIteratioinStarted(iterationInfo, ongoingVal_GreaterThan_PivotVal);

            if (ongoingVal_GreaterThan_PivotVal) {

                iterationInfo.nextToPivotIdx = iterationInfo.pivotIdx - 1;
                iterationInfo.nextToPivotVal = ongoing[iterationInfo.nextToPivotIdx];

                masterListener.notifySwapNeeded(iterationInfo, i);

                if (iterationInfo.ongoingIdx == iterationInfo.nextToPivotIdx) {
                    swapDuo(iterationInfo);
                } else {
                    swapTrio(iterationInfo);
                }

                iterationInfo.pivotIdx = iterationInfo.nextToPivotIdx;
                i--; // Do not advance index
                iterationInfo.ongoingIdx = i + 1;
            }

            masterListener.notifyEndOfIteration();
        }

        masterListener.notifyEndOfPartition(iterationInfo);

        Integer[] left = new Integer[iterationInfo.pivotIdx]; // pivot is left out
        Integer[] rigth = new Integer[ongoing.length - (iterationInfo.pivotIdx + 1)];

        for (int i = 0; i < iterationInfo.pivotIdx; i++) {
            left[i] = ongoing[i];
        }

        int offset = iterationInfo.pivotIdx + 1;
        for (int i = 0; i < ongoing.length - offset; i++) {
            rigth[i] = ongoing[i + offset];
        }

        // If no more items (length = 0) stop partitioning
        return join(left.length > 0 ? partition(left) : left, pivotVal, rigth.length > 0 ? partition(rigth) : rigth);
    }

    private Integer[] join(Integer[] left, int pivot, Integer[] right) {
        Integer[] joined = new Integer[left.length + right.length + 1]; // + 1 is pivot

        for (int i = 0; i < left.length; i++) {
            joined[i] = left[i];
        }

        joined[left.length] = pivot;

        for (int i = left.length + 1; i < joined.length; i++) {
            joined[i] = right[i - (left.length + 1)];
        }

        out.print("Joined array: ");
        display.print(joined);
        out.println();

        return joined;
    }

    /** Swaps ongoingVal, nextToPivotVal and pivotVal such that:
    {n1, ongoingVal, n2,  nextToPivotVal, pivotVal, ...} -> {n1, nextToPivotVal, n2,  pivotVal, ongoingVal, ...}
    */
    private void swapTrio(PartitionIterationInfo iterationInfo) {
        iterationInfo.ongoing[iterationInfo.nextToPivotIdx] = iterationInfo.pivotVal;
        iterationInfo.ongoing[iterationInfo.ongoingIdx] = iterationInfo.nextToPivotVal;
        iterationInfo.ongoing[iterationInfo.pivotIdx] = iterationInfo.ongoingVal;
    }

    /** Swaps ongoingVal and pivotVal such that:
     {n1, ongoingVal, pivotVal, ...} -> {n1, pivotVal, ongoingVal, ...}
     */
    private void swapDuo(PartitionIterationInfo iterationInfo) {
        iterationInfo.ongoing[iterationInfo.ongoingIdx] = iterationInfo.pivotVal;
        iterationInfo.ongoing[iterationInfo.pivotIdx] = iterationInfo.ongoingVal;
    }

}
