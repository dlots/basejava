package com.github.dlots.webapp.storage;

import com.github.dlots.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class UUIDSearchKeyMapStorage extends AbstractStorage<String> {
    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExists(String searchKey) {
        return storage.containsKey(searchKey);
    }

    @Override
    protected void doSave(String searchKey, Resume r) {
        storage.put(searchKey, r);
    }

    @Override
    protected void doUpdate(String searchKey, Resume r) {
        storage.put(searchKey, r);
    }

    @Override
    protected Resume doGet(String searchKey) {
        return storage.get(searchKey);
    }

    @Override
    protected void doDelete(String searchKey) {
        storage.remove(searchKey);
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
