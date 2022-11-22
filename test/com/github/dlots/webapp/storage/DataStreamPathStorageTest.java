package com.github.dlots.webapp.storage;

import com.github.dlots.webapp.storage.serializer.DataStreamSerializer;

public class DataStreamPathStorageTest extends AbstractStorageTest {
    public DataStreamPathStorageTest() {
        super(new PathStorage(STORAGE_DIRECTORY, new DataStreamSerializer()));
    }
}