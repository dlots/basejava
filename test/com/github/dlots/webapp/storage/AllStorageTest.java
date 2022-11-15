package com.github.dlots.webapp.storage;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ArrayStorageTest.class,
        SortedArrayStorageTest.class,
        ListStorageTest.class,
        UUIDSearchKeyMapStorageTest.class,
        ResumeSearchKeyMapStorageTest.class,
        ObjectFileStorageTest.class,
        ObjectPathStorageTest.class
})
public class AllStorageTest {
}
