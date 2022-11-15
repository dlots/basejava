package com.github.dlots.webapp.storage;

import com.github.dlots.webapp.storage.serializer.JsonStreamSerializer;

public class JsonPathStorageTest extends AbstractStorageTest {
    private static final String STORAGE_DIRECTORY = "E:\\IdeaProjects\\basejava\\resume_storage";

    public JsonPathStorageTest() {
        super(new PathStorage(STORAGE_DIRECTORY, new JsonStreamSerializer()));
    }
}