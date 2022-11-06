package com.github.dlots.webapp.storage;

import com.github.dlots.webapp.exception.ExistsStorageException;
import com.github.dlots.webapp.exception.NotExistsStorageException;
import com.github.dlots.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

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
        savedResumes[0] = new Resume(UUID_1);
        savedResumes[1] = new Resume(UUID_2);
        savedResumes[2] = new Resume(UUID_3);
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
    public void getAll() {
        Resume[] allResumes = storage.getAll();
        Assert.assertEquals(3, allResumes.length);
        Assert.assertTrue(Arrays.asList(allResumes).containsAll(Arrays.asList(savedResumes)));
    }

    @Test
    public void update() {
        storage.update(new Resume(UUID_1));
    }

    @Test(expected = NotExistsStorageException.class)
    public void updateNotExist() {
        storage.update(new Resume("dummy"));
    }

    @Test
    public void save() {
        Assert.assertEquals(savedResumes.length, storage.size());
        Resume r = new Resume();
        storage.save(r);
        Assert.assertEquals(savedResumes.length + 1, storage.size());
        Assert.assertEquals(r, storage.get(r.getUuid()));
    }

    @Test(expected = ExistsStorageException.class)
    public void saveExists() {
        storage.save(new Resume(UUID_1));
    }

    @Test(expected = NotExistsStorageException.class)
    public void delete() {
        Assert.assertEquals(savedResumes.length, storage.size());
        storage.delete(UUID_1);
        Assert.assertEquals(savedResumes.length - 1, storage.size());
        storage.get(UUID_1);
    }
}