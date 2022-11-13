package com.github.dlots.webapp.storage;

import com.github.dlots.webapp.exception.StorageException;
import com.github.dlots.webapp.model.Resume;

import java.util.Arrays;
import java.util.stream.Stream;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
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
    protected boolean isExists(Integer searchKey) {
        return searchKey >= 0;
    }

    @Override
    protected void doSave(Integer searchKey, Resume r) {
        if (size == storage.length) {
            throw new StorageException("Cannot save resume " + r.getUuid() + " (storage is full).", r.getUuid());
        }
        int index = searchKey;
        index = -index - 1;
        insertAt(index, r);
        size++;
    }

    @Override
    protected void doUpdate(Integer searchKey, Resume r) {
        storage[searchKey] = r;
    }

    @Override
    protected Resume doGet(Integer searchKey) {
        return storage[searchKey];
    }

    @Override
    protected void doDelete(Integer searchKey) {
        deleteFromArrayAt(searchKey);
        size--;
        storage[size] = null;
    }

    @Override
    protected abstract Integer getSearchKey(String uuid);

    protected abstract void insertAt(int index, Resume r);

    protected abstract void deleteFromArrayAt(int index);
}
