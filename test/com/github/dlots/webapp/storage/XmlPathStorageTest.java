package com.github.dlots.webapp.storage;

import com.github.dlots.webapp.storage.serializer.XmlStreamSerializer;

public class XmlPathStorageTest extends AbstractStorageTest {
    public XmlPathStorageTest() {
        super(new PathStorage(STORAGE_DIRECTORY, new XmlStreamSerializer()));
    }
}