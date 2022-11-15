package com.github.dlots.webapp.storage;

import com.github.dlots.webapp.storage.serializer.ObjectStreamSerializer;

public class ObjectPathStorageTest extends AbstractStorageTest {
    private static final String STORAGE_DIRECTORY = "E:\\IdeaProjects\\basejava\\resume_storage";

    public ObjectPathStorageTest() {
        super(new PathStorage(STORAGE_DIRECTORY, new ObjectStreamSerializer()));
    }
}