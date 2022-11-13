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

import static com.github.dlots.webapp.storage.AbstractStorage.RESUME_COMPARATOR;

public abstract class AbstractStorageTest {
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    protected final Storage storage;
    private final Resume[] savedResumes = new Resume[3];

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        savedResumes[0] = ResumeTestData.getFilledResume(UUID_1, "Amogus");
        savedResumes[1] = ResumeTestData.getFilledResume(UUID_2, "Geralt of Rivia");
        savedResumes[2] = ResumeTestData.getFilledResume(UUID_3, "Shrek");
        storage.save(savedResumes[0]);
        storage.save(savedResumes[1]);
        storage.save(savedResumes[2]);
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
        Assert.assertEquals(savedResumes[0], storage.get(UUID_1));
        Assert.assertEquals(savedResumes[1], storage.get(UUID_2));
        Assert.assertEquals(savedResumes[2], storage.get(UUID_3));
    }

    @Test(expected = NotExistsStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }

    @Test
    public void getAllSorted() {
        List<Resume> allResumes = storage.getAllSorted();
        Assert.assertEquals(3, allResumes.size());
        Assert.assertTrue(allResumes.containsAll(Arrays.asList(savedResumes)));
        Iterator<Resume> iter = allResumes.iterator();
        Resume current = iter.next();
        while (iter.hasNext()) {
            Resume next = iter.next();
            if (RESUME_COMPARATOR.compare(current, next) > 0) {
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
        Assert.assertEquals(savedResumes.length, storage.size());
        Resume r = ResumeTestData.getFilledResume("");
        storage.save(r);
        Assert.assertEquals(savedResumes.length + 1, storage.size());
        Assert.assertEquals(r, storage.get(r.getUuid()));
    }

    @Test(expected = ExistsStorageException.class)
    public void saveExists() {
        storage.save(ResumeTestData.getFilledResume(UUID_1, ""));
    }

    @Test(expected = NotExistsStorageException.class)
    public void delete() {
        Assert.assertEquals(savedResumes.length, storage.size());
        storage.delete(UUID_1);
        Assert.assertEquals(savedResumes.length - 1, storage.size());
        storage.get(UUID_1);
    }
}