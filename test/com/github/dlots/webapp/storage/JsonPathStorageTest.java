package com.github.dlots.webapp.storage;

import com.github.dlots.webapp.storage.serializer.JsonStreamSerializer;

public class JsonPathStorageTest extends AbstractStorageTest {
    public JsonPathStorageTest() {
        super(new PathStorage(STORAGE_DIRECTORY, new JsonStreamSerializer()));
    }
}