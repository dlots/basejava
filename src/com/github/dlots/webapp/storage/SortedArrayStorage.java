package com.github.dlots.webapp.storage;

import com.github.dlots.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    protected Integer getSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected void insertAt(int index, Resume r) {
        int numMoved = size - index;
        if (numMoved > 0) {
            // Shift the right part of the array to the right by one position.
            System.arraycopy(storage, index, storage, index + 1, size - index);
        }
        storage[index] = r;
    }

    @Override
    protected void deleteFromArrayAt(int index) {
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            // Shift the right part of the array except last element to the left by one position.
            System.arraycopy(storage, index + 1, storage, index, numMoved);
        }
    }
}
