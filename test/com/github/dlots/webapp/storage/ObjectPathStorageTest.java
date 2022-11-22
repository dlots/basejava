package com.github.dlots.webapp.storage;

import com.github.dlots.webapp.storage.serializer.ObjectStreamSerializer;

public class ObjectPathStorageTest extends AbstractStorageTest {
    public ObjectPathStorageTest() {
        super(new PathStorage(STORAGE_DIRECTORY, new ObjectStreamSerializer()));
    }
}