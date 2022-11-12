package com.github.dlots.webapp.storage;

import com.github.dlots.webapp.exception.ExistsStorageException;
import com.github.dlots.webapp.exception.NotExistsStorageException;
import com.github.dlots.webapp.exception.StorageException;
import com.github.dlots.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractStorage implements Storage {
    protected static final Comparator<Resume> RESUME_COMPARATOR =
            Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);

    @Override
    public void save(Resume r) {
        if (isStorageFull()) {
            throw new StorageException("Cannot save resume " + r.getUuid() + " (storage is full).", r.getUuid());
        }
        final Object searchKey = getNonExistingSearchKey(r.getUuid());
        doSave(searchKey, r);
    }

    @Override
    public void update(Resume r) {
        final Object searchKey = getExistingSearchKey(r.getUuid());
        doUpdate(searchKey, r);
    }

    @Override
    public Resume get(String uuid) {
        final Object searchKey = getExistingSearchKey(uuid);
        return doGet(searchKey);
    }

    @Override
    public List<Resume> getAllSorted() {
        return getStream().sorted(RESUME_COMPARATOR).collect(Collectors.toList());
    }

    @Override
    public void delete(String uuid) {
        final Object searchKey = getExistingSearchKey(uuid);
        doDelete(searchKey);
    }

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

    protected abstract Object getSearchKey(String uuid);

    protected abstract boolean isExists(Object searchKey);

    protected abstract boolean isStorageFull();

    protected abstract void doSave(Object searchKey, Resume r);

    protected abstract void doUpdate(Object searchKey, Resume r);

    protected abstract Resume doGet(Object searchKey);

    protected abstract void doDelete(Object searchKey);

    protected abstract Stream<Resume> getStream();
}
