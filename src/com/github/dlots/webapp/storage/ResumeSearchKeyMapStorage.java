package com.github.dlots.webapp.storage;

import com.github.dlots.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class ResumeSearchKeyMapStorage extends AbstractStorage<Resume> {
    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    protected Resume getSearchKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected boolean isExists(Resume searchKey) {
        return searchKey != null;
    }

    @Override
    protected void doSave(Resume searchKey, Resume r) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected void doUpdate(Resume searchKey, Resume r) {
        storage.replace(r.getUuid(), r);
    }

    @Override
    protected Resume doGet(Resume searchKey) {
        return searchKey;
    }

    @Override
    protected void doDelete(Resume searchKey) {
        storage.remove(searchKey.getUuid());
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
