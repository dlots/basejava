package com.github.dlots.webapp.storage;

import com.github.dlots.webapp.exception.ExistsStorageException;
import com.github.dlots.webapp.exception.NotExistsStorageException;
import com.github.dlots.webapp.model.Resume;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<SearchKey> implements Storage {
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    @Override
    public void save(Resume r) {
        LOG.info("save " + r);
        final SearchKey searchKey = getNonExistingSearchKey(r.getUuid());
        doSave(searchKey, r);
    }

    @Override
    public void update(Resume r) {
        LOG.info("update " + r);
        final SearchKey searchKey = getExistingSearchKey(r.getUuid());
        doUpdate(searchKey, r);
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("get " + uuid);
        final SearchKey searchKey = getExistingSearchKey(uuid);
        return doGet(searchKey);
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted");
        List<Resume> all = getAllCopy();
        Collections.sort(all);
        return all;
    }

    @Override
    public void delete(String uuid) {
        LOG.info("delete " + uuid);
        final SearchKey searchKey = getExistingSearchKey(uuid);
        doDelete(searchKey);
    }

    private SearchKey getExistingSearchKey(String uuid) {
        final SearchKey searchKey = getSearchKey(uuid);
        if (!isExists(searchKey)) {
            NotExistsStorageException e = new NotExistsStorageException(uuid);
            LOG.warning(e.getMessage());
            throw e;
        }
        return searchKey;
    }

    private SearchKey getNonExistingSearchKey(String uuid) {
        final SearchKey searchKey = getSearchKey(uuid);
        if (isExists(searchKey)) {
            ExistsStorageException e = new ExistsStorageException(uuid);
            LOG.warning(e.getMessage());
            throw e;
        }
        return searchKey;
    }

    protected abstract SearchKey getSearchKey(String uuid);

    protected abstract boolean isExists(SearchKey searchKey);

    protected abstract void doSave(SearchKey searchKey, Resume r);

    protected abstract void doUpdate(SearchKey searchKey, Resume r);

    protected abstract Resume doGet(SearchKey searchKey);

    protected abstract void doDelete(SearchKey searchKey);

    protected abstract List<Resume> getAllCopy();
}
