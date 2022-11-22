package com.github.dlots.webapp.storage;

import com.github.dlots.webapp.storage.serializer.ObjectStreamSerializer;

import java.io.File;

public class ObjectFileStorageTest extends AbstractStorageTest {
    public ObjectFileStorageTest() {
        super(new FileStorage(new File(STORAGE_DIRECTORY), new ObjectStreamSerializer()));
    }
}