package com.github.dlots.webapp.storage;

import com.github.dlots.webapp.ResumeTestData;
import com.github.dlots.webapp.exception.ExistsStorageException;
import com.github.dlots.webapp.exception.NotExistsStorageException;
import com.github.dlots.webapp.model.ContactType;
import com.github.dlots.webapp.model.Resume;
import com.github.dlots.webapp.util.Config;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractStorageTest {
    protected static final String STORAGE_DIRECTORY = Config.get().getStorageDirectory();
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    private static final String UUID_4 = "uuid4";
    public static final Resume R1 = ResumeTestData.getFilledResume(UUID_1, "Amogus");
    public static final Resume R2 = ResumeTestData.getFilledResume(UUID_2, "Geralt of Rivia");
    public static final Resume R3 = ResumeTestData.getFilledResume(UUID_3, "Shrek");

    public static final Resume R4 = new Resume(UUID_4, "No contacts");

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
        storage.save(R4);
    }

    @Test
    public void size() {
        Assert.assertEquals(4, storage.size());
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
        Assert.assertEquals(R4, storage.get(UUID_4));
    }

    @Test(expected = NotExistsStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }

    @Test
    public void getAllSorted() {
        List<Resume> allResumes = storage.getAllSorted();
        Assert.assertEquals(4, allResumes.size());
        Assert.assertTrue(allResumes.containsAll(Arrays.asList(R1, R2, R3, R4)));
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
        r.getContacts().put(ContactType.SKYPE, "88005553535");
        storage.update(r);
        Resume stored = storage.get(UUID_1);
        Assert.assertEquals(r, stored);
    }

    @Test(expected = NotExistsStorageException.class)
    public void updateNotExist() {
        storage.update(ResumeTestData.getFilledResume("dummy"));
    }

    @Test
    public void save() {
        Assert.assertEquals(4, storage.size());
        Resume r = ResumeTestData.getFilledResume("");
        storage.save(r);
        Assert.assertEquals(5, storage.size());
        Assert.assertEquals(r, storage.get(r.getUuid()));
    }

    @Test(expected = ExistsStorageException.class)
    public void saveExists() {
        storage.save(ResumeTestData.getFilledResume(UUID_1, ""));
    }

    @Test(expected = NotExistsStorageException.class)
    public void delete() {
        Assert.assertEquals(4, storage.size());
        storage.delete(UUID_1);
        Assert.assertEquals(3, storage.size());
        storage.get(UUID_1);
    }
}