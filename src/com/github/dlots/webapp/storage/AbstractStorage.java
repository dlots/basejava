package com.github.dlots.webapp.storage;

import com.github.dlots.webapp.exception.ExistsStorageException;
import com.github.dlots.webapp.exception.NotExistsStorageException;
import com.github.dlots.webapp.exception.StorageException;
import com.github.dlots.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {
    public void save(Resume r) {
        if (isStorageFull()) {
            throw new StorageException("Cannot save resume " + r.getUuid() + " (storage is full).", r.getUuid());
        }
        final Object searchKey = getNonExistingSearchKey(r.getUuid());
        doSave(searchKey, r);
    }

    public void update(Resume r) {
        final Object searchKey = getExistingSearchKey(r.getUuid());
        doUpdate(searchKey, r);
    }

    public Resume get(String uuid) {
        final Object searchKey = getExistingSearchKey(uuid);
        return doGet(searchKey);
    }

    public void delete(String uuid) {
        final Object searchKey = getExistingSearchKey(uuid);
        doDelete(searchKey);
    }

    /**
     * Default implementation uses linear search.
     * Override the method if there is a more optimal approach
     * for a concrete data structure, or it is not indexed.
     */
    protected abstract Object getSearchKey(String uuid);

    private Object getExistingSearchKey(String uuid) {
        final Object searchKey = getSearchKey(uuid);
        if (!isExists(searchKey)) {
            throw new NotExistsStorageException(uuid);
        }
        return searchKey;
    }

    private Object getNonExistingSearchKey(String uuid) {
        final Object searchKey = getSearchKey(uuid);
        if (isExists(searchKey)) {
            throw new ExistsStorageException(uuid);
        }
        return searchKey;
    }

    protected abstract boolean isExists(Object searchKey);

    protected abstract boolean isStorageFull();

    protected abstract void doSave(Object searchKey, Resume r);

    protected abstract void doUpdate(Object searchKey, Resume r);

    protected abstract Resume doGet(Object searchKey);

    protected abstract void doDelete(Object searchKey);
}
