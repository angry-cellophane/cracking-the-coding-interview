package com.github.ka.cci.linkedlist;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.github.ka.cci.linkedlist.LinkedListUtils.createList;
import static org.junit.jupiter.api.Assertions.assertEquals;

/*
p 94, 6th edition

2.2
Return Kth to Last: Implement an algorithm to find the kth to last element of a singly linked list.
Hints: #8, #25, #47, #67, # 726
 */
public class ReturnKLast {

    /*
        k = 3
        1 -> 2 -> 3 -> 4 => 2
                      p1
            p2
        diff = 2
        return diff == k ? p2 when p1.next == null : null

        O(n) time
        O(1) space
     */
    static Integer findKLast(ListNode head, int k) {
        if (head == null || k < 1) return null;

        var p1 = head;
        var p2 = head;
        int diff = 1;

        while (p1.next != null) {
            p1 = p1.next;
            if (diff == k) {
                p2 = p2.next;
            } else {
                diff++;
            }
        }

        return diff == k ? p2.val : null;
    }

    @ParameterizedTest
    @MethodSource("arguments")
    void test(ListNode head, int k, Integer expected) {
        var actual = findKLast(head, k);

        System.out.println("expected = " + expected + ", actual = " + actual);
        assertEquals(expected, actual);
    }

    static Stream<Arguments> arguments() {
        return Stream.of(
                Arguments.arguments(createList(), 2, null),
                Arguments.arguments(createList(1,2,3), 2, 2),
                Arguments.arguments(createList(1), 1, 1),
                Arguments.arguments(createList(1, 2), 1, 2),
                Arguments.arguments(createList(1, 2, 3, 4, 5), 5, 1),
                Arguments.arguments(createList(1, 2, 3, 4, 5), 6, null)
        );
    }
}
