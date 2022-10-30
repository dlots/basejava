package com.github.dlots.webapp.storage;

import com.github.dlots.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {
    @Override
    protected int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void insertAt(int index, Resume r) {
        storage[size] = r;
    }

    @Override
    protected void deleteAt(int index) {
        storage[index] = storage[size - 1];
    }
}
