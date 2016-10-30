package com.magilex.examples.algorithms.sorting.quicksort;

import java.util.List;

import static java.util.Arrays.asList;

/**
 * Created by marcocamacho on 10/24/16.
 */
public interface QuicksortListener {

    void notifyPartitionStarted(int pivotVal);

    void notifyIterationStarted(Quicksort.PartitionIterationInfo iterationInfo, boolean ongoingVal_GreaterThan_PivotVal);

    void notifySwapNeeded(Quicksort.PartitionIterationInfo iterationInfo, int i);

    void notifyEndOfIteration();

    void notifyIterationCycleEnded(Quicksort.PartitionIterationInfo iterationInfo);

    void notifyPartitionEnded(Integer[] joined);

    class QuicksortListenerFactory {

        private QuicksortListenerFactory() {}

        public static QuicksortListener create(QuicksortListener... listenerParams) {

            return new QuicksortListener() {
                private List<QuicksortListener> listeners = asList(listenerParams);

                @Override
                public void notifyPartitionStarted(int pivotVal) {
                    listeners.forEach(listener -> listener.notifyPartitionStarted(pivotVal));
                }

                @Override
                public void notifyIterationStarted(Quicksort.PartitionIterationInfo iterationInfo, boolean ongoingVal_GreaterThan_PivotVal) {
                    listeners.forEach(listener -> listener.notifyIterationStarted(iterationInfo, ongoingVal_GreaterThan_PivotVal));
                }

                @Override
                public void notifySwapNeeded(Quicksort.PartitionIterationInfo iterationInfo, int i) {
                    listeners.forEach(listener -> listener.notifySwapNeeded(iterationInfo, i));
                }

                @Override
                public void notifyEndOfIteration() {
                    listeners.forEach(QuicksortListener::notifyEndOfIteration);
                }

                @Override
                public void notifyIterationCycleEnded(Quicksort.PartitionIterationInfo iterationInfo) {
                    listeners.forEach(listener ->  listener.notifyIterationCycleEnded(iterationInfo));
                }

                @Override
                public void notifyPartitionEnded(Integer[] joined) {
                    listeners.forEach(listener ->  listener.notifyPartitionEnded(joined));
                }
            };
        }

    }
}
