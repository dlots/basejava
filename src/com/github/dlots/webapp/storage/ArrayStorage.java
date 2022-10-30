package com.github.dlots.webapp.storage;

import com.github.dlots.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private final Resume[] storage = new Resume[10000];
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
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(r.getUuid())) {
                System.out.println("Cannot save resume with uuid " + r.getUuid() + " (uuid already exists).");
                return;
            }
        }
        storage[size] = r;
        size++;
    }

    public void update(Resume r) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(r.getUuid())) {
                storage[i] = r;
                return;
            }
        }
        System.out.println("Tried to update resume with uuid " + r.getUuid() + " but it does not exist.");
    }

    public Resume get(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return storage[i];
            }
        }
        System.out.println("Cannot get resume with uuid " + uuid + " (does not exist).");
        return null;
    }

    public void delete(String uuid) {
        int indexToDelete = 0;
        for (; indexToDelete < size; indexToDelete++) {
            if (storage[indexToDelete].getUuid().equals(uuid)) {
                break;
            }
        }
        if (indexToDelete == size) {
            System.out.println("Cannot delete resume with uuid " + uuid + " (does not exist).");
            return;
        }
        size--;
        storage[indexToDelete] = storage[size];
        storage[size] = null;
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
}
