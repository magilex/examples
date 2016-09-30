package com.magilex.examples.sort;


import java.util.*;
import java.util.stream.Collectors;

import static java.lang.System.out;

/**
 * Created by marcocamacho on 9/29/16.
 */
public class Quicksort {

    public static void main(String[] args) {
        out.println("Tony Hoare's 1959 quicksort algorithm");

        Integer[] unsorted = new Integer[] {4,6,1,9,10,3,30,5};

        out.println("Final result:");
        print(new Quicksort().init(unsorted));
    }

    public Integer[] init(Integer[] unsorted) {
        return partition(unsorted);
    }

    private Integer[] partition(Integer [] unsorted) {
        int lastIndex = unsorted.length - 1;
        int lastElement = lastIndex;

        int pivot = unsorted[lastElement];
        int pivotPosition = lastElement;
        for (int i = 0; i < pivotPosition; i++) {
            if (unsorted[i] > pivot) {
                int nextToPivotPos = pivotPosition - 1;
                int nextToPivot = unsorted[nextToPivotPos];
                swap(unsorted[i], i, nextToPivot, pivotPosition, unsorted);
                unsorted[nextToPivotPos] = pivot;
                pivotPosition = nextToPivotPos;
                i--;
            }
            print(unsorted);
        }

        Integer[] left = new Integer[pivotPosition + 1];
        Integer[] rigth = new Integer[unsorted.length - (pivotPosition + 1)];

        for (int i = 0; i <= pivotPosition; i++) {
            left[i] = unsorted[i];
        }

        int offset = pivotPosition + 1;
        for (int i = 0; i < unsorted.length - offset; i++) {

            rigth[i] = unsorted[i + offset];
        }

        join(partition(left), partition(rigth));

        return unsorted;
    }

    private Integer[] join(Integer[] left, Integer[] right) {
        Integer[] joined = new Integer[left.length + right.length];

        for (int i = 0; i < left.length; i++) {
            joined[i] = left[i];
        }

        for (int i = left.length; i < joined.length; i++) {
            joined[i] = right[i];
        }

        return joined;
    }

    private void swap(int x, int idxX, int y, int idxY, Integer[] unsorted) {
        unsorted[idxX] = y;
        unsorted[idxY] = x;
    }

    private static void print(Integer[] array) {
        out.println(Arrays.asList(array)
                .stream()
                .map(Object::toString)
                .collect(Collectors.joining(", "))
        );

    }
}
