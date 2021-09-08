package com.github.ka.cci.others;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MultiIterator<T> {

    private final Queue<List<T>> lists;

    private List<T> currentList;
    private int index = -1;

    public MultiIterator(List<List<T>> lists) {
        this.lists = new LinkedList<>(lists);
    }


    public T next() {
        if (!hasNext()) {
            throw new IllegalStateException();
        }

        var element = currentList.get(index);
        index++;
        return element;
    }

    public boolean hasNext() {
        updateLists();
        return currentList != null;
    }

    private void updateLists() {
        if (currentList == null || index >= currentList.size()) {
            currentList = lists.isEmpty() ? null : lists.poll();
            index = 0;
        }
    }
}
