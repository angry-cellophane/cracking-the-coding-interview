package com.github.ka.cci.linkedlist;

public class ListNode {
    final int val;
    ListNode next;

    public ListNode(int val) {
        this.val = val;
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();
        sb.append(val);
        var next = this.next;
        while (next != null) {
            sb.append(" -> ").append(next.val);
            next = next.next;
        }

        return sb.toString();
    }
}
