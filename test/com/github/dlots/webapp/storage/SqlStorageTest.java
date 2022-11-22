package com.github.dlots.webapp.storage;

import com.github.dlots.webapp.util.Config;

import static org.junit.Assert.*;

public class SqlStorageTest extends AbstractStorageTest {
    public SqlStorageTest() {
        super(new SqlStorage(Config.get().getDbUrl(), Config.get().getDbUser(), Config.get().getDbPassword()));
    }
}