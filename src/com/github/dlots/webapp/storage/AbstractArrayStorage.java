package com.github.dlots.webapp.storage;

import com.github.dlots.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;

    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public int size() {
        return size;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public Resume get(String uuid) {
        final int index = getIndex(uuid);
        if (index < 0) {
            System.out.println("Cannot get resume " + uuid + " (does not exist).");
            return null;
        }
        return storage[index];
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public void update(Resume r) {
        final int index = getIndex(r.getUuid());
        if (index < 0) {
            System.out.println("Cannot update resume " + r.getUuid() + " (does not exist).");
            return;
        }
        storage[index] = r;
    }

    public void save(Resume r) {
        if (size == storage.length) {
            System.out.println("Cannot save resume " + r.getUuid() + " (storage is full).");
            return;
        }
        int index = getIndex(r.getUuid());
        if (index >= 0) {
            System.out.println("Cannot save resume " + r.getUuid() + " (already exists).");
            return;
        }
        index = - index - 1;
        insertAt(index, r);
        size++;
    }

    public void delete(String uuid) {
        final int index = getIndex(uuid);
        if (index < 0) {
            System.out.println("Cannot delete resume " + uuid + " (does not exist).");
            return;
        }
        deleteAt(index);
        size--;
        storage[size] = null;
    }

    protected abstract int getIndex(String uuid);

    protected abstract void insertAt(int index, Resume r);

    protected abstract void deleteAt(int index);
}
