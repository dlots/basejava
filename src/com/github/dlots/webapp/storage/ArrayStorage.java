package com.github.dlots.webapp.storage;

import com.github.dlots.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private static final int STORAGE_LIMIT = 10000;

    private final Resume[] storage = new Resume[STORAGE_LIMIT];
    private int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume r) {
        if (size == storage.length) {
            System.out.println("Cannot save resume with uuid " + r.getUuid() + " (storage is full).");
            return;
        }
        if (getIndex(r.getUuid()) > -1) {
            System.out.println("Cannot save resume with uuid " + r.getUuid() + " (already exists).");
            return;
        }
        storage[size] = r;
        size++;
    }

    public void update(Resume r) {
        final int index = getIndex(r.getUuid());
        if (index > -1) {
            storage[index] = r;
            return;
        }
        System.out.println("Cannot update resume with uuid " + r.getUuid() + " (does not exist).");
    }

    public Resume get(String uuid) {
        final int index = getIndex(uuid);
        if (index > -1) {
            return storage[index];
        }
        System.out.println("Cannot get resume with uuid " + uuid + " (does not exist).");
        return null;
    }

    public void delete(String uuid) {
        final int index = getIndex(uuid);
        if (index > -1) {
            size--;
            storage[index] = storage[size];
            storage[size] = null;
            return;
        }
        System.out.println("Cannot delete resume with uuid " + uuid + " (does not exist).");
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    private int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
