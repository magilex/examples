package com.magilex.examples.algorithms.quicksort;

import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;

import static java.lang.System.out;
import static java.util.Arrays.stream;

/**
 * Created by marcocamacho on 9/29/16.
 */
public class Quicksort {

    public static void main(String[] args) {
        out.println("A variation of Tony Hoare's 1959 quicksort algorithm");
        out.print("Main goal is to display the basis of how algorithm works ");
        out.print("and for the code readers to be able to understand it with least effort possible.");
        out.println();

        Integer[] unsorted = new Integer[] {4,6,1,9,10,5,3,30,5};

        Quicksort quicksort = new Quicksort();
        quicksort.masterListener = QuicksortListener.QuicksortListenerFactory.create(new QuicksortConsoleDisplayListener());

        quicksort.init(unsorted);
    }

    static class PartitionIterationInfo {

        Integer [] ongoing;
        int pivotIdx, ongoingIdx, nextToPivotIdx, ongoingVal, pivotVal, nextToPivotVal;

        public PartitionIterationInfo(Integer[] ongoing, int pivotIdx, int pivotVal) {
            this.ongoing = ongoing;
            this.pivotIdx = pivotIdx;
            this.pivotVal = pivotVal;
        }

        public String[] ongoingCopy() {
            return stream(Arrays.copyOf(ongoing, ongoing.length))
                    .map(String::valueOf).toArray(String[]::new);
        }

        public boolean isOngoingAndNextToPivotSame() {
            return ongoingIdx == nextToPivotIdx;
        }
    }

    QuicksortListener masterListener;

    public Integer[] init(Integer[] unsorted) {
        return partition(unsorted);
    }

    private Integer[] partition(Integer [] ongoing) {

        int lastIdx = ongoing.length - 1;
        int pivotVal = ongoing[lastIdx]; // Rightmost element

        masterListener.notifyPartitionStarted(pivotVal);

        PartitionIterationInfo iterationInfo = new PartitionIterationInfo(ongoing, lastIdx, pivotVal);

        for (int i = 0; i < iterationInfo.pivotIdx; i++) {

            iterationInfo.ongoingIdx = i;
            iterationInfo.ongoingVal = ongoing[i];

            boolean ongoingVal_GreaterThan_PivotVal = iterationInfo.ongoingVal > pivotVal;

            masterListener.notifyIterationStarted(iterationInfo, ongoingVal_GreaterThan_PivotVal);

            if (ongoingVal_GreaterThan_PivotVal) {

                iterationInfo.nextToPivotIdx = iterationInfo.pivotIdx - 1;
                iterationInfo.nextToPivotVal = ongoing[iterationInfo.nextToPivotIdx];

                masterListener.notifySwapNeeded(iterationInfo, iterationInfo.ongoingIdx);

                if (iterationInfo.isOngoingAndNextToPivotSame()) {
                    swapDuo(iterationInfo);
                } else {
                    swapTrio(iterationInfo);
                }

                iterationInfo.pivotIdx = iterationInfo.nextToPivotIdx;
                i--; // Do not advance index
                iterationInfo.ongoingIdx = i + 1; // Do advance ongoing index
            }

            masterListener.notifyEndOfIteration();
        }

        masterListener.notifyIterationCycleEnded(iterationInfo);

        Integer[] left = Arrays.copyOf(ongoing, iterationInfo.pivotIdx); // pivot is left out
        Integer[] right = Arrays.copyOfRange(ongoing, iterationInfo.pivotIdx + 1, ongoing.length);

        // If more than one item, partition, if only one item (length = 1) stop partitioning
        Integer[] joined = join(left.length > 1 ? partition(left) : left, pivotVal, right.length > 1 ? partition(right) : right);

        masterListener.notifyPartitionEnded(joined);

        return joined;
    }

    /** Joins left array, pivot element and right array
     */
    private Integer[] join(Integer[] left, int pivot, Integer[] right) {
        Integer[] leftPlusPivot = ArrayUtils.add(left, pivot);
        return ArrayUtils.addAll(leftPlusPivot, right);
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
