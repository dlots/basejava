package com.github.dlots.webapp.storage;

import com.github.dlots.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class UUIDSearchKeyMapStorage extends AbstractStorage {
    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExists(Object searchKey) {
        return storage.containsKey((String) searchKey);
    }

    @Override protected boolean isStorageFull() {
        return false;
    }

    @Override
    protected void doSave(Object searchKey, Resume r) {
        storage.put((String) searchKey, r);
    }

    @Override
    protected void doUpdate(Object searchKey, Resume r) {
        storage.put((String) searchKey, r);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return storage.get((String) searchKey);
    }

    @Override
    protected void doDelete(Object searchKey) {
        storage.remove((String) searchKey);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Stream<Resume> getStream() {
        return storage.values().stream();
    }

    @Override
    public int size() {
        return storage.size();
    }
}
