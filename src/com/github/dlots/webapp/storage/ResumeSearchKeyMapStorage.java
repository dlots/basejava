package com.github.dlots.webapp.storage;

import com.github.dlots.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class ResumeSearchKeyMapStorage extends AbstractStorage {
    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    protected Resume getSearchKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected boolean isExists(Object searchKey) {
        return searchKey != null;
    }

    @Override
    protected boolean isStorageFull() {
        return false;
    }

    @Override
    protected void doSave(Object searchKey, Resume r) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected void doUpdate(Object searchKey, Resume r) {
        storage.replace(r.getUuid(), r);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return (Resume) searchKey;
    }

    @Override
    protected void doDelete(Object searchKey) {
        storage.remove(((Resume) searchKey).getUuid());
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
