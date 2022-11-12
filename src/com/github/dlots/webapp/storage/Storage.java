package com.github.dlots.webapp.storage;

import com.github.dlots.webapp.model.Resume;

import java.util.List;

public interface Storage {
    void clear();

    void save(Resume r);

    void update(Resume r);

    Resume get(String uuid);

    void delete(String uuid);

    /**
     * @return array, contains only Resumes in storage (without null), sorted by full name.
     */
    List<Resume> getAllSorted();

    int size();
}
