package com.github.dlots.webapp.storage;

import com.github.dlots.webapp.exception.StorageException;
import com.github.dlots.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Test;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {
    public AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void saveStorageOverflow() {
        while (storage.size() < AbstractArrayStorage.STORAGE_LIMIT) {
            try {
                storage.save(new Resume());
            }
            catch (StorageException e) {
                Assert.fail("Overflow happened too soon");
            }
        }
        storage.save(new Resume());
    }
}