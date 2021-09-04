package com.github.ka.cci.arrays;

import com.github.ka.cci.others.MedianOfRandomNumbers;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
page 73, 6th edition

Given two sorted arrays, find the number of elements in common. The arrays are the same length
and each has all distinct elements.
 */
public class FindCommonElementsInSortedArrays {

    /*
        Use merge sort-ish approach.
        var result = [];
        walk through l1 and l2 until reach end of any of them:
            if elements equal add to result, increment both indices
            if l1 element > l2, increment l2's index
            if otherwise increment l1's index

        n - l1.size()
        m - l2.size();
        O(n + m)
     */
    static List<Integer> findCommonElements(List<Integer> l1, List<Integer> l2) {
        if (l1 == null || l2 == null || l1.isEmpty() || l2.isEmpty()) return List.of();

        var result = new ArrayList<Integer>();
        int i=0;
        int j=0;
        while (i < l1.size() && j < l2.size()) {
            if (l1.get(i).equals(l2.get(j))) {
                result.add(l1.get(i));
                i++;
                j++;
            } else if (l1.get(i) > l2.get(j)) {
                j++;
            } else {
                i++;
            }
        }

        return result;
    }

    @ParameterizedTest
    @MethodSource("arguments")
    void test(List<Integer> l1, List<Integer> l2, List<Integer> expected) {

        var actual = findCommonElements(l1, l2);
        assertEquals(expected, actual);
    }

    static Stream<Arguments> arguments() {
        return Stream.of(
                Arguments.arguments(List.of(1,5,10), List.of(1,10,12), List.of(1,10)),
                Arguments.arguments(List.of(12), List.of(1,10,12), List.of(12)),
                Arguments.arguments(List.of(12,24), List.of(), List.of()),
                Arguments.arguments(List.of(12, 24, 27, 30, 33, 45), List.of(1,2,3,4,5,6,7,24,25,26, 30, 44, 45, 46), List.of(24, 30, 45))
        );
    }
}
