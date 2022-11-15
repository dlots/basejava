package com.github.dlots.webapp.storage;

import com.github.dlots.webapp.storage.serializer.XmlStreamSerializer;

public class XmlPathStorageTest extends AbstractStorageTest {
    private static final String STORAGE_DIRECTORY = "E:\\IdeaProjects\\basejava\\resume_storage";

    public XmlPathStorageTest() {
        super(new PathStorage(STORAGE_DIRECTORY, new XmlStreamSerializer()));
    }
}