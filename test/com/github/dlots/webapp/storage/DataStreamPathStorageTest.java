package com.github.dlots.webapp.storage;

import com.github.dlots.webapp.storage.serializer.DataStreamSerializer;

public class DataStreamPathStorageTest extends AbstractStorageTest {
    private static final String STORAGE_DIRECTORY = "E:\\IdeaProjects\\basejava\\resume_storage";

    public DataStreamPathStorageTest() {
        super(new PathStorage(STORAGE_DIRECTORY, new DataStreamSerializer()));
    }
}