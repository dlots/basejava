package com.github.dlots.webapp.util;

import com.github.dlots.webapp.storage.SqlStorage;
import com.github.dlots.webapp.storage.Storage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Properties;

public class Config {
    private static final String STORAGE_DIR_KEY = "storage.dir";
    private static final String DB_URL_KEY = "db.url";
    private static final String DB_USER_KEY = "db.user";
    private static final String DB_PASSWORD_KEY = "db.password";
    private static final File PROPERTIES_FILE = new File(getHomeDir(), "config\\resumes.properties");

    private final String storageDirectory;

    private final Storage storage;

    private static final Config INSTANCE = new Config();

    public static Config get() {
        return INSTANCE;
    }

    private Config() {
        try (InputStream inputStream = Files.newInputStream(PROPERTIES_FILE.toPath())) {
            final Properties properties = new Properties();
            properties.load(inputStream);
            storageDirectory = properties.getProperty(STORAGE_DIR_KEY);
            storage = new SqlStorage(properties.getProperty(DB_URL_KEY), properties.getProperty(DB_USER_KEY), properties.getProperty(DB_PASSWORD_KEY));

        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file" + PROPERTIES_FILE.getAbsolutePath(), e);
        }
    }

    public String getStorageDirectory() {
        return storageDirectory;
    }

    public Storage getStorage() {
        return storage;
    }

    private static File getHomeDir() {
        String property = System.getProperty("homeDir");
        File homeDir = new File(property == null ? "." : property);
        if (!homeDir.isDirectory()) {
            throw new IllegalStateException(homeDir + " is not a directory!");
        }
        return homeDir;
    }
}
