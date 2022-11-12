package com.github.dlots.webapp.storage;

import com.github.dlots.webapp.model.Resume;

import java.util.Arrays;
import java.util.stream.Stream;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10000;

    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public Stream<Resume> getStream() {
        return Arrays.stream(Arrays.copyOf(storage, size));
    }

    @Override
    protected boolean isExists(Object searchKey) {
        return (Integer) searchKey >= 0;
    }

    @Override
    protected boolean isStorageFull() {
        return size == storage.length;
    }

    @Override
    protected void doSave(Object searchKey, Resume r) {
        int index = (Integer) searchKey;
        index = -index - 1;
        insertAt(index, r);
        size++;
    }

    @Override
    protected void doUpdate(Object searchKey, Resume r) {
        storage[(Integer) searchKey] = r;
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return storage[(Integer) searchKey];
    }

    @Override
    protected void doDelete(Object searchKey) {
        deleteFromArrayAt((Integer) searchKey);
        size--;
        storage[size] = null;
    }

    @Override
    protected abstract Integer getSearchKey(String uuid);

    protected abstract void insertAt(int index, Resume r);

    protected abstract void deleteFromArrayAt(int index);
}
