package com.magilex.examples.java8.sort;


import com.magilex.examples.java8.ClTextTranslator;

import java.util.function.IntFunction;
import java.util.stream.IntStream;

import static java.lang.System.out;
import static java.util.stream.Collectors.joining;
import static org.apache.commons.lang3.StringUtils.rightPad;

/**
 * Created by marcocamacho on 9/29/16.
 */
public class Quicksort {

    static int padding = 5;
    static int sleepTime = 900;

    ClTextTranslator clTextTranslator = new ClTextTranslator(sleepTime, padding);

    public static void main(String[] args) {
        out.println("Tony Hoare's 1959 quicksort algorithm");

        Integer[] unsorted = new Integer[] {4,6,1,9,10,3,30,5};

        //print(new Quicksort().init(unsorted), unsorted.length);
        Integer[] sorted = new Quicksort().init(unsorted);

        out.println("Final result:");
        print(sorted);
    }

    public Integer[] init(Integer[] unsorted) {
        return partition(unsorted);
    }

    private Integer[] partition(Integer [] unsorted) {
        out.println("Partitioning...");
        int lastIndex = unsorted.length - 1;
        int lastElement = lastIndex;

        int pivot = unsorted[lastElement];
        int pivotPosition = lastElement;
        int currentPosition = 0;
        for (int i = 0; i < pivotPosition; i++) {
            currentPosition = i;
            out.println("\r");
            print(unsorted, pivotPosition, currentPosition);

            if (unsorted[i] > pivot) {
                int nextToPivotPos = pivotPosition - 1;
                int nextToPivot = unsorted[nextToPivotPos];
                clTextTranslator.translateLeft(String.valueOf(pivot), pivotPosition, 1);
                out.println("\r");
                clTextTranslator.translateLeft(String.valueOf(unsorted[lastElement - 1]), pivotPosition - 1, pivotPosition - i - 1);
                out.println("\r");
                clTextTranslator.translateRight(String.valueOf(unsorted[i]), i, pivotPosition - i);
                swap(unsorted[i], i, nextToPivot, pivotPosition, unsorted);
                unsorted[nextToPivotPos] = pivot;
                pivotPosition = nextToPivotPos;
                i--;
                currentPosition = i + 1;
            }

            Helper.sleep(sleepTime);
        }

        out.println("");
        print(unsorted, pivotPosition, currentPosition);

        Integer[] left = new Integer[pivotPosition]; // pivot is left out
        Integer[] rigth = new Integer[unsorted.length - (pivotPosition + 1)];

        for (int i = 0; i < pivotPosition; i++) {
            left[i] = unsorted[i];
        }

        int offset = pivotPosition + 1;
        for (int i = 0; i < unsorted.length - offset; i++) {
            rigth[i] = unsorted[i + offset];
        }

        return join(left.length > 0 ? partition(left) : left, pivot, rigth.length > 0 ? partition(rigth) : rigth);
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

        return joined;
    }

    private void swap(int x, int idxX, int y, int idxY, Integer[] unsorted) {
        unsorted[idxX] = y;
        unsorted[idxY] = x;
    }

    private static void print(Integer[] array, Integer pivotPosition, Integer ongoingIndex) {
        out.println (
                IntStream.range(0, array.length)
                .mapToObj(i -> {
                    String val = array[i].toString() ;
                    val = i == pivotPosition ? "*" + val : val;
                    val = i == ongoingIndex ? "_" + val : val;
                    return rightPad(val, padding, ".");
                }).collect(joining(""))
        );

    }

    private static void print(Integer[] array) {
        out.println (
                IntStream.range(0, array.length)
                        .mapToObj(i -> {
                            String val = array[i].toString() ;
                            return rightPad(val, padding, ".");
                        }).collect(joining(""))
        );

    }

    static IntFunction<String> f = element -> rightPad(String.valueOf(element), padding);

    static QuadFunction<Integer[], Integer, Integer, Integer, String> f2 = (array, i, pivotPosition, ongoingIndex) -> {
        String val = array[i].toString() ;
        val = i == pivotPosition ? "*" + val : val;
        val = i == ongoingIndex ? "_" + val : val;
        return rightPad(val, padding);
    };

    interface QuadFunction<T, U, V, W, R> {
        R apply(T t, U u, V v, W w);
    }
}
