package com.magilex.examples.java8.sort;

import static com.magilex.examples.java8.Constants.padding;
import static com.magilex.examples.java8.Constants.sleepTime;
import static java.lang.System.out;

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

        quicksort.init(unsorted);
    }

    QuicksortListener masterListener;

    public Integer[] init(Integer[] unsorted) {
        return partition(unsorted);
    }

    public static class PartitionIterationInfo {

        Integer [] ongoing;
        int pivotIdx, ongoingIdx, ongoingVal, pivotVal;

        public PartitionIterationInfo(Integer[] ongoing, int pivotIdx, int pivotVal) {
            this.ongoing = ongoing;
            this.pivotIdx = pivotIdx;
            this.pivotVal = pivotVal;
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

            masterListener.notifyCycleStarted(iterationInfo, ongoingVal_GreaterThan_PivotVal);

            if (ongoingVal_GreaterThan_PivotVal) {

                masterListener.notifySwapNeeded(iterationInfo, i);

                int nextToPivotIdx = iterationInfo.pivotIdx - 1;
                int nextToPivot = ongoing[nextToPivotIdx];
                if (iterationInfo.ongoingIdx == nextToPivotIdx) {
                    swap(iterationInfo);
                } else {
                    swap(iterationInfo, nextToPivot, nextToPivotIdx);
                }

                iterationInfo.pivotIdx = nextToPivotIdx;
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

        out.println("Joined array:");
        display.print(joined);
        return joined;
    }

    /** Swaps values E.g.
    {n1, oingoingVal, n2,  nextToPivotVal, pivotVal, ...} -> {n1, nextToPivotVal, n2,  pivotVal, oingoingVal, ...}
    */
    private void swap(PartitionIterationInfo iterationInfo, int nextToPivot, int nextToPivotIdx) {
        iterationInfo.ongoing[nextToPivotIdx] = iterationInfo.pivotVal;
        iterationInfo.ongoing[iterationInfo.ongoingIdx] = nextToPivot;
        iterationInfo.ongoing[iterationInfo.pivotIdx] = iterationInfo.ongoingVal;
    }

    private void swap(PartitionIterationInfo iterationInfo) {
        iterationInfo.ongoing[iterationInfo.ongoingIdx] = iterationInfo.pivotVal;
        iterationInfo.ongoing[iterationInfo.pivotIdx] = iterationInfo.ongoingVal;
    }

}
