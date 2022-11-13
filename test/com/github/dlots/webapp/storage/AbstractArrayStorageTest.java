package com.github.dlots.webapp.storage;

import com.github.dlots.webapp.ResumeTestData;
import com.github.dlots.webapp.exception.StorageException;
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
                storage.save(ResumeTestData.getFilledResume(""));
            }
            catch (StorageException e) {
                Assert.fail("Overflow happened too soon");
            }
        }
        storage.save(ResumeTestData.getFilledResume(""));
    }
}