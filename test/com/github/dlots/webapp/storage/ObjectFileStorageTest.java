package com.github.dlots.webapp.storage;

import com.github.dlots.webapp.storage.serializer.ObjectStreamSerializer;

import java.io.File;

public class ObjectFileStorageTest extends AbstractStorageTest {
    private static final File STORAGE_DIRECTORY = new File("E:\\IdeaProjects\\basejava\\resume_storage");

    public ObjectFileStorageTest() {
        super(new FileStorage(STORAGE_DIRECTORY, new ObjectStreamSerializer()));
    }
}