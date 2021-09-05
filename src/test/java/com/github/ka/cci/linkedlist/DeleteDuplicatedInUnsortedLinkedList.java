package com.github.ka.cci.linkedlist;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashSet;
import java.util.stream.Stream;

import static com.github.ka.cci.linkedlist.LinkedListUtils.createList;
import static org.junit.jupiter.api.Assertions.assertTrue;

/*
p 94, 6th edition
2.1
Remove Dups: Write code to remove duplicates from an unsorted li nked list.
FOLLOW UP
How would you solve this problem if a temporary buffer is not allowed?
Hints: #9, #40
 */
public class DeleteDuplicatedInUnsortedLinkedList {

    /*
        1 -> 1 -> 2 -> 3
        for every node:
            start runner from node.next to delete node with val == node.val
                runner to check node.next != null && node.nex.val == node.val -> node.next = node.next.next

         O(n^2) time, O(1) space
     */
    static ListNode deleteDuplicatesWithRunner(ListNode head) {
        if (head == null) return head;

        var node = head;
        while (node != null && node.next != null) {
            var runner = node;
            while (runner.next != null) {
                if (runner.next.val == node.val) {
                    runner.next = runner.next.next;
                } else {
                    runner = runner.next;
                }
            }
            node = node.next;
        }
        return head;
    }

    /*
        createList(1,2,2,3,2) -> createList(1,2,3))
        seen = set()
        walk through list while node.next != null
        if node.next in seen:
            node.next = node.next.next;
        seen.add(node)
        node = node.next;

        O(n) time, O(n) space
     */
    static ListNode deleteDuplicatesWithHasSet(ListNode head) {
        if (head == null) return null;

        var node = head;
        var seen = new HashSet<Integer>();
        seen.add(node.val);
        while (node.next != null) {
            if (seen.contains(node.next.val)) {
                node.next = node.next.next;
            } else {
                seen.add(node.next.val);
                node = node.next;
            }
        }
        return head;
    }

    @ParameterizedTest
    @MethodSource("arguments")
    void testWithRunner(ListNode head, ListNode expected) {
        var actual = deleteDuplicatesWithRunner(head);

        System.out.println("expected = " + expected + ", actual = " + actual);
        assertTrue(LinkedListUtils.equals(expected, actual));
    }

    @ParameterizedTest
    @MethodSource("arguments")
    void testWithHashSet(ListNode head, ListNode expected) {
        var actual = deleteDuplicatesWithHasSet(head);

        System.out.println("expected = " + expected + ", actual = " + actual);
        assertTrue(LinkedListUtils.equals(expected, actual));
    }

    static Stream<Arguments> arguments() {
        return Stream.of(
                Arguments.arguments(createList(), createList()),
                Arguments.arguments(createList(1,2,3), createList(1,2,3)),
                Arguments.arguments(createList(1,2,2,3,2), createList(1,2,3)),
                Arguments.arguments(createList(1,1,1,2,3), createList(1,2,3)),
                Arguments.arguments(createList(1,2,3,3,3), createList(1,2,3))
        );
    }
}
