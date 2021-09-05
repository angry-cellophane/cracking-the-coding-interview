package com.github.ka.cci.linkedlist;

public class LinkedListUtils {

    static ListNode createList(int ... nums) {
        if (nums == null || nums.length == 0) return null;

        ListNode head = null;
        ListNode node = null;
        for (int i=0; i<nums.length; i++) {
            var newNode = new ListNode(nums[i]);
            if (node != null) {
                node.next = newNode;
            }
            node = newNode;
            if (head == null) head = newNode;
        }

        return head;
    }
    static boolean equals(ListNode l1, ListNode l2) {
        if (l1 == null && l2 == null) return true;
        if (l1 != null && l2 == null || l1 == null && l2 != null) return false;

        while (l1 != null && l2 != null) {
            if (l1.val != l2.val) return false;

            l1 = l1.next;
            l2 = l2.next;
        }

        return l1 == null && l2 == null;
    }
}
