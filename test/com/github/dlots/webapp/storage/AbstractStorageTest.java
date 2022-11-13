package com.github.dlots.webapp.storage;

import com.github.dlots.webapp.ResumeTestData;
import com.github.dlots.webapp.exception.ExistsStorageException;
import com.github.dlots.webapp.exception.NotExistsStorageException;
import com.github.dlots.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractStorageTest {
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    public static final Resume R1 = ResumeTestData.getFilledResume(UUID_1, "Amogus");
    public static final Resume R2 = ResumeTestData.getFilledResume(UUID_2, "Geralt of Rivia");
    public static final Resume R3 = ResumeTestData.getFilledResume(UUID_3, "Shrek");

    protected final Storage storage;

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(R1);
        storage.save(R2);
        storage.save(R3);
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void get() {
        Assert.assertEquals(R1, storage.get(UUID_1));
        Assert.assertEquals(R2, storage.get(UUID_2));
        Assert.assertEquals(R3, storage.get(UUID_3));
    }

    @Test(expected = NotExistsStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }

    @Test
    public void getAllSorted() {
        List<Resume> allResumes = storage.getAllSorted();
        Assert.assertEquals(3, allResumes.size());
        Assert.assertTrue(allResumes.containsAll(Arrays.asList(R1, R2, R3)));
        Iterator<Resume> iter = allResumes.iterator();
        Resume current = iter.next();
        while (iter.hasNext()) {
            Resume next = iter.next();
            if (current.compareTo(next) > 0) {
                Assert.fail();
            }
        }
    }

    @Test
    public void update() {
        Resume r = ResumeTestData.getFilledResume(UUID_1, "New Name");
        storage.update(r);
        Assert.assertEquals(r, storage.get(UUID_1));
    }

    @Test(expected = NotExistsStorageException.class)
    public void updateNotExist() {
        storage.update(ResumeTestData.getFilledResume("dummy"));
    }

    @Test
    public void save() {
        Assert.assertEquals(3, storage.size());
        Resume r = ResumeTestData.getFilledResume("");
        storage.save(r);
        Assert.assertEquals(4, storage.size());
        Assert.assertEquals(r, storage.get(r.getUuid()));
    }

    @Test(expected = ExistsStorageException.class)
    public void saveExists() {
        storage.save(ResumeTestData.getFilledResume(UUID_1, ""));
    }

    @Test(expected = NotExistsStorageException.class)
    public void delete() {
        Assert.assertEquals(3, storage.size());
        storage.delete(UUID_1);
        Assert.assertEquals(2, storage.size());
        storage.get(UUID_1);
    }
}