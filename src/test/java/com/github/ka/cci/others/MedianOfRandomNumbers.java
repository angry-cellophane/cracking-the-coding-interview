package com.github.ka.cci.others;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
page 72, 6th edition
Numbers are randomly generated and stored into an (expanding) array. How would
you keep track ofthe median?
 */
public class MedianOfRandomNumbers {

    /*
        Create two heaps: one keeps < median, another keeps > median
        top of heap#1 is the largest element <= median, top of heap#2 is the smallest element >= median

        on new element add to heap1 -> rebalance heaps if required

        medium == to of smallest if heap1.size() != heap2.size() or (heap1.top + heap2.top) / 2


        rebalance:
        - if heap1's top > heap2's top then move heap1's top to heap2
        - make sure heap1 has at most +2 elements than heap2
        - make sure heap1.size() >= heap2.size()

        n - count of all numbers
        O(n*logn)
     */
    static class Medium {

        final PriorityQueue<Integer> smaller = new PriorityQueue<>((i1, i2) -> i2 - i1); // biggest goes first
        final PriorityQueue<Integer> greater = new PriorityQueue<>((i1, i2) -> i1 - i2); // smallest goes first

        void add(int n) {
            smaller.add(n);
            rebalance();
        }

        private void rebalance() {
            // make sure all elements in smaller < in greater
            while (!smaller.isEmpty() && !greater.isEmpty() && smaller.peek() > greater.peek()) {
                greater.add(smaller.poll());
            }

            // if smaller has 2 or more elements than great -> rebalance
            while (smaller.size() - greater.size() > 1) {
                greater.add(smaller.poll());
            }

            // make sure smaller.size is always >= greater.size()
            while (smaller.size() < greater.size()) {
                smaller.add(greater.poll());
            }
        }

        double medium() {
            return smaller.size() == greater.size()
                    ? (smaller.peek() + greater.peek()) / 2
                    : smaller.peek();
        }
    }

    @ParameterizedTest
    @MethodSource("arguments")
    void test(List<Integer> numbers, double expected) {
        var medium = new Medium();
        numbers.forEach(medium::add);

        var actual = medium.medium();
        System.out.println("input " + numbers + " expected " + expected + " actual " + actual);
        assertEquals(expected, actual);
    }

    static Stream<Arguments> arguments() {
        return Stream.of(
                Arguments.arguments(List.of(1,2,3,4,5), 3),
                Arguments.arguments(List.of(1), 1),
                Arguments.arguments(List.of(17, 2, 10), 10),
                Arguments.arguments(List.of(17, 4, 2, 10), 7)
        );
    }
}
