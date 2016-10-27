package com.magilex.examples.java8.sort;

import java.util.List;

import static java.util.Arrays.asList;

/**
 * Created by marcocamacho on 10/24/16.
 */
public interface QuicksortListener {

    void notifyPartitionStarted();

    void notifyCycleStarted(Quicksort.PartitionIterationInfo iterationInfo, boolean ongoingVal_GreaterThan_PivotVal);

    void notifySwapNeeded(Quicksort.PartitionIterationInfo iterationInfo, int i);

    void notifyEndOfIteration();

    void notifyEndOfPartition(Quicksort.PartitionIterationInfo iterationInfo);

    class QuicksortListenerFactory {

        private QuicksortListenerFactory() {}

        public static QuicksortListener create(QuicksortListener... listenerParams) {

            return new QuicksortListener() {
                private List<QuicksortListener> listeners = asList(listenerParams);

                @Override
                public void notifyPartitionStarted() {
                    listeners.forEach(QuicksortListener::notifyPartitionStarted);
                }

                @Override
                public void notifyCycleStarted(Quicksort.PartitionIterationInfo iterationInfo, boolean ongoingVal_GreaterThan_PivotVal) {
                    listeners.forEach(listener -> listener.notifyCycleStarted(iterationInfo, ongoingVal_GreaterThan_PivotVal));
                }

                @Override
                public void notifySwapNeeded(Quicksort.PartitionIterationInfo iterationInfo, int i) {
                    listeners.forEach(listener -> listener.notifySwapNeeded(iterationInfo, i));
                }

                @Override
                public void notifyEndOfIteration() {
                    listeners.forEach(listener -> listener.notifyEndOfIteration());
                }

                @Override
                public void notifyEndOfPartition(Quicksort.PartitionIterationInfo iterationInfo) {
                    listeners.forEach(listener ->  listener.notifyEndOfPartition(iterationInfo));
                }

            };
        }

    }
}
